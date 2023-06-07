package ru.practicum.later.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.later.exception.exp.BadRequestException;
import ru.practicum.later.exception.exp.NotFoundException;
import ru.practicum.later.user.UserService;
import ru.practicum.later.validation.Marker;
import ru.practicum.later.validation.Util;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;
import java.util.Set;


import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
class ItemServiceImpl implements ItemService {
    private final ItemEntityRepository itemEntityRepository;
    private final ModelMapper itemModelMapper;
    private final UserService userService;


    @Override
    public List<ItemView> findAllItemView() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /findAllItemView не реализован.");
        //return itemEntityRepository.findAllItemView();
    }

    @Override
    public List<ItemCountByOwner> countByOwnerRegistered(String dateFrom, String dateTo) throws BadRequestException {
        final Instant from = Util.dateStringToLocalDate(dateFrom);
        final Instant to = Util.dateStringToLocalDate(dateTo);
        Util.validateDates(from, to);
        return itemEntityRepository.countByOwnerRegistered(from, to);
    }

    @Override
    public List<ItemCountByOwner> countByOwnerRegisteredAndUrl(String urlPart, String dateFrom, String dateTo) throws BadRequestException {
        final Instant from = Util.dateStringToLocalDate(dateFrom);
        final Instant to = Util.dateStringToLocalDate(dateTo);
        Util.validateDates(from, to);
        return itemEntityRepository.countByOwnerRegisteredAndUrl(urlPart, from, to);
    }

    @Override
    public List<ItemCountByOwner> countItemsByOwner(String urlPart) {
        return itemEntityRepository.countItemsByOwner(urlPart);
    }

    @Override
    public List<ItemDto> findItemsByOwnerId(@Positive long ownerId) throws NotFoundException {
        userService.checkExistUserById(ownerId);
        final List<ItemEntity> userItems = itemEntityRepository.findItemsByOwnerIdOrderById(ownerId);
        return userItems
                .stream()
                .map(itemEntity -> itemModelMapper.map(itemEntity, ItemDto.class))
                .collect(toList());
    }

    @Override
    public List<ItemDto> findItemsByOwnerIdAndTags(@Positive long ownerId, @NotEmpty Set<String> tags) {
        BooleanExpression byOwnerId = QItemEntity.itemEntity.ownerId.eq(ownerId); // предикаты
        BooleanExpression byAnyTag = QItemEntity.itemEntity.tags.any().in(tags);  // предикаты
        List<ItemEntity> foundItems = (List<ItemEntity>)itemEntityRepository.findAll(byOwnerId.and(byAnyTag));
        return foundItems
                .stream()
                .map(itemEntity -> itemModelMapper.map(itemEntity, ItemDto.class))
                .collect(toList());
    }                                                       // QueryDSL target/generated-sources/java

    @Override
    public List<ItemDto> findItemsByLastNamePrefix(String lastNamePrefix) {
        final List<ItemEntity> userItems = itemEntityRepository.findItemsByLastNamePrefix(lastNamePrefix);
        return userItems
                .stream()
                .map(itemEntity -> itemModelMapper.map(itemEntity, ItemDto.class))
                .collect(toList());
    }

    @Override
    public ItemDto findWithJoinFetch(long itemId) {
        final ItemEntity itemJoinFetch = itemEntityRepository
                .findWithJoinFetch(itemId)
                .orElseThrow(
                        () -> new NotFoundException("Вещь по id => " + itemId + " не существует"));
        return itemModelMapper.map(itemJoinFetch, ItemDto.class);
    }

    @Transactional
    @Validated(Marker.OnCreate.class)
    @Override
    public ItemDto saveItemByOwnerId(@Positive long ownerId, @Valid ItemDto itemDto) throws NotFoundException {
        userService.checkExistUserById(ownerId);
        itemDto.setOwnerId(ownerId);
        final ItemEntity itemEntity = itemModelMapper.map(itemDto, ItemEntity.class);
        final ItemEntity createdItemEntity = itemEntityRepository.save(itemEntity);
        return itemModelMapper.map(createdItemEntity, ItemDto.class);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndOwnerId(@Positive long userId, @Positive long itemId) throws NotFoundException {
        userService.checkExistUserById(userId);
        this.checkExistItemById(itemId);
        itemEntityRepository.deleteById(itemId);
    }

    @Transactional
    @Override
    public void deleteItemsByOwnerId(@Positive long ownerId) throws NotFoundException {
        userService.checkExistUserById(ownerId);
        itemEntityRepository.deleteItemsByOwnerId(ownerId);
    }

    @Override
    public void checkExistItemById(long itemId) throws NotFoundException {
        if (!itemEntityRepository.existsById(itemId)) {
            throw new NotFoundException("Вещь по id => " + itemId + " не существует");
        }
    }

}


package ru.practicum.later.item;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.later.expception.exp.NotFoundException;
import ru.practicum.later.user.UserService;
import ru.practicum.later.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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
    public List<ItemDto> getItemsByOwnerId(@Positive long ownerId) throws NotFoundException {
        userService.checkExistUserById(ownerId);
        final List<ItemEntity> userItems = itemEntityRepository.findItemsByOwnerIdOrderById(ownerId);
        return userItems
                .stream()
                .map(itemEntity -> itemModelMapper.map(itemEntity, ItemDto.class))
                .collect(toList());
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

    @Override
    public void deleteItemsByOwnerId(@Positive long ownerId) throws NotFoundException {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /deleteItemsByOwnerId не реализован.");
        //userService.checkExistUserById(ownerId);
        //itemEntityRepository.deleteItemsByOwnerId(ownerId);
    }

    @Override
    public void checkExistItemById(long itemId) throws NotFoundException {
        if (!itemEntityRepository.existsById(itemId)) {
            throw new NotFoundException("Вещь по id => " + itemId + " не существует");
        }
    }

}

/*

select id, user_id , url
    from items
    where user_id=?

select id, email, first_name, last_name, password , registration_date, state
    from public.users
    where id=?

*/


package ru.practicum.later.item;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.later.exception.exp.BadRequestException;
import ru.practicum.later.exception.exp.NotFoundException;
import ru.practicum.later.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@Validated
@Transactional(readOnly = true)
interface ItemService {

    List<ItemView> findAllItemView();

    List<ItemCountByOwner> countByOwnerRegisteredAndUrl(@NotBlank String urlPart, String dateFrom, String dateTo) throws BadRequestException;

    List<ItemCountByOwner> countByOwnerRegistered(String dateFrom, String dateTo) throws BadRequestException;

    List<ItemCountByOwner> countItemsByOwner(@NotBlank String urlPart);

    List<ItemDto> findItemsByOwnerId(@Positive long ownerId) throws NotFoundException;

    List<ItemDto> findItemsByOwnerIdAndTags(@Positive long ownerId, @NotEmpty Set<String> tags);

    List<ItemDto> findItemsByLastNamePrefix(@NotBlank String lastNamePrefix);

    ItemDto findWithJoinFetch(@Positive long itemId);

    @Transactional
    @Validated(Marker.OnCreate.class)
    ItemDto saveItemByOwnerId(@Positive long ownerId, @Valid ItemDto itemDto) throws NotFoundException;

    @Transactional
    void deleteByUserIdAndOwnerId(@Positive long ownerId, @Positive long itemId) throws NotFoundException;

    @Transactional
    void deleteItemsByOwnerId(@Positive long ownerId) throws NotFoundException;

    void checkExistItemById(long itemId) throws NotFoundException;
}
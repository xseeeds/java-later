package ru.practicum.later.item;

import org.springframework.validation.annotation.Validated;
import ru.practicum.later.expception.exp.NotFoundException;
import ru.practicum.later.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
interface ItemService {

    List<ItemDto> getItemsByOwnerId(@Positive long ownerId) throws NotFoundException;

    @Validated(Marker.OnCreate.class)
    ItemDto saveItemByOwnerId(@Positive long ownerId, @Valid ItemDto itemDto) throws NotFoundException;

    void deleteByUserIdAndOwnerId(@Positive long ownerId, @Positive long itemId) throws NotFoundException;

    void deleteItemsByOwnerId(@Positive long ownerId) throws NotFoundException;

    void checkExistItemById(long itemId) throws NotFoundException;
}
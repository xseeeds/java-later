/*
package ru.practicum.later.item;

import lombok.experimental.UtilityClass;

@UtilityClass
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public ItemDto toItemDto(ItemEntity itemEntity) {
        final ItemDto itemDto = new ItemDto();
        itemDto.setId(itemEntity.getId());
        itemDto.setOwnerId(itemEntity.getOwnerId());
        itemDto.setTags(itemEntity.getTags());
        return itemDto;
    }

    public ItemEntity toItemEntity(ItemDto itemDto, long ownerId) {
        final ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemDto.getId());
        itemEntity.setOwnerId(ownerId);
        itemEntity.setTags(itemDto.getTags());
        return itemEntity;
    }

}
*/
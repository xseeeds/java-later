package ru.practicum.later.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCountByOwner {

    private Long ownerId;

    private Long count;

    public ItemCountByOwner(Long ownerId, Long count) {
        this.ownerId = ownerId;
        this.count = count;
    }
}
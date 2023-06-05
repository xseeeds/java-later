package ru.practicum.later.item;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;

    private Long ownerId;

    private String url;

    private Set<String> tags;

}

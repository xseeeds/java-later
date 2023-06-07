package ru.practicum.later.item;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.later.validation.Marker;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ItemDto {

    @Positive(groups = Marker.OnUpdate.class)
    private Long id;

    private Long ownerId;

    @Pattern(groups = {Marker.OnCreate.class, Marker.OnUpdate.class},
            regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
            message = "должен быть адресом URL")
    private String url;

    private Set<String> tags = new HashSet<>();

}

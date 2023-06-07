package ru.practicum.later.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.later.validation.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemNoteDto {

    @Positive(groups = Marker.OnUpdate.class)
    private Long id;

    @NotBlank
    @NotNull
    private Long itemId;

    @NotBlank(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @NotNull(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String text;

    private String dateOfNote;

    @NotBlank
    @NotNull
    private String itemUrl;
}

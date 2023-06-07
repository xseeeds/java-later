package ru.practicum.later.note;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.later.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Transactional(readOnly = true)
@Validated
interface ItemNoteService {

    @Transactional
    @Validated(Marker.OnCreate.class)
    ItemNoteDto addNewItemNote(@Positive long userId, @Valid ItemNoteDto itemNoteDto);

    List<ItemNoteDto> searchNotesByUrl(@NotBlank String url, @Positive long userId);

    List<ItemNoteDto> searchNotesByTag(@Positive long ownerId, @Positive String tag);

    List<ItemNoteDto> listAllItemsWithNotes(@Positive long ownerId, @PositiveOrZero int from, @Positive int size);
}

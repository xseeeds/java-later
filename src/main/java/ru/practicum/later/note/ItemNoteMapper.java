package ru.practicum.later.note;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.later.item.ItemEntity;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemNoteMapper {

    public static ItemNoteDto toItemNoteDto(ItemNoteEntity itemNoteEntity) {
        String dateOfNote = DateTimeFormatter
                .ofPattern("yyyy.MM.dd hh:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(itemNoteEntity.getDateOfNote());

        return new ItemNoteDto(
                itemNoteEntity.getId(),
                itemNoteEntity.getItem().getId(),
                itemNoteEntity.getText(),
                dateOfNote,
                itemNoteEntity.getItem().getUrl()
        );
    }

    public static List<ItemNoteDto> toItemNoteDto(Iterable<ItemNoteEntity> itemNotes) {
        List<ItemNoteDto> dtos = new ArrayList<>();
        for (ItemNoteEntity itemNote : itemNotes) {
            dtos.add(toItemNoteDto(itemNote));
        }
        return dtos;
    }

    public static ItemNoteEntity toItemNote(ItemNoteDto itemNoteDto, ItemEntity itemEntity) {
        ItemNoteEntity itemNoteEntity = new ItemNoteEntity();
        itemNoteEntity.setItem(itemEntity);
        itemNoteEntity.setText(itemNoteDto.getText());
        return itemNoteEntity;
    }
}

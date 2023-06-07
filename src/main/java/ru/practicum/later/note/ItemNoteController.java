package ru.practicum.later.note;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.later.header.HttpHeadersLater;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class ItemNoteController {

    private final ItemNoteService itemNoteService;

    @GetMapping(params = "url")
    public List<ItemNoteDto> searchByUrl(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long userId,
                                         @RequestParam String url) {
        return itemNoteService.searchNotesByUrl(url, userId);
    }

    @GetMapping(params = "tag")
    public List<ItemNoteDto> searchByTags(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long userId,
                                          @RequestParam String tag) {
        return itemNoteService.searchNotesByTag(userId, tag);
    }

    @GetMapping
    public List<ItemNoteDto> listAllNotes(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long userId,
                                          @RequestParam(defaultValue = "1") int from,
                                          @RequestParam(defaultValue = "10") int size) {
        return itemNoteService.listAllItemsWithNotes(userId, from, size);
    }

    @PostMapping
    public ItemNoteDto add(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) Long userId, @RequestBody ItemNoteDto itemNote) {
        return itemNoteService.addNewItemNote(userId, itemNote);
    }
}

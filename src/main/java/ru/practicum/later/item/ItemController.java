package ru.practicum.later.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.later.header.HttpHeadersLater;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getItemsByOwnerId(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long ownerId) {
        return itemService.getItemsByOwnerId(ownerId);
    }

    @PostMapping
    public ItemDto saveItemByOwnerId(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long ownerId,
                                     @RequestBody ItemDto itemDto) {
        return itemService.saveItemByOwnerId(ownerId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteByUserIdAndOwnerId(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long ownerId,
                                        @PathVariable long itemId) {
        itemService.deleteByUserIdAndOwnerId(ownerId, itemId);
    }

    @DeleteMapping
    public void deleteItemsByOwnerId(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long ownerId) {
        itemService.deleteItemsByOwnerId(ownerId);
    }

}

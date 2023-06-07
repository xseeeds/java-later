package ru.practicum.later.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.later.exception.exp.BadRequestException;
import ru.practicum.later.header.HttpHeadersLater;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/view")
    public List<ItemView> findAllItemView() {
        return itemService.findAllItemView();
    }

    @GetMapping
    public List<ItemDto> findItemsByOwnerId(@RequestHeader(HttpHeadersLater.X_LATER_USER_ID) long ownerId,
                                            @RequestParam(required = false) Set<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return itemService.findItemsByOwnerId(ownerId);
        } else {
            return itemService.findItemsByOwnerIdAndTags(ownerId, tags);
        }
    }

    @GetMapping(params = "lastName")                    //?lastname={lastname}
    public List<ItemDto> findItemsByLastNamePrefix(@RequestParam String lastName) {
        return itemService.findItemsByLastNamePrefix(lastName);
    }

    @GetMapping("/{itemId}")
    public ItemDto findWithJoinFetch(@PathVariable long itemId) {
        return itemService.findWithJoinFetch(itemId);
    }

    @GetMapping("/countByOwner")                    //?urlPart={urlPart}&dateFrom={dd.MM.yyyy}&dateTo={dd.MM.yyyy}
    public List<ItemCountByOwner> countItemsByOwner(@RequestParam(value = "urlPart", required = false) String urlPart,
                                                    @RequestParam(value = "dateFrom", required = false) String dateFrom,
                                                    @RequestParam(value = "dateTo", required = false) String dateTo) {
        if (urlPart != null && dateFrom != null && dateTo != null) {
            return itemService.countByOwnerRegisteredAndUrl(urlPart, dateFrom, dateTo);
        }
        if (dateFrom != null && dateTo != null) {
            return itemService.countByOwnerRegistered(dateFrom, dateTo);
        }
        if (urlPart != null) {
            return itemService.countItemsByOwner(urlPart);
        }
        throw new BadRequestException("Параметр в методе users/countByOwner?urlPart={urlPart}&dateFrom={dd.MM.yyyy}&dateTo={dd.MM.yyyy}, " +
                "должн быть либо urlPart, либо dateFrom и dateTo");
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

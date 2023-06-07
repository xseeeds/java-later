package ru.practicum.later.note;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.later.exception.common.InsufficientPermissionException;
import ru.practicum.later.item.ItemEntity;
import ru.practicum.later.item.ItemEntityRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemNoteServiceImpl implements ItemNoteService {

    private final ItemNoteEntityRepository itemNoteEntityRepository;

    private final ItemEntityRepository itemEntityRepository;

    @Override
    @Transactional
    public ItemNoteDto addNewItemNote(@Positive long userId, @Valid ItemNoteDto itemNoteDto) {
        ItemEntity itemEntity = itemEntityRepository.findById(itemNoteDto.getItemId())
                .orElseThrow(() ->  new InsufficientPermissionException(
                        "You do not have permission to perform this operation"));
        ItemNoteEntity itemNoteEntity = itemNoteEntityRepository.save(ItemNoteMapper.toItemNote(itemNoteDto, itemEntity));
        return ItemNoteMapper.toItemNoteDto(itemNoteEntity);
    }

    @Override
    public List<ItemNoteDto> searchNotesByUrl(@NotBlank String url, @Positive long ownerId) {
        List<ItemNoteEntity> itemNotes = itemNoteEntityRepository.findAllByItemUrlContainingAndItemOwnerId(url, ownerId);
        return ItemNoteMapper.toItemNoteDto(itemNotes);
    }

    @Override
    public List<ItemNoteDto> searchNotesByTag(@Positive long userId, @Positive String tag) {
        List<ItemNoteEntity> itemNotes = itemNoteEntityRepository.findByTag(userId, tag);
        return ItemNoteMapper.toItemNoteDto(itemNotes);
    }

    @Override
    public List<ItemNoteDto> listAllItemsWithNotes(@Positive long ownerId,
                                                   @PositiveOrZero int from,
                                                   @Positive int size) {
        final PageRequest page = PageRequest.of(from > 1 ? from / size - size : 1, size);
        return itemNoteEntityRepository.findAllByItemOwnerId(ownerId, page)
                .map(ItemNoteMapper::toItemNoteDto)
                .getContent();
    }
}

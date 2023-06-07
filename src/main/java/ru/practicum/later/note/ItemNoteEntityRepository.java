package ru.practicum.later.note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ItemNoteEntityRepository extends JpaRepository<ItemNoteEntity, Long> {

    List<ItemNoteEntity> findAllByItemUrlContainingAndItemOwnerId(String itemUrl, long ownerId);

    @Query("select iNoteE " +
            "from ItemNoteEntity as iNoteE " +
            "join iNoteE.item as iE " +
            "where iE.ownerId = ?1 " +
            "and ?2 member of iE.tags")
    List<ItemNoteEntity> findByTag(Long userId, String tag);

    Page<ItemNoteEntity> findAllByItemOwnerId(long ownerId, Pageable page);
}

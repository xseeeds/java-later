package ru.practicum.later.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findItemsByOwnerIdOrderById(long ownerId);

/*
    @Query("delete " +
        "from items " +
        "where user_id = :ownerId")
*/
    void deleteItemsByOwnerId(long ownerId);
}

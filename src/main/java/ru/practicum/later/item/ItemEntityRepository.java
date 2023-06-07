package ru.practicum.later.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long>, QuerydslPredicateExecutor<ItemEntity> {

    //List<ItemView> findAllItemView(); //механизм проекций.

    @Query(value = "select it.owner_id, count(it.id) as count " +
            "from items as it " +
            "left join users as us on it.owner_id = us.id " +
            "where (cast(us.registration_date as date)) between ?1 and ?2 " +
            "group by it.owner_id", nativeQuery = true)                                     //JPQL-запрос нативным запросом
    List<ItemCountByOwner> countByOwnerRegistered(Instant dateFrom, Instant dateTo); //Не рабатает не может найти конвертер

    @Query("select new ru.practicum.later.item.ItemCountByOwner(iE.ownerId, count(iE.id))" +
            "from ItemEntity as iE " +
            "left join iE.owner as uE " +
            "where iE.url like :urlPart " +
            "and uE.registrationDate between :dateFrom and :dateTo " +
            "group by iE.ownerId " +
            "order by count(iE.id) desc")
    List<ItemCountByOwner> countByOwnerRegisteredAndUrl(String urlPart, Instant dateFrom, Instant dateTo);  //JPQL-запрос

    List<ItemEntity> findItemsByOwnerIdOrderById(long ownerId); //запросный метод

    @Query("select iE from ItemEntity iE " +
            "join fetch iE.owner where iE.id = :itemId")
    Optional<ItemEntity> findWithJoinFetch(long itemId); //JPQL-запрос

    @Query("select new ru.practicum.later.item.ItemCountByOwner(iE.ownerId, count(iE.id))" +
            "from ItemEntity as iE " +
            "where iE.url like :urlPart " +
            "group by iE.ownerId " +
            "order by count(iE.id) desc")
    List<ItemCountByOwner> countItemsByOwner(String urlPart); //JPQL-запрос

    @Modifying
    void deleteItemsByOwnerId(long ownerId); //запросный метод

    @Query("select iE " +
            "from ItemEntity as iE " +
            "join iE.owner as uE " +
            "where uE.lastName like concat(?1, '%')")
    List<ItemEntity> findItemsByLastNamePrefix(String lastNamePrefix); //JPQL-запрос

    List<ItemEntity> findAllByOwnerLastNameStartingWith(String lastNamePrefix); //запросный метод StartingWith — предикат;




}
package ru.practicum.later.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import ru.practicum.later.user.UserEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false, unique = true, insertable = false, updatable = false)
    @JsonBackReference
    private UserEntity owner;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column
    private String url;

    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    @Column(name = "name")
    private Set<String> tags = new HashSet<>();

}
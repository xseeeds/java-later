package ru.practicum.later.note;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.later.item.ItemEntity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Table(name = "item_notes")
public class ItemNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // исключаем все поля с отложенной загрузкой из
    // метода toString, чтобы не было случайных обращений
    // базе данных, например при выводе в лог.
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemEntity item;

    @Column
    private String text;

    @Column(name = "note_date")
    private Instant dateOfNote = Instant.now();

    //TODO надо это обдумать, а как будет происходить обновление Note в маппере применится новая дата)))

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemNoteEntity)) return false;
        return id != null && id.equals(((ItemNoteEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

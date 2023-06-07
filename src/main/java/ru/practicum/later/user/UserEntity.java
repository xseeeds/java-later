package ru.practicum.later.user;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "users"/*, schema = "public"*/)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    @ToString.Exclude
    private String password;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @Enumerated(EnumType.STRING)
    //(EnumType.ORDINAL) нумерация enum
    private UserState state;
}
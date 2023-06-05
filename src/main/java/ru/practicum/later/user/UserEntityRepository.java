package ru.practicum.later.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(Set<UserState> states, Instant from, Instant to);



}


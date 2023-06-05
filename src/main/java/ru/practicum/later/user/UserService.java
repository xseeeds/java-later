package ru.practicum.later.user;

import org.springframework.validation.annotation.Validated;
import ru.practicum.later.expception.exp.NotFoundException;
import ru.practicum.later.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Validated
public interface UserService {

    UserDto findUserById(@Positive long userId) throws NotFoundException;

    List<UserDto> findAllUsers();

    List<UserDto> findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(Set<UserState> states, Instant from, Instant to);

    @Validated(Marker.OnCreate.class)
    UserDto saveUser(@Valid UserDto userDto);

    void checkExistUserById(@Positive long userId) throws NotFoundException;
}
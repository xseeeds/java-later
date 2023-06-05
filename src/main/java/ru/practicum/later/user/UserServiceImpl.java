package ru.practicum.later.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.later.expception.exp.NotFoundException;
import ru.practicum.later.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper userModelMapper;

    @Override
    public UserDto findUserById(@Positive long userId) throws NotFoundException {
        this.checkExistUserById(userId);
        final Optional<UserEntity> userEntity = userEntityRepository
                .findById(userId);
        return userModelMapper.map(userEntity.get(), UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        final List<UserEntity> usersEntity = userEntityRepository.findAll();
        return usersEntity
                .stream()
                .map(userEntity -> userModelMapper.map(userEntity, UserDto.class))
                .collect(toList());
    }

    @Override
    public List<UserDto> findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(Set<UserState> states, Instant from, Instant to) {
        List<UserEntity> usersEntity = userEntityRepository
                .findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(states, from, to);
        return usersEntity
                .stream()
                .map(userEntity -> userModelMapper.map(userEntity, UserDto.class))
                .collect(toList());
    }

    @Transactional
    @Validated(Marker.OnCreate.class)
    @Override
    public UserDto saveUser(@Valid UserDto userDto) {
        final UserEntity userEntity = userModelMapper.map(userDto, UserEntity.class);
        userEntity.setRegistrationDate(Instant.now());
        final UserEntity createdUserEntity = userEntityRepository.save(userEntity);
        return userModelMapper.map(createdUserEntity, UserDto.class);
    }

    @Override
    public void checkExistUserById(@Positive long userId) throws NotFoundException {
        if (!userEntityRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь по id => " + userId + " не найден");
        }
    }

}
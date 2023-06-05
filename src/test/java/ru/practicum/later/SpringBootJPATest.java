package ru.practicum.later;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.later.item.ItemDto;
import ru.practicum.later.item.ItemEntity;
import ru.practicum.later.item.ItemEntityRepository;
import ru.practicum.later.user.UserEntity;
import ru.practicum.later.user.UserEntityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SpringBootJPATest {
    private final ItemEntityRepository itemEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper itemModelMapper;
    private final ModelMapper userModelMapper;

    @Test
    public void givenUserItemEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@email.ru");
        userEntity.setFirstName("Chelentano");
        final UserEntity createdUserEntity =
                userEntityRepository.save(userEntity);
        final Optional<UserEntity> foundedUserEntity =
                userEntityRepository.findById(createdUserEntity.getId());

        assertTrue(foundedUserEntity.isPresent());
        assertEquals(userEntity, foundedUserEntity.get());


        ItemDto itemDto = new ItemDto();
        itemDto.setUrl("test@url");
        itemDto.setOwnerId(1L);
        final ItemEntity itemEntity = itemModelMapper.map(itemDto, ItemEntity.class);
        final ItemEntity createdItemEntity = itemEntityRepository.save(itemEntity);

        final Optional<ItemEntity> foundedItemEntity =
                itemEntityRepository.findById(createdItemEntity.getId());

        assertTrue(foundedItemEntity.isPresent());
        assertEquals(createdItemEntity.getUrl(), foundedItemEntity.get().getUrl());

        final ItemDto createdItemDto = itemModelMapper.map(createdItemEntity, ItemDto.class);

        assertEquals(createdItemDto.getOwnerId(), foundedItemEntity.get().getOwner().getId());
    }

}

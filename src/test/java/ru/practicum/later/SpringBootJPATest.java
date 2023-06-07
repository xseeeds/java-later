package ru.practicum.later;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.later.item.ItemDto;
import ru.practicum.later.item.ItemEntity;
import ru.practicum.later.item.ItemEntityRepository;
import ru.practicum.later.user.UserEntity;
import ru.practicum.later.user.UserEntityRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@DataJpaTest
//@RunWith(SpringRunner.class)
@Sql(scripts = "/projection-insert-data.sql")
@Sql(scripts = "/projection-clean-up-data.sql", executionPhase = AFTER_TEST_METHOD)
public class SpringBootJPATest {
    private final ItemEntityRepository itemEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper itemModelMapper;
    private final ModelMapper userModelMapper;

    @Test
    //@Transactional//(isolation = Isolation.SERIALIZABLE)
    public void givenItemEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        ItemDto itemDto = new ItemDto();
        itemDto.setUrl("test@url");
        itemDto.setOwnerId(2L);
        itemDto.setTags(Set.of("Mobile"));
        final ItemEntity itemEntity = itemModelMapper.map(itemDto, ItemEntity.class);
        final ItemEntity createdItemEntity = itemEntityRepository.save(itemEntity);

        final Optional<ItemEntity> foundedOptionalItemEntity =
                itemEntityRepository.findWithJoinFetch(createdItemEntity.getId());

        assertTrue(foundedOptionalItemEntity.isPresent());

        final ItemEntity foundedItemEntityEntity = foundedOptionalItemEntity.get();

        assertEquals(foundedItemEntityEntity.getUrl(), createdItemEntity.getUrl());
        assertEquals(foundedItemEntityEntity.getOwner().getId(), 2L);

        final ItemDto createdItemDto = itemModelMapper.map(foundedItemEntityEntity, ItemDto.class);

        assertEquals(createdItemDto.getOwnerId(), foundedItemEntityEntity.getOwner().getId());
        assertEquals(createdItemDto.getTags(), foundedItemEntityEntity.getTags());
    }

    @Test
    public void givenUserEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("leonardoDaVinci@email.ru");
        userEntity.setFirstName("leonardo");
        final UserEntity createdUserEntity =
                userEntityRepository.save(userEntity);
        final Optional<UserEntity> foundedUserEntity =
                userEntityRepository.findById(createdUserEntity.getId());

        assertTrue(foundedUserEntity.isPresent());
        assertEquals(userEntity, foundedUserEntity.get());
    }

    @Test
    public void whenUsingClosedProjections_thenViewWithRequiredPropertiesIsReturned() {
        //final List<ItemView> itemViews = itemEntityRepository.findAllItem();


    }

}

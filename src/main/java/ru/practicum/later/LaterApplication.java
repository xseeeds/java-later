package ru.practicum.later;

import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.practicum.later.item.ItemDto;
import ru.practicum.later.item.ItemEntity;
import ru.practicum.later.user.UserDto;
import ru.practicum.later.user.UserEntity;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@SpringBootApplication
public class LaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaterApplication.class, args);
    }


/*
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(UserEntity.class);
        metadataSources.addAnnotatedClass(ItemEntity.class);
        Metadata metadata = metadataSources.buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setOutputFile("AutoSchema.sql");
        schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
*/



    @Bean
    public ModelMapper itemModelMapper() {

        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

/*
        modelMapper.addConverter((Converter<UserEntity, OwnerView>) mappingContext -> {
                        final UserEntity source = mappingContext.getSource();
                        final OwnerView destination = mappingContext.getDestination();
                        destination.
                        return destination;
                });
        modelMapper.addConverter((Converter<CarWashDTO, CarWash>) mappingContext -> {
            CarWashDTO source = mappingContext.getSource();
            CarWash destination = mappingContext.getDestination();
            destination.setId(source.getId());
            destination.setFirstShift(source.getFirstShift() == null ? null : Time.valueOf(source.getFirstShift()));
            destination.setSecondShift(source.getSecondShift() == null ? null : Time.valueOf(source.getSecondShift()));
            destination.setEnable(true);
            destination.setAddress(source.getAddress());
            destination.setBoxCount(source.getBoxCount());
            destination.setName(source.getName());
            destination.setDateOfCreation(source.getDateOfCreation());
            return destination;
        });
*/

        modelMapper
                .createTypeMap(ItemEntity.class, ItemDto.class)
                .addMappings(mapper -> {
                    mapper.map(ItemEntity::getOwnerId, ItemDto::setOwnerId);
                });

        modelMapper
                .createTypeMap(ItemDto.class, ItemEntity.class)
                        .addMappings(mapper -> {
                            mapper.skip(ItemEntity::setOwner);
                        });
        modelMapper
                .validate();

        return modelMapper;
    }


    @Bean
    public ModelMapper userModelMapper() {

        final ModelMapper modelMapper = new ModelMapper();

        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy.MM.dd HH:mm:ss")
                .withZone(ZoneOffset.UTC);

        final Converter<Instant, String> instantToString = new AbstractConverter<>() {
            @Override
            protected String convert(Instant instant) {
                return formatter.format(instant);
            }
        };

        final Converter<String, Instant> stringToInstant = new AbstractConverter<>() {
            @Override
            protected Instant convert(String text) {
                final ZonedDateTime zonedDateTime = ZonedDateTime.parse(text, formatter);
                return zonedDateTime.toInstant();
            }
        };

        final Condition<String, String> hasEmail = ctx -> ctx.getSource() != null && !ctx.getSource().isBlank();
        final Condition<String, String> hasFirstName = ctx -> ctx.getSource() != null && !ctx.getSource().isBlank();
        final Condition<String, String> hasLastName = ctx -> ctx.getSource() != null && !ctx.getSource().isBlank();
        final Condition<String, Instant> hasRegistrationDate = ctx -> {
            final ZonedDateTime zonedDateTime;
            if (ctx.getSource() != null) {
                zonedDateTime = ZonedDateTime.parse(ctx.getSource(), formatter);
            } else {
                zonedDateTime = null;
            }
            return zonedDateTime != null && !zonedDateTime.toInstant().isBefore(Instant.now());
        };

        modelMapper
                .createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(mapper -> {
                    mapper.when(Conditions.isNull()).skip(UserEntity::getId, UserDto::setId);
                });
        modelMapper
                .addConverter(instantToString);

        modelMapper
                .createTypeMap(UserDto.class, UserEntity.class)
                .addMappings(mapper -> {
                    mapper.skip(UserEntity::setRegistrationDate);
                    mapper.skip(UserEntity::setPassword);
                    mapper.when(hasEmail).map(UserDto::getEmail, UserEntity::setEmail);
                    mapper.when(hasFirstName).map(UserDto::getFirstName, UserEntity::setFirstName);
                    mapper.when(hasLastName).map(UserDto::getLastName, UserEntity::setLastName);
                    mapper.when(hasRegistrationDate).map(UserDto::getRegistrationDate, UserEntity::setRegistrationDate);
                });
        modelMapper
                .addConverter(stringToInstant);

        modelMapper
                .validate();

        return modelMapper;

    }

/*
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer(
                "-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
*/

}

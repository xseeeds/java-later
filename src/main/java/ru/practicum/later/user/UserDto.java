package ru.practicum.later.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.later.validation.Marker;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserDto {

    @Positive(groups = Marker.OnUpdate.class)
    private Long id;

    @NotBlank(groups = Marker.OnCreate.class)
    @Email(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String email;

    @NotNull(groups = Marker.OnCreate.class)
    @NotBlank(groups = Marker.OnCreate.class)
    private String firstName;

    private String lastName;

    @Null(groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String registrationDate;

    private UserState state;

}

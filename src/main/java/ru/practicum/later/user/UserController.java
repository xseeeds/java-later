package ru.practicum.later.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/search")
    public List<UserDto> findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(@RequestBody Set<UserState> states,
                                                                                @RequestParam(value = "from") Instant from,
                                                                                @RequestParam(value = "to") Instant to) {
        // todo надо сделать ковертацию String vs Instant                                       //?from={from}&to={to}
        return userService.findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(states, from, to);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }
}

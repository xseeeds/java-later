package ru.practicum.later.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")                                                   //?from={dd.MM.yyyy}&to={dd.MM.yyyy}
    public List<UserDto> findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(@RequestBody Set<UserState> states,
                                                                                @RequestParam(value = "from") String from,
                                                                                @RequestParam(value = "to") String to) {
        return userService.findAllByStateInAndRegistrationDateBetweenOrderByIdAsc(states, from, to);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }
}

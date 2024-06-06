package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.impl.UserServiceDtoImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserServiceDtoImpl  userServiceDto;

    @PostMapping
    public UserDto add(@Valid @RequestBody UserDto userDto) {
        return userServiceDto.add(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable Long  userId) {
        return userServiceDto.findById(userId);
    }

    @GetMapping
    public List<UserDto> findAll(@PathVariable Long  userId) {
        return userServiceDto.findAll();
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userServiceDto.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userServiceDto.delete(userId);
    }
}

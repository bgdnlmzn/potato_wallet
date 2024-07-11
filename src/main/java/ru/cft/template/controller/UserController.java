package ru.cft.template.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.dto.*;
import ru.cft.template.service.UserServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("potato")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/users")
    public TokenDto register(@RequestBody @Valid UserCreateRequest body) {
        return userService.createUser(body);
    }

    @GetMapping("/users")
    public UserDto getProfile(Authentication authentication) {
        return userService.getUserResponse(authentication);
    }

    @GetMapping("users/{userId}")
    public UserShortInfo findById(@PathVariable String userId) {
        UUID uid = UUID.fromString(userId);
        return userService.findUserShortInfo(uid);
    }

    @PatchMapping("/users")
    public UserDto updateInfo(@RequestBody @Valid UserUpdateRequest body, Authentication authentication) {
        return userService.updateUser(body,authentication);
    }
}

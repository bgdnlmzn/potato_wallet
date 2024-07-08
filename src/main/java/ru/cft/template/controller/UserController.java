package ru.cft.template.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.dto.*;
import ru.cft.template.service.UserServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("potato/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/register")
    public TokenDto register(@RequestBody UserCreateRequest body) {
        log.info("pass");
        return userService.createUser(body);
    }

    @GetMapping("/myprofile")
    public UserDto getProfile(Authentication authentication) {
        log.info("pass");
        return userService.getUserResponse(authentication);
    }

    @GetMapping("/findbyid/{userId}")
    public UserShortInfo findById(@PathVariable String userId) {
        log.info("pass");
        UUID uid = UUID.fromString(userId);
        return userService.findUserShortInfo(uid);
    }

    @PatchMapping("/updateinfo")
    public UserDto updateInfo(@RequestBody UserUpdateRequest body, Authentication authentication) {
        log.info("pass");
        return userService.updateUser(body,authentication);
    }


}

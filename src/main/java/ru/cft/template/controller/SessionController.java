package ru.cft.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.dto.ActiveSessionDto;
import ru.cft.template.dto.DeleteSessionResponse;
import ru.cft.template.dto.LoginRequest;
import ru.cft.template.dto.SessionDto;
import ru.cft.template.service.SessionService;

import java.util.List;

@RestController
@RequestMapping("potato/users")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/sessions")
    public SessionDto createSession(@RequestBody LoginRequest body) {
        return sessionService.createSession(body);
    }
    @GetMapping("/sessions/now")
    public ActiveSessionDto getCurrentSession(Authentication authentication) {
        return sessionService.getSession(authentication);
    }

    @GetMapping("/sessions")
    public List<ActiveSessionDto> getSessions(Authentication authentication) {
        return sessionService.getActiveSessions(authentication);
    }

    @DeleteMapping("/sessions/{id}")
    public DeleteSessionResponse deleteSession(Authentication authentication,@PathVariable String id) {
        return sessionService.deleteSession(authentication,id);
    }
}

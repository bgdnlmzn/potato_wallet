package ru.cft.template.service;

import org.springframework.security.core.Authentication;
import ru.cft.template.dto.ActiveSessionDto;
import ru.cft.template.dto.DeleteSessionResponse;
import ru.cft.template.dto.LoginRequest;
import ru.cft.template.dto.SessionDto;

import java.util.List;

public interface SessionService {
    SessionDto createSession(LoginRequest body);

    ActiveSessionDto getSession(Authentication authentication);

    List<ActiveSessionDto> getActiveSessions(Authentication authentication);

    DeleteSessionResponse deleteSession(Authentication authentication, String id);

}

package ru.cft.template.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.dto.ActiveSessionDto;
import ru.cft.template.dto.DeleteSessionResponse;
import ru.cft.template.dto.SessionDto;
import ru.cft.template.entity.Session;

@Component
public class SessionMapper {
    public static ActiveSessionDto mapActiveSession(Session session, Boolean isActive) {
        return new ActiveSessionDto(
                session.getId(),
                session.getUser().getId(),
                session.getExpirationTime(),
                isActive
        );
    }
    public static SessionDto mapSession(Session session){
        return new SessionDto(
                session.getId(),
                session.getUser().getId(),
                session.getToken(),
                session.getExpirationTime()
        );
    }
    public static DeleteSessionResponse mapDeleteSession(String message){
        return new DeleteSessionResponse(message);
    }
}

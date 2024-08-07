package ru.cft.template.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.cft.template.dto.UserCreateRequest;
import ru.cft.template.dto.UserDto;
import ru.cft.template.dto.UserShortInfo;
import ru.cft.template.entity.User;

@Component
public class UserMapper {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User mapRegBody(UserCreateRequest body) {
        User user = new User();
        user.setMobilePhone(body.mobilePhone());
        user.setPassword(passwordEncoder.encode(body.password()));
        user.setLastName(body.lastName());
        user.setFirstName(body.firstName());
        user.setMiddleName(body.middleName());
        user.setEmail(body.email());
        user.setBirthDate(body.birthDate());
        return user;
    }
    public static UserShortInfo mapUserShortInfoBody(User user) {
        return new UserShortInfo(
                user.getMobilePhone(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()

        );
    }

    public static UserDto mapUserToResponse(User user) {
        return new UserDto(
                user.getId().toString(),
                user.getWallet().getId().toString(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getBirthDate(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

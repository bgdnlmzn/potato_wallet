package ru.cft.template.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserCreateRequest(

        @NotNull
        String mobilePhone,

        @NotNull
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?])[A-Za-z\\d!?]{8,64}$")
        String password,

        @NotNull
        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String lastName,

        @NotNull
        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String firstName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String middleName,

        @Email
        @NotNull
        String email,

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthDate
) {
}

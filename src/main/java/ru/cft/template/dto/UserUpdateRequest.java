package ru.cft.template.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserUpdateRequest(

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String firstName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String lastName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String middleName,

        LocalDate birthdate
) {
}

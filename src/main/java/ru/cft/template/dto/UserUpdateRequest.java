package ru.cft.template.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserUpdateRequest(

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String firstName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String lastName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$")
        String middleName
) {
}

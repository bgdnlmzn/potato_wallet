package ru.cft.template.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record UserUpdateRequest(

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$", message = "ФИО, только буквы русского алфавита, первая буква заглавная, не более 50 символов.")
        String firstName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$", message = "ФИО, только буквы русского алфавита, первая буква заглавная, не более 50 символов.")
        String lastName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$", message = "ФИО, только буквы русского алфавита, первая буква заглавная, не более 50 символов.")
        String middleName,

        @Past(message = "Некорректная дата рождения.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate
) {
}

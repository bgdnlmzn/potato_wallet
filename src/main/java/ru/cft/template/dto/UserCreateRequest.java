package ru.cft.template.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserCreateRequest(

        @NotNull(message = "Введите номер телефона.")
        @Pattern(regexp = "7\\d{10}", message = "Формат телефона: 11 цифр, начинается с '7' ")
        String mobilePhone,

        @NotNull
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?])[A-Za-z\\d!?]{8,64}$", message = "Пароль должен быть от 8 до 64 символов. Только латинские буквы, цифры и знаки '!?'. Обязательно наличие минимум 1 буквы верхнего и нижнего регистра, цифры и знака.")
        String password,

        @NotNull(message = "Введите свою фамилию.")
        @Pattern(regexp = "^[А-Я][а-я]{1,50}$", message = "ФИО, только буквы русского алфавита, первая буква заглавная, не более 50 символов.")
        String lastName,

        @NotNull(message = "Введите свое имя.")
        @Pattern(regexp = "^[А-Я][а-я]{1,50}$",message = "ФИО, только буквы русского алфавита, первая буква заглавная, не более 50 символов.")
        String firstName,

        @Pattern(regexp = "^[А-Я][а-я]{1,50}$",message = "ФИО, только буквы русского алфавита, первая буква заглавная, не более 50 символов.")
        String middleName,

        @Email(message = "Неверный формат почты.")
        @NotNull(message = "Введите почту")
        String email,

        @NotNull(message = "Введите дату рождения.")
        @Past(message = "Некорректная дата рождения.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthDate
) {
}

package ru.cft.template.dto;

import lombok.Builder;

@Builder
public record UserShortInfo(
        String mobilePhone,
        String email,
        String firstName,
        String lastName
) {

}

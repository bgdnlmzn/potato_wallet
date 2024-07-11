package ru.cft.template.dto;

import lombok.Builder;

@Builder
public record DeleteSessionResponse(
        String message
) {
}

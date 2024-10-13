package ru.tinkoff.edu.shortlink.web.dto;

import java.util.Collection;

public interface ResponseDto {
    record LinkCreateResponseDto(
            String fullUrl,
            String shortUrl
    ) implements ResponseDto {}
}

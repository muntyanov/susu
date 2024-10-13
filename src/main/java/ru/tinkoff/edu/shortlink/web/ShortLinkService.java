package ru.tinkoff.edu.shortlink.web;

import jakarta.validation.constraints.NotEmpty;
import ru.tinkoff.edu.shortlink.domain.AbstractLink;

import java.util.Optional;

public interface ShortLinkService {
    AbstractLink find(String shortLink);

    boolean delete(@NotEmpty String shortLink);

    Optional<Object> findAll();

    AbstractLink create(@NotEmpty String fullPath);
}

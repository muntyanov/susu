package ru.tinkoff.edu.shortlink.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.tinkoff.edu.shortlink.web.dto.LinkMapper;

import java.util.*;
import java.util.stream.Collectors;

import static ru.tinkoff.edu.shortlink.web.dto.ResponseDto.*;

@RestController
@RequiredArgsConstructor
public class ShortLinksController {

    private final ShortLinkService shortLinkService;

    private final LinkMapper mapper = LinkMapper.INSTANCE;

    @Operation(summary = "Получить короткую ссылку из длинной",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Возвращает объект созданной ссылки"),
                    @ApiResponse(responseCode = "400", description = "Пустая строка не может быть урлом"),
                    @ApiResponse(responseCode = "500", description = "Не удалось сгенерировать")
            })
    @PostMapping("/link")
    public LinkCreateResponseDto shortLinkCreate(@RequestBody @NotEmpty String fullPath) {
        try {
            return mapper.linkToLinkCreateDto(shortLinkService.create(fullPath));
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Получить ссылку из короткой",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Возвращает объект созданной ссылки"),
                    @ApiResponse(responseCode = "404", description = "Ссылка не найдена")
            })
    @GetMapping("/link/{shortLink}")
    public LinkListResponseDto shortLinkFind(@PathVariable @NotEmpty String shortLink) {
        var link = shortLinkService.find(shortLink);
        if (link == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return mapper.linkToListLinkListDto(link);
    }

    @Operation(summary = "Удалить короткую ссылку",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ссылка удалена"),
                    @ApiResponse(responseCode = "404", description = "Ссылка не найдена")
            })
    @DeleteMapping("/link/{shortLink}")
    public ResponseEntity<Void> shortLinkDelete(@PathVariable @NotEmpty String shortLink) {
        if (shortLinkService.delete(shortLink))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}

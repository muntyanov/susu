package ru.tinkoff.edu.shortlink.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.edu.shortlink.domain.AbstractLink;

import java.util.Collection;
import java.util.Collections;

import static ru.tinkoff.edu.shortlink.web.dto.ResponseDto.*;

@Mapper(imports = Collections.class)
public interface LinkMapper {

    LinkMapper INSTANCE = Mappers.getMapper(LinkMapper.class);

    LinkCreateResponseDto linkToLinkCreateDto(AbstractLink link);

    @Mapping(expression = "java(LinkMapper.fullPathOfLink(link))", target = "fullUrl")
    LinkListResponseDto linkToListLinkListDto(AbstractLink link);

    static Collection<String> fullPathOfLink(AbstractLink link){
        return Collections.singletonList(link.getFullUrl());
    }
}
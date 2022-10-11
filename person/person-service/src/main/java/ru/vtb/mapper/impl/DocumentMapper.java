package ru.vtb.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Document;


@Component
@RequiredArgsConstructor
public class DocumentMapper implements IModelMapper<Document, DocumentCreateInputDto, DocumentDto> {
    private final ModelMapper mapper;

    @Override
    public Document convertFromCreateDto(DocumentCreateInputDto createDto) {
        Document d = mapper.map(createDto, Document.class);
        d.setVisibility(true);
        return d;
    }

    @Override
    public Document convertFromUpdateDto(DocumentDto updateDto) {
        return mapper.map(updateDto, Document.class);
    }

    @Override
    public DocumentDto convertToOutputDto(Document entity) {
        return mapper.map(entity, DocumentDto.class);
    }
}

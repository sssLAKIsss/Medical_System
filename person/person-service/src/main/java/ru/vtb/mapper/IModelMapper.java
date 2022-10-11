package ru.vtb.mapper;

public interface IModelMapper<E, I, O> {

    E convertFromCreateDto(I createDto);
    E convertFromUpdateDto(O updateDto);
    O convertToOutputDto(E entity);
}

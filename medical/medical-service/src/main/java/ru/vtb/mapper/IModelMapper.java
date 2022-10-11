package ru.vtb.mapper;

public interface IModelMapper<E, D> {
    E convertToEntity(D dto);
    D convertToDto(E entity);
}

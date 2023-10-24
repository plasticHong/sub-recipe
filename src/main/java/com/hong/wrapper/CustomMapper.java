package com.hong.wrapper;

import org.modelmapper.ModelMapper;

import java.util.List;


public class CustomMapper extends ModelMapper {

    public <D> List<D> entityListToDtoList(List<?> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> map(entity, dtoClass))
                .toList();
    }

}

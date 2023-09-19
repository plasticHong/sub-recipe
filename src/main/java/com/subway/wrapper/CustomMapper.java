package com.subway.wrapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomMapper extends ModelMapper {

    public <D> List<D> entityListToDtoList(List<?> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> map(entity, dtoClass))
                .toList();
    }

}

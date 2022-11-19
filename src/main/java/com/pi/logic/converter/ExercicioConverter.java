package com.pi.logic.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.pi.model.dto.ExercicioRequest;
import com.pi.model.dto.ExercicioResponse;
import com.pi.model.entity.ExercicioEntity;

public class ExercicioConverter {
    
    public static ExercicioEntity toEntity(ExercicioRequest request) {
        return ExercicioEntity.builder()
        .nome(request.getNome())
        .musculo(request.getMusculo())
        .serie(request.getSerie())
        .repeticao(request.getRepeticao())
        .build();
    }

    public static ExercicioResponse toResponse(ExercicioEntity entity) {
        return ExercicioResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .musculo(entity.getMusculo())
                .repeticao(entity.getRepeticao())
                .serie(entity.getSerie())
                .build();
    }

    public static List<ExercicioResponse> toResponseList(List<ExercicioEntity> entityList) {
        return entityList.stream().map((entity) -> toResponse(entity)).collect(Collectors.toList());
    }

}

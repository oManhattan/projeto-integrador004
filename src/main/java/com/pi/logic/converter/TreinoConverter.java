package com.pi.logic.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.pi.model.dto.TreinoRequest;
import com.pi.model.dto.TreinoResponse;
import com.pi.model.entity.TreinoEntity;

public class TreinoConverter {

    public static TreinoEntity toEntity(TreinoRequest request) {
        return TreinoEntity.builder()
                .titulo(request.getTitulo())
                .build();
    }

    public static TreinoResponse toResponse(TreinoEntity entity) {
        return TreinoResponse.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .exercicios(ExercicioConverter.toResponseList(entity.getExercicios()))
                .build();
    }

    public static List<TreinoResponse> toResponseList(List<TreinoEntity> entityList) {
        return entityList.stream().map((entity) -> toResponse(entity)).collect(Collectors.toList());
    }

}

package com.pi.logic.converter;

import java.util.ArrayList;

import com.pi.model.dto.ProfissionalRequest;
import com.pi.model.dto.ProfissionalResponse;
import com.pi.model.entity.ProfissionalEntity;

public class ProfissionalConverter {

    public static ProfissionalEntity toEntity(ProfissionalRequest request) {
        return ProfissionalEntity.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .nome(request.getNome())
                .sobrenome(request.getSobrenome())
                .cpf(request.getCpf())
                .cnpj(request.getCnpj())
                .clientes(new ArrayList<>())
                .build();
    }

    public static ProfissionalResponse toResponse(ProfissionalEntity entity) {
        return ProfissionalResponse.profissionalResponseBuilder()
                .id(entity.getId())
                .email(entity.getEmail())
                .nome(entity.getNome())
                .sobrenome(entity.getSobrenome())
                .cpf(entity.getCpf())
                .cnpj(entity.getCnpj())
                .build();
    }
}

package com.pi.logic.converter;

import java.util.ArrayList;

import com.pi.model.dto.ClienteRequest;
import com.pi.model.dto.ClienteResponse;
import com.pi.model.entity.ClienteEntity;

public class ClienteConverter {
    
    public static ClienteEntity toEntity(ClienteRequest request) {
        return ClienteEntity.builder()
        .email(request.getEmail())
        .senha(request.getSenha())
        .nome(request.getNome())
        .sobrenome(request.getSobrenome())
        .cpf(request.getCpf())
        .dataNascimento(request.getDataNascimento())
        .genero(request.getGenero())
        .altura(request.getAltura())
        .treinos(new ArrayList<>())
        .build();
    }

    public static ClienteResponse toResponse(ClienteEntity entity) {
        return ClienteResponse.clienteResponseBuilder()
        .id(entity.getId())
        .email(entity.getEmail())
        .nome(entity.getNome())
        .sobrenome(entity.getSobrenome())
        .cpf(entity.getCpf())
        .dataNascimento(entity.getDataNascimento())
        .genero(entity.getGenero())
        .altura(entity.getAltura())
        .build();
    }

}

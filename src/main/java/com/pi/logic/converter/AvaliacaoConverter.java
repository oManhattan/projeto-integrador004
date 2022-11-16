package com.pi.logic.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.pi.model.dto.AvaliacaoRequest;
import com.pi.model.dto.AvaliacaoResponse;
import com.pi.model.entity.AvaliacaoCorporalEntity;

public class AvaliacaoConverter {

    public static AvaliacaoCorporalEntity toEntity(AvaliacaoRequest request) {

        return AvaliacaoCorporalEntity.builder()
                .dataRealizacao(request.getDataRealizacao())
                .altura(request.getAltura())
                .peso(request.getPeso())
                .musculoEsqueletico(request.getMusculoEsqueletico())
                .idadeCorporal(request.getIdadeCorporal())
                .gorduraCorporal(request.getGorduraCorporal())
                .gorduraVisceral(request.getGorduraVisceral())
                .metabolismoBasal(request.getMetabolismoBasal())
                .torax(request.getTorax())
                .cintura(request.getCintura())
                .abdome(request.getAbdomem())
                .quadril(request.getQuadril())
                .bicepsEsquerdo(request.getBicepsEsquerdo())
                .antebracoEsquerdo(request.getAntebracoEsquerdo())
                .bicepsDireito(request.getBicepsDireito())
                .antebracoDireito(request.getAntebracoDireito())
                .coxaEsquerda(request.getCoxaEsquerda())
                .panturrilhaEsquerda(request.getPanturrilhaEsquerda())
                .coxaDireita(request.getCoxaDireita())
                .panturrilhaDireita(request.getPanturrilhaDireita())
                .build();
    }

    public static AvaliacaoResponse toResponse(AvaliacaoCorporalEntity entity) {
        return AvaliacaoResponse.builder()
                .id(entity.getId())
                .dataRealizacao(entity.getDataRealizacao())
                .altura(entity.getAltura())
                .peso(entity.getPeso())
                .musculoEsqueletico(entity.getMusculoEsqueletico())
                .idadeCorporal(entity.getIdadeCorporal())
                .gorduraCorporal(entity.getGorduraCorporal())
                .gorduraVisceral(entity.getGorduraVisceral())
                .metabolismoBasal(entity.getMetabolismoBasal())
                .torax(entity.getTorax())
                .cintura(entity.getCintura())
                .abdomem(entity.getAbdome())
                .quadril(entity.getQuadril())
                .bicepsEsquerdo(entity.getBicepsEsquerdo())
                .antebracoEsquerdo(entity.getAntebracoEsquerdo())
                .bicepsDireito(entity.getBicepsDireito())
                .antebracoDireito(entity.getAntebracoDireito())
                .coxaEsquerda(entity.getCoxaEsquerda())
                .panturrilhaEsquerda(entity.getPanturrilhaEsquerda())
                .coxaDireita(entity.getCoxaDireita())
                .panturrilhaDireita(entity.getPanturrilhaDireita())
                .build();
    }

    public static List<AvaliacaoResponse> toResponseList(List<AvaliacaoCorporalEntity> entityList) {
        return entityList.stream().map((entity) -> toResponse(entity)).collect(Collectors.toList());
    }
}

package com.pi.logic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.logic.converter.AvaliacaoConverter;
import com.pi.logic.util.JWTUtil;
import com.pi.model.dto.AvaliacaoRequest;
import com.pi.model.dto.AvaliacaoResponse;
import com.pi.model.entity.AvaliacaoCorporalEntity;
import com.pi.model.entity.ClienteEntity;
import com.pi.model.repository.AvaliacaoRepository;
import com.pi.model.repository.ClienteRepository;

/**
 * Criar uma avaliação (token do profissional, id do cliente, avaliacao request)
 * Buscar todas as avaliações do cliente
 * Apagar uma avaliação
 * 
 */
@Service
public class AvaliacaoService {
    
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JWTUtil jwtUtil;

    public AvaliacaoResponse criarAvaliacao(String token, Long clienteID, AvaliacaoRequest request) throws Exception{

        String formattedToken = jwtUtil.formatToken(token);
        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorIdComProfissional(clienteID, profissionalID);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Cliente não encontrado ou não vinculao ao profissional.");
        }

        AvaliacaoCorporalEntity entity = AvaliacaoConverter.toEntity(request);
        entity.setCliente(optionalCliente.get());

        AvaliacaoResponse response = AvaliacaoConverter.toResponse(avaliacaoRepository.save(entity));
        return response;
    }

    public List<AvaliacaoResponse> encontrarListaAvaliacoes(String token) throws Exception {

        String formattedToken = jwtUtil.formatToken(token);
        Long clienteID = jwtUtil.getIdFromToken(formattedToken);

        List<AvaliacaoCorporalEntity> entityList = avaliacaoRepository.encontrarTodasAvaliacoesDoCliente(clienteID);

        return AvaliacaoConverter.toResponseList(entityList);
    }

}

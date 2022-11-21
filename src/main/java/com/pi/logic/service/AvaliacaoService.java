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

    public List<AvaliacaoResponse> encontrarListAvaliacoesPorID(String token, Long id) throws Exception {
        String formattedToken = jwtUtil.formatToken(token);
        Long profissionalID = jwtUtil.getIdFromToken(formattedToken);

        Optional<ClienteEntity> optionalCliente = clienteRepository.encontrarPorIdComProfissional(id, profissionalID);

        if (optionalCliente.isEmpty()) {
            throw new Exception("Cliente não encontrado ou não vinculado ao profissional.");
        }

        List<AvaliacaoCorporalEntity> entities = avaliacaoRepository.encontrarTodasAvaliacoesDoCliente(id);
        return AvaliacaoConverter.toResponseList(entities);
    }

    public List<AvaliacaoResponse> encontrarListaAvaliacoes(String token) throws Exception {

        String formattedToken = jwtUtil.formatToken(token);
        Long clienteID = jwtUtil.getIdFromToken(formattedToken);

        List<AvaliacaoCorporalEntity> entityList = avaliacaoRepository.encontrarTodasAvaliacoesDoCliente(clienteID);

        return AvaliacaoConverter.toResponseList(entityList);
    }

    public void apagarAvaliacao(Long id) throws Exception {
        int affectedRows = avaliacaoRepository.apagarAvaliacao(id);
        if (affectedRows < 1) {
            throw new Exception("Não foi possível apagar a avaliação corporal");
        }
    }

}   

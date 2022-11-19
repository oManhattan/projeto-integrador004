package com.pi.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pi.logic.util.Pair;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"cliente", "ultima_avaliacao", "grafico_peso", "treinos"})
public class PaginaClienteResponse {
    
    @JsonProperty("cliente")
    private ClienteResponse cliente;

    @JsonProperty("ultima_avaliacao")
    private AvaliacaoResponse ultimaAvaliacao;

    @JsonProperty("grafico_peso")
    private List<Pair<Float, LocalDate>> graficoPeso;

    @JsonProperty("treinos")
    private List<TreinoResponse> treinos;
}

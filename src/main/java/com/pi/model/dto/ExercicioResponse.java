package com.pi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({"nome", "musculo", "serie", "repeticao"})
public class ExercicioResponse {
    
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("musculo")
    private String musculo;

    @JsonProperty("serie")
    private Integer serie;

    @JsonProperty("repeticao")
    private Integer repeticao;

}

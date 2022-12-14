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
public class ExercicioRequest {
    
    @JsonProperty(namespace = "nome")
    private String nome;

    @JsonProperty(namespace = "musculo")
    private String musculo;

    @JsonProperty(namespace = "serie")
    private Integer serie;

    @JsonProperty(namespace = "repeticao")
    private Integer repeticao;

}

package com.pi.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({"titulo", "exercicios"})
public class TreinoRequest {
    
    @JsonProperty("titulo")
    private Character titulo;

    @JsonProperty("exercicios")
    private List<ExercicioRequest> exercicios;

}   

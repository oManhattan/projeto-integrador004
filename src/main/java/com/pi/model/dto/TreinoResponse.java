package com.pi.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder({"id", "titulo", "exercicios"})
public class TreinoResponse {
    
    private Long id;

    private Character titulo;

    private List<ExercicioResponse> exercicios;
}

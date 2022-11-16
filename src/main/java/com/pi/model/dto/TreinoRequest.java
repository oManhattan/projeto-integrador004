package com.pi.model.dto;

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
@JsonPropertyOrder({"titulo", "substitulo"})
public class TreinoRequest {
    
    @JsonProperty(namespace = "titulo")
    private Character titulo;

    @JsonProperty(namespace = "subtitulo")
    private String subtitulo;

}

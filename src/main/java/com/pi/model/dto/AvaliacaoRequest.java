package com.pi.model.dto;

import java.time.LocalDate;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@JsonPropertyOrder({"dataRealizacao", "altura", "peso", "musculoEsqueletico", "idadeCorporal",
	"gorduraCorporal","gorduraVisceral","metabolismoBasal","torax","cintura","abdomem","quadril",
	"bicepsEsquerdo","antebracoEsquerdo","bicepsDireito","antebracoDireito","coxaEsquerda","panturrilhaEsquerda",
	"coxaDireita","panturrilhaDireita"})
public class AvaliacaoRequest {

	/*Verificar se as medidas não serão melhores em Float do que Integer
	 * */
	
	@JsonProperty(namespace = "dataRealizacao")
	private LocalDate dataRealizacao;
	
	@JsonProperty(namespace = "altura")
	private Integer altura;
	
	@JsonProperty(namespace = "peso")
	private Float peso;
	
	@JsonProperty(namespace = "musculoEsqueletico")
	private Float musculoEsqueletico;
	
	@JsonProperty(namespace = "idadeCorporal")
	private Integer idadeCorporal;
	
	@JsonProperty(namespace = "gorduraCorporal")
	private Float gorduraCorporal;

	@JsonProperty(namespace = "gorduraVisceral")
	private Float gorduraVisceral;
	
	@JsonProperty(namespace = "metabolismoBasal")
	private Integer metabolismoBasal;
	
	// Medidas do tronco
	@JsonProperty(namespace = "torax")
	private Float torax;
	
	@JsonProperty(namespace = "cintura")
	private Float cintura;
	
	@JsonProperty(namespace = "abdomem")
	private Float abdomem;
	
	@JsonProperty(namespace = "quadril")
	private Float quadril;
	
	// Medidas braços
	@JsonProperty(namespace = "bicepsEsquerdo")
	private Float bicepsEsquerdo;
	
	@JsonProperty(namespace = "antebracoEsquerdo")
	private Float antebracoEsquerdo;
	
	@JsonProperty(namespace = "bicepsDireito")
	private Float bicepsDireito;
	
	@JsonProperty(namespace = "antebracoDireito")
	private Float antebracoDireito;
	
	// Medidas membros inferiores
	@JsonProperty(namespace = "coxaEsquerda")
	private Float coxaEsquerda;
	
	@JsonProperty(namespace = "panturrilhaEsquerda")
	private Float panturrilhaEsquerda;
	
	@JsonProperty(namespace = "coxaDireita")
	private Float coxaDireita;
	
	@JsonProperty(namespace = "panturrilhaDireita")
	private Float panturrilhaDireita;
	
	

}

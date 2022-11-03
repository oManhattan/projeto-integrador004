package com.pi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonPropertyOrder({"email", "password"})
public class LoginRequest {

    @JsonProperty(namespace = "email", required = true)
    private String email;

    @JsonProperty(namespace = "password", required = true)
    private String password;
}

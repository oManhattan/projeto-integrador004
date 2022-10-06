package com.pi.model.entity;

public enum GeneroPessoa {
    MASCULINO("Masculino"),
    FEMININO("Feminino");

    private final String genero;

    private GeneroPessoa(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}

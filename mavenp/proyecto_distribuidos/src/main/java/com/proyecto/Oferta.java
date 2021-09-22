package com.proyecto;

import java.io.Serializable;

public class Oferta extends Object implements Serializable {

    private String titulo;
    private String sector;
    private Integer codigo;
    private Integer experiencia;
    private Integer edad;
    private String formacion_academica;

    public Oferta(String titulo, String sector, Integer codigo, Integer experiencia, Integer edad,
            String formacion_academica) {
        this.titulo = titulo;
        this.sector = sector;
        this.codigo = codigo;
        this.experiencia = experiencia;
        this.edad = edad;
        this.formacion_academica = formacion_academica;
    }
    public String getTitulo(){
        return this.titulo;
    }


}

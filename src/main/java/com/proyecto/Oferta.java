package com.proyecto;

import java.io.Serializable;

public class Oferta extends Object implements Serializable {

    private String titulo;
    private String sector;
    private String codigo;
    private Integer experiencia;
    private Integer edad;
    private String formacion_academica;

    public Oferta(String titulo, String sector, String codigo, Integer experiencia, Integer edad,
            String formacion_academica) {
        this.titulo = titulo;
        this.sector = sector;
        this.codigo = codigo;
        this.experiencia = experiencia;
        this.edad = edad;
        this.formacion_academica = formacion_academica;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSector() {
        return this.sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getExperiencia() {
        return this.experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getFormacion_academica() {
        return this.formacion_academica;
    }

    public void setFormacion_academica(String formacion_academica) {
        this.formacion_academica = formacion_academica;
    }

}

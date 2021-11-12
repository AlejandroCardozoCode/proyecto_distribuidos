package com.proyecto;

import java.io.Serializable;
import java.util.Objects;

public class Oferta extends Object implements Serializable {

    private String titulo;
    private String sector;
    private String codigo;
    private Integer experiencia;
    private Integer edad;
    private String formacion_academica;
    private Integer sectorCodigo;
    private String estado;
    private String nombreAspiratne;

    public Oferta(String titulo, String sector, String codigo, Integer experiencia, Integer edad,
            String formacion_academica, Integer sectorCodigo) {
        this.titulo = titulo;
        this.sector = sector;
        this.codigo = codigo;
        this.experiencia = experiencia;
        this.edad = edad;
        this.formacion_academica = formacion_academica;
        this.sectorCodigo = sectorCodigo;
        this.estado = "disponible";
        this.nombreAspiratne = "";
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getNombreAspiratne() {
        return this.nombreAspiratne;
    }

    public void setNombreAspiratne(String nombreAspiratne) {
        this.nombreAspiratne = nombreAspiratne;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getSectorCodigo() {
        return this.sectorCodigo;
    }

    public void setSectorCodigo(Integer sectorCodigo) {
        this.sectorCodigo = sectorCodigo;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Oferta)) {
            return false;
        }
        Oferta oferta = (Oferta) o;
        return Objects.equals(titulo, oferta.titulo) && Objects.equals(sector, oferta.sector)
                && Objects.equals(codigo, oferta.codigo) && Objects.equals(experiencia, oferta.experiencia)
                && Objects.equals(edad, oferta.edad) && Objects.equals(formacion_academica, oferta.formacion_academica)
                && Objects.equals(sectorCodigo, oferta.sectorCodigo) && Objects.equals(estado, oferta.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, sector, codigo, experiencia, edad, formacion_academica, sectorCodigo, estado);
    }

}
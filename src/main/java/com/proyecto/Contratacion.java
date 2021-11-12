package com.proyecto;

import java.io.Serializable;
import java.util.Objects;

public class Contratacion implements Serializable {
    private String idOferta;
    private String nombreCliente;

    public Contratacion() {
    }

    public Contratacion(String idOferta, String nombreCliente) {
        this.idOferta = idOferta;
        this.nombreCliente = nombreCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Contratacion)) {
            return false;
        }
        Contratacion contratacion = (Contratacion) o;
        return Objects.equals(idOferta, contratacion.idOferta)
                && Objects.equals(nombreCliente, contratacion.nombreCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOferta, nombreCliente);
    }

    @Override
    public String toString() {
        return "{" + " idOferta='" + getIdOferta() + "'" + ", nombreCliente='" + getNombreCliente() + "'" + "}";
    }

    public String getIdOferta() {
        return this.idOferta;
    }

    public void setIdOferta(String idOferta) {
        this.idOferta = idOferta;
    }

    public String getNombreCliente() {
        return this.nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}
public class Oferta {
    String titulo;
    int codigo;
    String sector;
    int experiencia;
    int edad;
    String formacion_academica;

    public void crear_oferta(String titulo, int codigo, String sector, int experiencia, int edad,
            String formacion_academica) {

        this.titulo = titulo;
        this.codigo = codigo;
        this.edad = edad;
        this.experiencia = experiencia;
        this.sector = sector;
        this.formacion_academica = formacion_academica;
    }

    @Override
    public String toString() {
        return "{" + " titulo='" + this.titulo + "'" + "codigo='" + this.codigo + "'" + " sector='" + this.sector + "'"
                + ", experiencia='" + Integer.toString(this.experiencia) + "'" + ", edad='"
                + Integer.toString(this.edad) + "'" + "formacion_academica='" + this.formacion_academica + "'" + "}";
    }

}

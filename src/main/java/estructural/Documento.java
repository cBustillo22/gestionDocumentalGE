package estructural;

import javax.print.Doc;
import java.util.Date;

public class Documento {

    private String carpetaRaiz;
    private String nombre;
    private String autor;
    private double tamanio;
    private String tipo;
    private String descripcion;
    private Date fechaCreacion;
    private String estado;

    public Documento(String carpetaRaiz, String nombre, String autor, double tamanio, String tipo, String descripcion, Date fechaCreacion, String estado) {
        this.carpetaRaiz = carpetaRaiz;
        this.nombre = nombre;
        this.autor = autor;
        this.tamanio = tamanio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Documento(){}

    public String getCarpetaRaiz() {
        return carpetaRaiz;
    }

    public void setCarpetaRaiz(String carpetaRaiz) {
        this.carpetaRaiz = carpetaRaiz;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getTamanio() {
        return tamanio;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "carpetaRaiz='" + carpetaRaiz + '\'' +
                ", nombre='" + nombre + '\'' +
                ", autor='" + autor + '\'' +
                ", tamanio=" + tamanio +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estado='" + estado + '\'' +
                '}';
    }
}

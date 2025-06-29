package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa un servicio que se puede aplicar a un vehículo en el taller.
 */
@XmlRootElement(name = "servicio")
public class Servicio {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private double costoManoObra;

    // Constructor vacío requerido por JAXB
    public Servicio() {
    }

    public Servicio(int id, String nombre, String descripcion, double precio, double costoManoObra) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.costoManoObra = costoManoObra;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public String getNombre() {
        return nombre;
    }

    @XmlElement
    public String getDescripcion() {
        return descripcion;
    }

    @XmlElement
    public double getPrecio() {
        return precio;
    }

    @XmlElement
    public double getCostoManoObra() {
        return costoManoObra;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", costoManoObra=" + costoManoObra +
                '}';
    }
}

package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "repuesto")
public class Repuesto {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadDisponible;
    private boolean pedido;

    public Repuesto() {
    }

    public Repuesto(int id, String nombre, String descripcion, double precio, int cantidadDisponible, boolean pedido) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
        this.pedido = pedido;
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
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    @XmlElement
    public boolean isPedido() {
        return pedido;
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

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setPedido(boolean pedido) {
        this.pedido = pedido;
    }
}

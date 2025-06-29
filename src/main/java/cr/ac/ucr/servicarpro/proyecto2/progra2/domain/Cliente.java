package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cliente")
public class Cliente {

    private int id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;
    private String direccion;
    private String email;

    // Constructor vacío requerido por JAXB
    public Cliente() {
    }

    public Cliente(int id, String nombre, String primerApellido, String segundoApellido,
                   String telefono, String direccion, String email) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
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
    public String getPrimerApellido() {
        return primerApellido;
    }

    @XmlElement
    public String getSegundoApellido() {
        return segundoApellido;
    }

    @XmlElement
    public String getTelefono() {
        return telefono;
    }

    @XmlElement
    public String getDireccion() {
        return direccion;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

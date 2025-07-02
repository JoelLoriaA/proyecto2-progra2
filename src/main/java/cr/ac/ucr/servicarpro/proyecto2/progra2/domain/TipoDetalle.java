package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

public class TipoDetalle {
    private int id;
    private String nombre;

    public TipoDetalle() {}

    public TipoDetalle(String nombre) {
        this.nombre = nombre;
    }

    public TipoDetalle(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
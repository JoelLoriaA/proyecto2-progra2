package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdenDeTrabajo {
    private int idOrden;
    private String descripcionSolicitud;
    private LocalDate fechaIngreso;
    private Estado estado; // "Diagnóstico", "En reparación", "Listo para entrega"
    private String numeroPlaca;
    private int idCliente;
    private String observacionesRecepcion;
    private List<DetalleOrden> detalles;

    // Constructor vacío
    public OrdenDeTrabajo() {
        this.detalles = new ArrayList<>();
    }


    public OrdenDeTrabajo(String descripcionSolicitud, LocalDate fechaIngreso, Estado estado, String numeroPlaca, int idCliente, String observacionesRecepcion, List<DetalleOrden> detalles) {
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.numeroPlaca = numeroPlaca;
        this.idCliente = idCliente;
        this.observacionesRecepcion = observacionesRecepcion;
        this.detalles = detalles;
    }

    public OrdenDeTrabajo(int codigoOrden, String descripcionSolicitud, LocalDate fechaIngreso, Estado estado, String numeroPlaca, int idCliente, String observacionesRecepcion, List<DetalleOrden> detalles) {
        this.idOrden = codigoOrden;
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.numeroPlaca = numeroPlaca;
        this.idCliente = idCliente;
        this.observacionesRecepcion = observacionesRecepcion;
        this.detalles = detalles;
    }

    // Getters y Setters
    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getObservacionesRecepcion() {
        return observacionesRecepcion;
    }

    public void setObservacionesRecepcion(String observacionesRecepcion) {
        this.observacionesRecepcion = observacionesRecepcion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrdenDeTrabajo that = (OrdenDeTrabajo) o;
        return getIdOrden() == that.getIdOrden() && getIdCliente() == that.getIdCliente() && Objects.equals(getDescripcionSolicitud(), that.getDescripcionSolicitud()) && Objects.equals(getFechaIngreso(), that.getFechaIngreso()) && Objects.equals(getEstado(), that.getEstado()) && Objects.equals(getNumeroPlaca(), that.getNumeroPlaca()) && Objects.equals(getObservacionesRecepcion(), that.getObservacionesRecepcion()) && Objects.equals(getDetalles(), that.getDetalles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrden(), getDescripcionSolicitud(), getFechaIngreso(), getEstado(), getNumeroPlaca(), getIdCliente(), getObservacionesRecepcion(), getDetalles());
    }

    @Override
    public String toString() {
        return "OrdenDeTrabajo{" +
                "idOrden='" + idOrden + '\'' +
                ", descripcionSolicitud='" + descripcionSolicitud + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", estado='" + estado + '\'' +
                ", codigoVehiculo='" + numeroPlaca + '\'' +
                ", codigoCliente='" + idCliente + '\'' +
                ", observacionesRecepcion='" + observacionesRecepcion + '\'' +
                ", detalles=" + detalles.size() + " items" +
                '}';
    }

    public List<DetalleOrden> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrden> detalles) {
        this.detalles = detalles;
    }
}
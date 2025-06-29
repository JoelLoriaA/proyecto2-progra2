package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdenDeTrabajo {
    private String idOrden;
    private String descripcionSolicitud;
    private LocalDate fechaIngreso;
    private Estado estado; // "Diagnóstico", "En reparación", "Listo para entrega"
    private String codigoVehiculo;
    private String codigoCliente;
    private String observacionesRecepcion;
    private List<DetalleOrden> detalles;

    // Constructor vacío
    public OrdenDeTrabajo() {
        this.detalles = new ArrayList<>();
    }


    public OrdenDeTrabajo(String descripcionSolicitud, LocalDate fechaIngreso, Estado estado, String codigoVehiculo, String codigoCliente, String observacionesRecepcion, List<DetalleOrden> detalles) {
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.codigoVehiculo = codigoVehiculo;
        this.codigoCliente = codigoCliente;
        this.observacionesRecepcion = observacionesRecepcion;
        this.detalles = detalles;
    }

    public OrdenDeTrabajo(String codigoOrden, String descripcionSolicitud, LocalDate fechaIngreso, Estado estado, String codigoVehiculo, String codigoCliente, String observacionesRecepcion, List<DetalleOrden> detalles) {
        this.idOrden = codigoOrden;
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.codigoVehiculo = codigoVehiculo;
        this.codigoCliente = codigoCliente;
        this.observacionesRecepcion = observacionesRecepcion;
        this.detalles = detalles;
    }

    // Getters y Setters
    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getObservacionesRecepcion() {
        return observacionesRecepcion;
    }

    public void setObservacionesRecepcion(String observacionesRecepcion) {
        this.observacionesRecepcion = observacionesRecepcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdenDeTrabajo that = (OrdenDeTrabajo) o;
        return Objects.equals(idOrden, that.idOrden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrden);
    }

    @Override
    public String toString() {
        return "OrdenDeTrabajo{" +
                "idOrden='" + idOrden + '\'' +
                ", descripcionSolicitud='" + descripcionSolicitud + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", estado='" + estado + '\'' +
                ", codigoVehiculo='" + codigoVehiculo + '\'' +
                ", codigoCliente='" + codigoCliente + '\'' +
                ", observacionesRecepcion='" + observacionesRecepcion + '\'' +
                ", detalles=" + detalles.size() + " items" +
                '}';
    }
}
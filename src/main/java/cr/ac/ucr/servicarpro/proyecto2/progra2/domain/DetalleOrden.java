package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

public class DetalleOrden {
    private int idDetalleOrden;
    private int cantidad;
    private String observaciones;
    private int idOrdenTrabajo;
    private TipoDetalle tipoDetalle;
    private Estado estado;
    private double precio;
    private String nombreRepuesto;
    private boolean repuestoPedido;
    private double costoManoObra;

    public DetalleOrden() {
    }

    public DetalleOrden(int cantidad, String observaciones, int idOrdenTrabajo, TipoDetalle tipoDetalle, Estado estado, double precio, String nombreRepuesto, boolean repuestoPedido, double costoManoObra) {
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.tipoDetalle = tipoDetalle;
        this.estado = estado;
        this.precio = precio;
        this.nombreRepuesto = nombreRepuesto;
        this.repuestoPedido = repuestoPedido;
        this.costoManoObra = costoManoObra;
    }

    public DetalleOrden(int idDetalleOrden, int cantidad, String observaciones, int idOrdenTrabajo,
                        TipoDetalle tipoDetalle, Estado estado, double precio, String nombreRepuesto,
                        boolean repuestoPedido, double costoManoObra) {
        this.idDetalleOrden = idDetalleOrden;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.tipoDetalle = tipoDetalle;
        this.estado = estado;
        this.precio = precio;
        this.nombreRepuesto = nombreRepuesto;
        this.repuestoPedido = repuestoPedido;
        this.costoManoObra = costoManoObra;
    }

    public int getIdDetalleOrden() {
        return idDetalleOrden;
    }

    public void setIdDetalleOrden(int idDetalleOrden) {
        this.idDetalleOrden = idDetalleOrden;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(int idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public TipoDetalle getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(TipoDetalle tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }

    public boolean isRepuestoPedido() {
        return repuestoPedido;
    }

    public void setRepuestoPedido(boolean repuestoPedido) {
        this.repuestoPedido = repuestoPedido;
    }

    public double getCostoManoObra() {
        return costoManoObra;
    }

    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }
}
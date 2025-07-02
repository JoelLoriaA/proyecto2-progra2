/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.servicarpro.proyecto2.progra2.servicies;


import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.DetalleOrdenDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.DetalleOrden;
import org.jdom2.JDOMException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DetalleOrdenService {
    private final DetalleOrdenDAO detalleOrdenDAO;

    public DetalleOrdenService() throws JDOMException, IOException {
        this.detalleOrdenDAO = new DetalleOrdenDAO();
    }

    public void agregarDetalle(DetalleOrden detalle) throws IOException {
        if (detalle.getIdDetalleOrden() == 0) {
            detalle.setIdDetalleOrden(detalleOrdenDAO.getNextId());
        }
        detalleOrdenDAO.insertOrUpdate(detalle);
    }

    public void modificarDetalle(DetalleOrden detalle) throws IOException {
        detalleOrdenDAO.edit(detalle);
    }

    public void eliminarDetalle(int idDetalleOrden) throws IOException {
        List<DetalleOrden> detalles = detalleOrdenDAO.findAll()
            .stream()
            .filter(d -> d.getIdDetalleOrden() != idDetalleOrden)
            .collect(Collectors.toList());
        // Limpiar y reinsertar todos menos el eliminado
        for (DetalleOrden detalle : detalles) {
            detalleOrdenDAO.insertOrUpdate(detalle);
        }
    }

    public List<DetalleOrden> listarDetallesPorOrden(int idOrdenTrabajo) {
        return detalleOrdenDAO.findAll()
            .stream()
            .filter(d -> d.getIdOrdenTrabajo() == idOrdenTrabajo)
            .collect(Collectors.toList());
    }
}
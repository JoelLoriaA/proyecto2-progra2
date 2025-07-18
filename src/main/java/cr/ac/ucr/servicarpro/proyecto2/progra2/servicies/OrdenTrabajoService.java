
package cr.ac.ucr.servicarpro.proyecto2.progra2.servicies;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.OrdenDeTrabajoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.VehiculoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.XmlRepositoryException;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.OrdenDeTrabajo;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Estado;

public class OrdenTrabajoService {
    private OrdenDeTrabajoDAO ordenDAO;
    private VehiculoDAO vehiculoDAO;
    private ClienteDAO clienteDAO;

    public OrdenTrabajoService() {
        try {
            this.ordenDAO = new OrdenDeTrabajoDAO();
            this.vehiculoDAO = new VehiculoDAO();
            this.clienteDAO = new ClienteDAO();
        } catch (Exception e) {
            throw new XmlRepositoryException("Error inicializando OrdenTrabajoService", e);
        }
    }

    public List<OrdenDeTrabajo> listarOrdenes() {
        return ordenDAO.findAll();
    }

    public OrdenDeTrabajo buscarPorId(int idOrden) {
        return ordenDAO.findByKey(idOrden).orElse(null);
    }

    public void crearOrden(OrdenDeTrabajo orden) throws IOException {
        try {
            validarNuevaOrden(orden);

            // Autogenerar ID usando getNextId()
            int nuevoId = ordenDAO.getNextId();
            orden.setIdOrden(nuevoId);

            // Establecer estado inicial y fecha de ingreso
            orden.setEstado(new Estado(1, "Diagnóstico"));
            orden.setFechaIngreso(LocalDate.now());

            ordenDAO.insertOrUpdate(orden);
        } catch (XmlRepositoryException e) {
            throw new XmlRepositoryException("Error creando orden de trabajo", e);
        }
    }

    public void actualizarOrden(OrdenDeTrabajo orden) throws IOException {
        try {
            validarOrdenExiste(orden.getIdOrden());
            validarOrden(orden);
            ordenDAO.edit(orden);
        } catch (XmlRepositoryException e) {
            throw new XmlRepositoryException("Error actualizando orden de trabajo", e);
        }
    }

    public void cambiarEstadoOrden(int idOrden, Estado nuevoEstado) throws IOException {
        try {
            OrdenDeTrabajo orden = buscarPorId(idOrden);
            if (orden == null) {
                throw new IllegalArgumentException("No existe orden con ID: " + idOrden);
            }

            validarCambioEstado(orden.getEstado(), nuevoEstado);
            orden.setEstado(nuevoEstado);
            ordenDAO.edit(orden);
        } catch (XmlRepositoryException e) {
            throw new XmlRepositoryException("Error cambiando estado de orden", e);
        }
    }

    public void borrarOrden(int idOrden) {
        try {
            validarOrdenExiste(idOrden);
            ordenDAO.delete(idOrden);
        } catch (Exception e) {
            throw new XmlRepositoryException("Error borrando orden de trabajo", e);
        }
    }

    // Validaciones de negocio
    private void validarNuevaOrden(OrdenDeTrabajo orden) {
        validarOrden(orden);

        // Validar que el vehículo existe por número de placa
        if (vehiculoDAO.findByKey(orden.getNumeroPlaca()).isEmpty()) {
            throw new IllegalArgumentException("No existe vehículo con placa: " + orden.getNumeroPlaca());
        }

        // Validar que el cliente existe por ID (cédula)
        if (clienteDAO.findByKey(orden.getIdCliente()).isEmpty()) {
            throw new IllegalArgumentException("No existe cliente con ID: " + orden.getIdCliente());
        }

        // Validar que no hay órdenes activas para el mismo vehículo
        boolean tieneOrdenActiva = ordenDAO.findAll().stream()
                .anyMatch(o -> o.getNumeroPlaca().equals(orden.getNumeroPlaca())
                        && (o.getEstado().getId() == 1 || o.getEstado().getId() == 2)); // Diagnóstico o En reparación

        if (tieneOrdenActiva) {
            throw new IllegalArgumentException("El vehículo ya tiene una orden de trabajo activa");
        }
    }

    private void validarOrden(OrdenDeTrabajo orden) {
        if (orden == null) {
            throw new IllegalArgumentException("La orden no puede ser nula");
        }

        if (orden.getNumeroPlaca() == null || orden.getNumeroPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de placa es obligatorio");
        }

        if (orden.getIdCliente() <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser mayor a 0");
        }

        if (orden.getDescripcionSolicitud() == null || orden.getDescripcionSolicitud().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la solicitud es obligatoria");
        }

        if (orden.getObservacionesRecepcion() == null) {
            orden.setObservacionesRecepcion(""); // Valor por defecto
        }
    }

    private void validarOrdenExiste(int idOrden) {
        if (ordenDAO.findByKey(idOrden).isEmpty()) {
            throw new IllegalArgumentException("No existe orden con ID: " + idOrden);
        }
    }

    private void validarCambioEstado(Estado estadoActual, Estado nuevoEstado) {
        int estadoActualId = estadoActual.getId();
        int nuevoEstadoId = nuevoEstado.getId();
        if (estadoActualId == 5 || estadoActualId == 6) {
            throw new IllegalArgumentException("No se puede cambiar el estado de una orden " +
                    (estadoActualId == 5 ? "Entregada" : "Cancelada"));
        }

        // Validar transiciones válidas
        switch (estadoActualId) {
            case 1: // Diagnóstico
                if (nuevoEstadoId != 2 && nuevoEstadoId != 6) {
                    throw new IllegalArgumentException("Desde Diagnóstico solo se puede cambiar a En reparación o Cancelado");
                }
                break;

            case 2: // En reparación
                if (nuevoEstadoId != 3 && nuevoEstadoId != 4 && nuevoEstadoId != 6) {
                    throw new IllegalArgumentException("Desde En reparación solo se puede cambiar a Esperando repuestos, Listo para entrega o Cancelado");
                }
                break;

            case 3: // Esperando repuestos
                if (nuevoEstadoId != 2 && nuevoEstadoId != 4 && nuevoEstadoId != 6) {
                    throw new IllegalArgumentException("Desde Esperando repuestos solo se puede cambiar a En reparación, Listo para entrega o Cancelado");
                }
                break;

            case 4: // Listo para entrega
                if (nuevoEstadoId != 5 && nuevoEstadoId != 6) {
                    throw new IllegalArgumentException("Desde Listo para entrega solo se puede cambiar a Entregado o Cancelado");
                }
                break;

            default:
                throw new IllegalArgumentException("Estado actual no válido: " + estadoActualId);
        }
    }
}

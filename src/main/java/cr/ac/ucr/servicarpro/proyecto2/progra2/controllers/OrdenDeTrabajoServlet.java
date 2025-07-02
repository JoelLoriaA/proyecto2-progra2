package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.RepuestoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ServicioDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.VehiculoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*;
import cr.ac.ucr.servicarpro.proyecto2.progra2.servicies.OrdenTrabajoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrdenDeTrabajoServlet", urlPatterns = {"/OrdenDeTrabajoServlet"})
public class OrdenDeTrabajoServlet extends HttpServlet {

    private OrdenTrabajoService ordenService;
    private RepuestoDAO repuestoDAO;
    private ServicioDAO servicioDAO;
    private VehiculoDAO vehiculoDAO;
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        try {
            ordenService = new OrdenTrabajoService();
            repuestoDAO = new RepuestoDAO();
            servicioDAO = new ServicioDAO();
            vehiculoDAO = new VehiculoDAO();
            clienteDAO = new ClienteDAO();
        } catch (Exception e) {
            throw new ServletException("Error inicializando DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "list";

        try {
            switch (action) {
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "detalle":
                    mostrarDetalle(request, response);
                    break;
                case "cambiarEstado":
                    cambiarEstado(request, response);
                    break;
                case "eliminar":
                    eliminarOrden(request, response);
                    break;
                default:
                    listarOrdenes(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error procesando acción: " + action, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            boolean esNueva = idParam == null || idParam.trim().isEmpty();

            if (esNueva) {
                crearOrden(request, response);
            } else {
                actualizarOrden(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error guardando orden", e);
        }
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarDatosFormulario(request);
        request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        OrdenDeTrabajo orden = ordenService.buscarPorId(id);

        if (orden == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada");
            return;
        }

        cargarDatosFormulario(request);
        request.setAttribute("orden", orden);
        request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
    }

    private void cargarDatosFormulario(HttpServletRequest request) {
        request.setAttribute("vehiculos", vehiculoDAO.findAll());
        request.setAttribute("clientes", clienteDAO.findAll());
        request.setAttribute("repuestos", repuestoDAO.findAll());
        request.setAttribute("servicios", servicioDAO.findAll());
    }

    private void crearOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrdenDeTrabajo orden = construirOrdenDesdeRequest(request);

        try {
            ordenService.crearOrden(orden);
            response.sendRedirect("OrdenDeTrabajoServlet");
        } catch (Exception e) {
            cargarDatosFormulario(request);
            request.setAttribute("orden", orden);
            request.setAttribute("error", "Error creando orden: " + e.getMessage());
            request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
        }
    }

    private void actualizarOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        OrdenDeTrabajo orden = construirOrdenDesdeRequest(request);
        orden.setIdOrden(id);

        try {
            ordenService.actualizarOrden(orden);
            response.sendRedirect("OrdenDeTrabajoServlet");
        } catch (Exception e) {
            cargarDatosFormulario(request);
            request.setAttribute("orden", orden);
            request.setAttribute("error", "Error actualizando orden: " + e.getMessage());
            request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
        }
    }

    private OrdenDeTrabajo construirOrdenDesdeRequest(HttpServletRequest request) {
        OrdenDeTrabajo orden = new OrdenDeTrabajo();

        orden.setDescripcionSolicitud(request.getParameter("descripcionSolicitud"));
        orden.setNumeroPlaca(request.getParameter("numeroPlaca"));
        orden.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
        orden.setObservacionesRecepcion(request.getParameter("observacionesRecepcion"));

        // Procesar detalles
        List<DetalleOrden> detalles = new ArrayList<>();
        String[] tiposDetalle = request.getParameterValues("tipoDetalle");

        if (tiposDetalle != null) {
            for (int i = 0; i < tiposDetalle.length; i++) {
                String tipoParam = tiposDetalle[i];
                String itemIdParam = request.getParameterValues("itemId")[i];
                String cantidadParam = request.getParameterValues("cantidad")[i];
                String observacionesParam = request.getParameterValues("observacionesDetalle")[i];

                if (itemIdParam != null && !itemIdParam.trim().isEmpty() &&
                        cantidadParam != null && !cantidadParam.trim().isEmpty()) {

                    DetalleOrden detalle = new DetalleOrden();
                    detalle.setCantidad(Integer.parseInt(cantidadParam));
                    detalle.setObservaciones(observacionesParam != null ? observacionesParam : "");

                    if ("repuesto".equals(tipoParam)) {
                        // Es un repuesto
                        int repuestoId = Integer.parseInt(itemIdParam);
                        Repuesto repuesto = repuestoDAO.findById(repuestoId);

                        detalle.setTipoDetalle(new TipoDetalle(1, "Repuesto"));
                        detalle.setNombreRepuesto(repuesto.getNombre());
                        detalle.setPrecio(repuesto.getPrecio());
                        detalle.setRepuestoPedido(repuesto.isPedido());
                        detalle.setCostoManoObra(0.0);

                    } else if ("servicio".equals(tipoParam)) {
                        // Es un servicio
                        int servicioId = Integer.parseInt(itemIdParam);
                        Servicio servicio = servicioDAO.findById(servicioId);

                        detalle.setTipoDetalle(new TipoDetalle(2, "Servicio"));
                        detalle.setNombreRepuesto(servicio.getNombre());
                        detalle.setPrecio(servicio.getPrecio());
                        detalle.setRepuestoPedido(false);
                        detalle.setCostoManoObra(servicio.getCostoManoObra());
                    }

                    detalle.setEstado(new Estado(1, "Pendiente"));
                    detalles.add(detalle);
                }
            }
        }

        orden.setDetalles(detalles);
        return orden;
    }

   private void cambiarEstado(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       try {
           int id = Integer.parseInt(request.getParameter("id"));
           int estadoId = Integer.parseInt(request.getParameter("estado"));

           // Obtener la orden actual
           OrdenDeTrabajo orden = ordenService.buscarPorId(id);
           if (orden == null) {
               response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada");
               return;
           }

           // Validar transición
           if (!esTransicionValida(orden.getEstado().getId(), estadoId)) {
               response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                   "No se puede cambiar el estado de una orden " + orden.getEstado().getDescripcion() +
                   " al estado " + obtenerEstadoPorId(estadoId).getDescripcion());
               return;
           }

           // Crear nuevo estado
           Estado nuevoEstado = obtenerEstadoPorId(estadoId);

           // Cambiar estado en el servicio
           ordenService.cambiarEstadoOrden(id, nuevoEstado);

           // Si se marca como entregada (estado 5), actualizar stock de repuestos
           if (estadoId == 5) {
               actualizarStockRepuestos(id);
           }

           // Redirigir a la página de detalle
           response.sendRedirect("OrdenDeTrabajoServlet?action=detalle&id=" + id);

       } catch (NumberFormatException e) {
           response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
       } catch (Exception e) {
           e.printStackTrace();
           response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
       }
   }

   private boolean esTransicionValida(int estadoActual, int nuevoEstado) {
       // No permitir cambio al mismo estado
       if (estadoActual == nuevoEstado) {
           return false;
       }

       switch (estadoActual) {
           case 1: // Recibida
               return nuevoEstado == 2 || nuevoEstado == 6; // Solo a "En reparación" o "Cancelado"
           case 2: // En reparación
               return nuevoEstado == 3 || nuevoEstado == 4 || nuevoEstado == 6; // A "En espera", "Listo" o "Cancelado"
           case 3: // En espera de repuestos
               return nuevoEstado == 2 || nuevoEstado == 4 || nuevoEstado == 6; // A "En reparación", "Listo" o "Cancelado"
           case 4: // Listo para entrega
               return nuevoEstado == 5 || nuevoEstado == 6; // Solo a "Entregado" o "Cancelado"
           case 5: // Entregado
           case 6: // Cancelado
               return false; // Estados finales, no se pueden cambiar
           default:
               return false;
       }
   }

    private void actualizarStockRepuestos(int idOrden) throws IOException {
        OrdenDeTrabajo orden = ordenService.buscarPorId(idOrden);

        if (orden != null && orden.getDetalles() != null) {
            for (DetalleOrden detalle : orden.getDetalles()) {
                // Solo actualizar stock si es un repuesto
                if (detalle.getTipoDetalle().getId() == 1) {
                    // Buscar el repuesto por nombre
                    List<Repuesto> repuestos = repuestoDAO.findAll();
                    for (Repuesto repuesto : repuestos) {
                        if (repuesto.getNombre().equals(detalle.getNombreRepuesto())) {
                            int nuevaCantidad = repuesto.getCantidadDisponible() - detalle.getCantidad();

                            if (nuevaCantidad <= 0) {
                                // Eliminar repuesto si no queda stock
                                repuestoDAO.delete(repuesto.getId());
                            } else {
                                // Actualizar cantidad
                                repuesto.setCantidadDisponible(nuevaCantidad);
                                repuestoDAO.save(repuesto);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

   private Estado obtenerEstadoPorId(int estadoId) {
       switch (estadoId) {
           case 1: return new Estado(1, "Recibida");
           case 2: return new Estado(2, "En reparación");
           case 3: return new Estado(3, "En espera de repuestos");
           case 4: return new Estado(4, "Listo para entrega");
           case 5: return new Estado(5, "Entregado");
           case 6: return new Estado(6, "Cancelado");
           default: throw new IllegalArgumentException("Estado no válido: " + estadoId);
       }
   }

    private void mostrarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        OrdenDeTrabajo orden = ordenService.buscarPorId(id);

        if (orden == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada");
            return;
        }

        // Determinar si se puede editar (estados 1, 2, 3, 4)
        boolean puedeEditar = orden.getEstado().getId() < 5;

        request.setAttribute("orden", orden);
        request.setAttribute("puedeEditar", puedeEditar);
        request.getRequestDispatcher("ordenes/detalle.jsp").forward(request, response);
    }

    private void eliminarOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            ordenService.borrarOrden(id);
            response.sendRedirect("OrdenDeTrabajoServlet");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error eliminando orden: " + e.getMessage());
        }
    }

    private void listarOrdenes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filtro = request.getParameter("filtro");
        List<OrdenDeTrabajo> ordenes = ordenService.listarOrdenes();

        if (filtro != null && !filtro.trim().isEmpty()) {
            String filtroLower = filtro.trim().toLowerCase();
            ordenes = ordenes.stream()
                    .filter(orden ->
                            String.valueOf(orden.getIdOrden()).contains(filtroLower) ||
                                    orden.getNumeroPlaca().toLowerCase().contains(filtroLower) ||
                                    String.valueOf(orden.getIdCliente()).contains(filtroLower) ||
                                    orden.getDescripcionSolicitud().toLowerCase().contains(filtroLower))
                    .toList();
        }

        request.setAttribute("ordenes", ordenes);
        request.getRequestDispatcher("ordenes/lista.jsp").forward(request, response);
    }
}
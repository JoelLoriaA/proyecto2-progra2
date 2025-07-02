package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*;
import cr.ac.ucr.servicarpro.proyecto2.progra2.servicies.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrdenServlet", urlPatterns = {"/OrdenServlet"})
public class OrdenServlet extends HttpServlet {

    private OrdenTrabajoService ordenService;
    private VehiculoService vehiculoService;
    private ClienteService clienteService;

    @Override
    public void init() throws ServletException {
        try {
            this.ordenService = new OrdenTrabajoService();
            this.vehiculoService = new VehiculoService();
            this.clienteService = new ClienteService();
        } catch (Exception e) {
            throw new ServletException("Error inicializando servicios", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("nuevo".equals(action)) {
                mostrarFormularioNuevo(request, response);
            } else if ("editar".equals(action)) {
                mostrarFormularioEditar(request, response);
            } else if ("detalle".equals(action)) {
                mostrarDetalle(request, response);
            } else if ("cambiarEstado".equals(action)) {
                cambiarEstado(request, response);
            } else if ("eliminar".equals(action)) {
                eliminarOrden(request, response);
            } else {
                listarOrdenes(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error procesando solicitud: " + e.getMessage());
            listarOrdenes(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String numeroPlaca = request.getParameter("numeroPlaca");
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String descripcionSolicitud = request.getParameter("descripcionSolicitud");
            String observacionesRecepcion = request.getParameter("observacionesRecepcion");
            String fechaDevolucionStr = request.getParameter("fechaDevolucion");
            String idOrdenStr = request.getParameter("idOrden");

            // Procesar detalles
            List<DetalleOrden> detalles = procesarDetalles(request);

            // Crear o actualizar orden
            OrdenDeTrabajo orden = new OrdenDeTrabajo();
            orden.setNumeroPlaca(numeroPlaca);
            orden.setIdCliente(idCliente);
            orden.setDescripcionSolicitud(descripcionSolicitud);
            orden.setObservacionesRecepcion(observacionesRecepcion);
            orden.setDetalles(detalles);

            // Agregar fecha de devolución como observación adicional
            if (fechaDevolucionStr != null && !fechaDevolucionStr.isEmpty()) {
                LocalDate fechaDevolucion = LocalDate.parse(fechaDevolucionStr);
                String observacionesCompletas = observacionesRecepcion +
                        "\nFecha estimada de devolución: " + fechaDevolucion.toString();
                orden.setObservacionesRecepcion(observacionesCompletas);
            }

            if (idOrdenStr != null && !idOrdenStr.isEmpty()) {
                // Actualizar orden existente
                int idOrden = Integer.parseInt(idOrdenStr);
                orden.setIdOrden(idOrden);
                ordenService.actualizarOrden(orden);
            } else {
                // Crear nueva orden
                ordenService.crearOrden(orden);
            }

            response.sendRedirect("OrdenServlet");

        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar la orden: " + e.getMessage());
            cargarDatosFormulario(request);
            request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
        }
    }

    private List<DetalleOrden> procesarDetalles(HttpServletRequest request) {
        List<DetalleOrden> detalles = new ArrayList<>();

        String[] tiposDetalle = request.getParameterValues("tipoDetalle");
        String[] cantidades = request.getParameterValues("cantidad");
        String[] observacionesDetalle = request.getParameterValues("observacionesDetalle");
        String[] precios = request.getParameterValues("precio");
        String[] descripciones = request.getParameterValues("descripcion");
        String[] costosManoPbra = request.getParameterValues("costoManoObra");

        if (tiposDetalle != null) {
            for (int i = 0; i < tiposDetalle.length; i++) {
                if (cantidades[i] != null && !cantidades[i].isEmpty()) {
                    DetalleOrden detalle = new DetalleOrden();
                    detalle.setCantidad(Integer.parseInt(cantidades[i]));
                    detalle.setObservaciones(observacionesDetalle[i]);
                    detalle.setPrecio(Double.parseDouble(precios[i]));
                    detalle.setNombreRepuesto(descripciones[i]);
                    detalle.setCostoManoObra(costosManoPbra[i] != null ?
                            Double.parseDouble(costosManoPbra[i]) : 0.0);

                    // Configurar tipo de detalle
                    TipoDetalle tipo = new TipoDetalle();
                    if ("REPUESTO".equals(tiposDetalle[i])) {
                        tipo.setId(1);
                        tipo.setNombre("REPUESTO");
                    } else {
                        tipo.setId(2);
                        tipo.setNombre("SERVICIO");
                    }
                    detalle.setTipoDetalle(tipo);

                    // Estado inicial
                    Estado estado = new Estado(1, "PENDIENTE");
                    detalle.setEstado(estado);

                    detalles.add(detalle);
                }
            }
        }

        return detalles;
    }

    private void listarOrdenes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filtro = request.getParameter("filtro");
        List<OrdenDeTrabajo> ordenes;

        if (filtro != null && !filtro.trim().isEmpty()) {
            ordenes = ordenService.listarOrdenes().stream()
                    .filter(o -> o.getIdOrden() == Integer.parseInt(filtro) ||
                            o.getNumeroPlaca().toLowerCase().contains(filtro.toLowerCase()) ||
                            o.getDescripcionSolicitud().toLowerCase().contains(filtro.toLowerCase()))
                    .toList();
        } else {
            ordenes = ordenService.listarOrdenes();
        }

        request.setAttribute("ordenes", ordenes);
        request.setAttribute("filtro", filtro);
        request.getRequestDispatcher("ordenes/lista.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarDatosFormulario(request);
        request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("id"));
        OrdenDeTrabajo orden = ordenService.buscarPorId(idOrden);

        if (orden == null) {
            request.setAttribute("error", "Orden no encontrada");
            listarOrdenes(request, response);
            return;
        }

        request.setAttribute("orden", orden);
        cargarDatosFormulario(request);
        request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
    }

    private void mostrarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("id"));
        OrdenDeTrabajo orden = ordenService.buscarPorId(idOrden);

        if (orden == null) {
            request.setAttribute("error", "Orden no encontrada");
            listarOrdenes(request, response);
            return;
        }

        request.setAttribute("orden", orden);
        request.getRequestDispatcher("ordenes/detalle.jsp").forward(request, response);
    }

    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("id"));
        int nuevoEstadoId = Integer.parseInt(request.getParameter("estado"));

        Estado nuevoEstado;
        switch (nuevoEstadoId) {
            case 1: nuevoEstado = new Estado(1, "Diagnóstico"); break;
            case 2: nuevoEstado = new Estado(2, "En reparación"); break;
            case 3: nuevoEstado = new Estado(3, "Listo para entrega"); break;
            case 4: nuevoEstado = new Estado(4, "Entregado"); break;
            case 5: nuevoEstado = new Estado(5, "Cancelado"); break;
            default: throw new IllegalArgumentException("Estado no válido");
        }

        ordenService.cambiarEstadoOrden(idOrden, nuevoEstado);
        response.sendRedirect("OrdenServlet?action=detalle&id=" + idOrden);
    }

    private void eliminarOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("id"));
        ordenService.borrarOrden(idOrden);
        response.sendRedirect("OrdenServlet");
    }

    private void cargarDatosFormulario(HttpServletRequest request) {
        List<Vehiculo> vehiculos = vehiculoService.listarVehiculos();
        List<Cliente> clientes = clienteService.listarClientes();

        request.setAttribute("vehiculos", vehiculos);
        request.setAttribute("clientes", clientes);
    }
}            
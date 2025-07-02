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

@WebServlet(name = "OrdenDeTrabajoServlet", urlPatterns = {"/OrdenDeTrabajoServlet"})
public class OrdenDeTrabajoServlet extends HttpServlet {

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

            // Si es una edición, validar estado antes de procesar detalles
            if (idOrdenStr != null && !idOrdenStr.isEmpty()) {
                int idOrden = Integer.parseInt(idOrdenStr);
                OrdenDeTrabajo ordenExistente = ordenService.buscarPorId(idOrden);

                if (ordenExistente != null && !puedeModificarDetalles(ordenExistente)) {
                    request.setAttribute("error",
                            "No se pueden agregar/modificar repuestos/servicios. Estado actual: " +
                                    ordenExistente.getEstado().getDescripcion());
                    request.setAttribute("orden", ordenExistente);
                    cargarDatosFormulario(request);
                    request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
                    return;
                }
            }

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

            response.sendRedirect("OrdenDeTrabajoServlet");

        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar la orden: " + e.getMessage());
            cargarDatosFormulario(request);
            request.getRequestDispatcher("ordenes/formulario.jsp").forward(request, response);
        }
    }

    /**
     * Valida si se pueden modificar los detalles de una orden según su estado
     */
    private boolean puedeModificarDetalles(OrdenDeTrabajo orden) {
        // Estados que permiten modificación:
        // 1: Recibida/Diagnóstico, 2: En reparación, 3: En espera de repuestos
        return orden.getEstado().getId() < 4;
    }

    /**
     * Valida si una orden está completada (estado >= 4)
     */
    private boolean estaOrdenCompletada(OrdenDeTrabajo orden) {
        // Estados completados: 4: Entregado, 5: Cancelado
        return orden.getEstado().getId() >= 4;
    }

    private List<DetalleOrden> procesarDetalles(HttpServletRequest request) {
        List<DetalleOrden> detalles = new ArrayList<>();

        String[] tiposDetalle = request.getParameterValues("tipoDetalle");
        String[] cantidades = request.getParameterValues("cantidad");
        String[] observaciones = request.getParameterValues("observaciones");
        String[] precios = request.getParameterValues("precio");
        String[] nombresRepuesto = request.getParameterValues("nombreRepuesto");
        String[] costosManObra = request.getParameterValues("costoManoObra");
        String[] repuestosPedido = request.getParameterValues("repuestoPedido");

        if (tiposDetalle != null) {
            for (int i = 0; i < tiposDetalle.length; i++) {
                if (cantidades[i] != null && !cantidades[i].isEmpty()) {
                    DetalleOrden detalle = new DetalleOrden();
                    detalle.setCantidad(Integer.parseInt(cantidades[i]));
                    detalle.setObservaciones(observaciones != null && i < observaciones.length ? observaciones[i] : "");
                    detalle.setPrecio(Double.parseDouble(precios[i]));
                    detalle.setNombreRepuesto(nombresRepuesto[i]);
                    detalle.setCostoManoObra(costosManObra != null && i < costosManObra.length && !costosManObra[i].isEmpty() ?
                            Double.parseDouble(costosManObra[i]) : 0.0);

                    // Verificar si el repuesto está pedido
                    boolean pedido = false;
                    if (repuestosPedido != null) {
                        for (String rp : repuestosPedido) {
                            if (("on".equals(rp) || "true".equals(rp)) && repuestosPedido.length > i) {
                                pedido = true;
                                break;
                            }
                        }
                    }
                    detalle.setRepuestoPedido(pedido);

                    // Configurar tipo de detalle
                    TipoDetalle tipo = new TipoDetalle();
                    int tipoId = Integer.parseInt(tiposDetalle[i]);
                    tipo.setId(tipoId);
                    tipo.setNombre(tipoId == 1 ? "Repuesto" : "Servicio");
                    detalle.setTipoDetalle(tipo);

                    // Estado inicial
                    Estado estado = new Estado(1, "Pendiente");
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
                    .filter(o -> String.valueOf(o.getIdOrden()).contains(filtro) ||
                            o.getNumeroPlaca().toLowerCase().contains(filtro.toLowerCase()) ||
                            o.getDescripcionSolicitud().toLowerCase().contains(filtro.toLowerCase()) ||
                            String.valueOf(o.getIdCliente()).contains(filtro))
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

        // Verificar si la orden está completada
        if (estaOrdenCompletada(orden)) {
            request.setAttribute("error",
                    "No se puede editar una orden que ya fue " + orden.getEstado().getDescripcion().toLowerCase());
            mostrarDetalle(request, response);
            return;
        }

        request.setAttribute("orden", orden);
        request.setAttribute("puedeModificarDetalles", puedeModificarDetalles(orden));
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
        request.setAttribute("puedeModificarDetalles", puedeModificarDetalles(orden));
        request.getRequestDispatcher("ordenes/detalle.jsp").forward(request, response);
    }

    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("id"));
        int nuevoEstadoId = Integer.parseInt(request.getParameter("estado"));

        Estado nuevoEstado;
        switch (nuevoEstadoId) {
            case 1: nuevoEstado = new Estado(1, "Recibida"); break;
            case 2: nuevoEstado = new Estado(2, "En reparación"); break;
            case 3: nuevoEstado = new Estado(3, "En espera de repuestos"); break;
            case 4: nuevoEstado = new Estado(4, "Entregado"); break;
            case 5: nuevoEstado = new Estado(5, "Cancelado"); break;
            default: throw new IllegalArgumentException("Estado no válido");
        }

        // Si se está cambiando a estado completado (4 o 5), agregar una confirmación
        if (nuevoEstadoId >= 4) {
            OrdenDeTrabajo orden = ordenService.buscarPorId(idOrden);
            if (orden != null) {
                System.out.println("Orden #" + idOrden + " marcada como " + nuevoEstado.getDescripcion() +
                        ". Ya no se podrán agregar más repuestos/servicios.");
            }
        }

        ordenService.cambiarEstadoOrden(idOrden, nuevoEstado);
        response.sendRedirect("OrdenDeTrabajoServlet?action=detalle&id=" + idOrden);
    }

    private void eliminarOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idOrden = Integer.parseInt(request.getParameter("id"));

        // Verificar si la orden existe antes de eliminar
        OrdenDeTrabajo orden = ordenService.buscarPorId(idOrden);
        if (orden == null) {
            request.setAttribute("error", "La orden de trabajo no existe");
            listarOrdenes(request, response);
            return;
        }

        // Eliminar la orden
        ordenService.borrarOrden(idOrden);
        response.sendRedirect("OrdenDeTrabajoServlet");
    }

    private void cargarDatosFormulario(HttpServletRequest request) {
        try {
            // Obtener vehículos como Object[] con placa, marca, estilo, dueño
            List<Vehiculo> vehiculosList = vehiculoService.listarVehiculos();
            List<Object[]> vehiculos = vehiculosList.stream()
                    .map(v -> new Object[]{v.getNumeroPlaca(), v.getMarca(), v.getEstilo(), v.getDuenio()})
                    .toList();

            // Obtener clientes como Object[] con id, nombre, apellido
            List<Cliente> clientesList = clienteService.listarClientes();
            List<Object[]> clientes = clientesList.stream()
                    .map(c -> new Object[]{c.getId(), c.getNombre(), c.getPrimerApellido(), c.getSegundoApellido()})
                    .toList();

            request.setAttribute("vehiculos", vehiculos);
            request.setAttribute("clientes", clientes);
        } catch (Exception e) {
            System.err.println("Error cargando datos para formulario: " + e.getMessage());
            request.setAttribute("vehiculos", new ArrayList<>());
            request.setAttribute("clientes", new ArrayList<>());
        }
    }
}
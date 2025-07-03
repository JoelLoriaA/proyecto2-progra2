package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.RepuestoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ServicioDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.VehiculoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.OrdenDeTrabajoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*;
import org.jdom2.JDOMException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReporteServlet", urlPatterns = {"/ReporteServlet"})
public class ReporteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            ServicioDAO servicioDAO = new ServicioDAO();
            RepuestoDAO repuestoDAO = new RepuestoDAO();
            VehiculoDAO vehiculoDAO = new VehiculoDAO();
            OrdenDeTrabajoDAO ordenDAO = new OrdenDeTrabajoDAO();

            List<Cliente> clientes = clienteDAO.findAll();
            List<Servicio> servicios = servicioDAO.findAll();
            List<Repuesto> repuestos = repuestoDAO.findAll();
            List<Vehiculo> vehiculos = vehiculoDAO.findAll();
            List<OrdenDeTrabajo> ordenes = ordenDAO.findAll();

            // Estadísticas existentes
            int totalClientes = clientes.size();
            double totalIngresosServicios = servicios.stream()
                    .mapToDouble(s -> s.getPrecio() + s.getCostoManoObra()).sum();
            double totalValorRepuestos = repuestos.stream()
                    .mapToDouble(r -> r.getPrecio() * r.getCantidadDisponible()).sum();

            // Nuevas estadísticas
            int totalVehiculos = vehiculos.size();
            int totalOrdenes = ordenes.size();
            long ordenesActivas = ordenes.stream()
                    .filter(o -> o.getEstado().getId() < 5).count(); // Estados 1-4 son activos
            double totalIngresosPorOrdenes = ordenes.stream()
                    .filter(o -> o.getEstado().getId() == 5) // Solo órdenes entregadas
                    .flatMap(o -> o.getDetalles().stream())
                    .mapToDouble(d -> (d.getCantidad() * d.getPrecio()) + d.getCostoManoObra())
                    .sum();

            request.setAttribute("clientes", clientes);
            request.setAttribute("servicios", servicios);
            request.setAttribute("repuestos", repuestos);
            request.setAttribute("vehiculos", vehiculos);
            request.setAttribute("ordenes", ordenes);
            request.setAttribute("totalClientes", totalClientes);
            request.setAttribute("totalIngresosServicios", totalIngresosServicios);
            request.setAttribute("totalValorRepuestos", totalValorRepuestos);
            request.setAttribute("totalVehiculos", totalVehiculos);
            request.setAttribute("totalOrdenes", totalOrdenes);
            request.setAttribute("ordenesActivas", ordenesActivas);
            request.setAttribute("totalIngresosPorOrdenes", totalIngresosPorOrdenes);

            request.getRequestDispatcher("reportes/reporte.jsp").forward(request, response);

        } catch (JDOMException e) {
            throw new ServletException("Error al cargar datos para el reporte", e);
        }
    }
}
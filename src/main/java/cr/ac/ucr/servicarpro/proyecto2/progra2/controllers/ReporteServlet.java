package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.RepuestoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ServicioDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Servicio;
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

            List<Cliente> clientes = clienteDAO.findAll();
            List<Servicio> servicios = servicioDAO.findAll();
            List<Repuesto> repuestos = repuestoDAO.findAll();

            int totalClientes = clientes.size();
            double totalIngresosServicios = servicios.stream()
                    .mapToDouble(s -> s.getPrecio() + s.getCostoManoObra()).sum();
            double totalValorRepuestos = repuestos.stream()
                    .mapToDouble(r -> r.getPrecio() * r.getCantidadDisponible()).sum();

            request.setAttribute("clientes", clientes);
            request.setAttribute("servicios", servicios);
            request.setAttribute("repuestos", repuestos);
            request.setAttribute("totalClientes", totalClientes);
            request.setAttribute("totalIngresosServicios", totalIngresosServicios);
            request.setAttribute("totalValorRepuestos", totalValorRepuestos);

            request.getRequestDispatcher("reportes/reporte.jsp").forward(request, response);

        } catch (JDOMException e) {
            throw new ServletException("Error al cargar datos para el reporte", e);
        }
    }

}

package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.RepuestoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.ServicioDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Servicio;
import org.jdom2.JDOMException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServicioServlet", urlPatterns = {"/ServicioServlet"})
public class ServicioServlet extends HttpServlet {

    private ServicioDAO servicioDAO;

     @Override
    public void init() throws ServletException {
        try {
            servicioDAO = new ServicioDAO();
        } catch (Exception e) {
            throw new ServletException("Error al inicializar RepuestoDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "list";

        try {
            switch (action) {
                case "new":
                    request.getRequestDispatcher("servicios/formulario.jsp").forward(request, response);
                    break;
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Servicio servicioEdit = servicioDAO.findById(idEdit);
                    request.setAttribute("servicio", servicioEdit);
                    request.getRequestDispatcher("servicios/formulario.jsp").forward(request, response);
                    break;
                case "delete":
                    int idDelete = Integer.parseInt(request.getParameter("id"));
                    servicioDAO.delete(idDelete);
                    response.sendRedirect("ServicioServlet");
                    break;
                default:
                    List<Servicio> servicios = servicioDAO.findAll();
                    request.setAttribute("servicios", servicios);
                    request.getRequestDispatcher("servicios/lista.jsp").forward(request, response);
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
            int id = request.getParameter("id") == null || request.getParameter("id").isEmpty()
                    ? servicioDAO.getNextId()
                    : Integer.parseInt(request.getParameter("id"));

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            double costoManoObra = Double.parseDouble(request.getParameter("costoManoObra"));

            Servicio servicio = new Servicio(id, nombre, descripcion, precio, costoManoObra);

            if (precio < 0 || costoManoObra < 0) {
                request.setAttribute("servicio", servicio);
                request.setAttribute("error", "El precio y el costo de mano de obra no pueden ser negativos.");
                request.getRequestDispatcher("servicios/formulario.jsp").forward(request, response);
                return;
            }

            boolean esNuevo = request.getParameter("id") == null || request.getParameter("id").isEmpty();
            boolean existeDuplicado = servicioDAO.findAll().stream()
                    .anyMatch(s -> (esNuevo || s.getId() != id) && s.getNombre().equalsIgnoreCase(nombre));

            if (existeDuplicado) {
                request.setAttribute("servicio", servicio);
                request.setAttribute("error", "Ya existe un servicio con ese nombre.");
                request.getRequestDispatcher("servicios/formulario.jsp").forward(request, response);
                return;
            }

            servicioDAO.save(servicio);
            response.sendRedirect("ServicioServlet");

        } catch (Exception e) {
            throw new ServletException("Error al guardar el servicio", e);
        }
    }


}

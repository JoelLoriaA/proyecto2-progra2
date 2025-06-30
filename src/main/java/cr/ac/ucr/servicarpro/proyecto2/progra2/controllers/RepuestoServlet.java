package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.RepuestoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto;
import org.jdom2.JDOMException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RepuestoServlet", urlPatterns = {"/RepuestoServlet"})
public class RepuestoServlet extends HttpServlet {

    private RepuestoDAO repuestoDAO;

    @Override
    public void init() throws ServletException {
        try {
            repuestoDAO = new RepuestoDAO();
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
                    request.getRequestDispatcher("repuestos/formulario.jsp").forward(request, response);
                    break;

                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Repuesto repuestoEdit = repuestoDAO.findById(idEdit);
                    request.setAttribute("repuesto", repuestoEdit);
                    request.getRequestDispatcher("repuestos/formulario.jsp").forward(request, response);
                    break;

                case "delete":
                    int idDelete = Integer.parseInt(request.getParameter("id"));
                    repuestoDAO.delete(idDelete);
                    response.sendRedirect("RepuestoServlet");
                    break;

                default:
                    String filtro = request.getParameter("filtro");
                    List<Repuesto> repuestos = repuestoDAO.findAll();

                    if (filtro != null && !filtro.trim().isEmpty()) {
                        String filtroLower = filtro.trim().toLowerCase();
                        repuestos = repuestos.stream()
                                .filter(r ->
                                        String.valueOf(r.getId()).contains(filtroLower) ||
                                        r.getNombre().toLowerCase().contains(filtroLower) ||
                                        r.getDescripcion().toLowerCase().contains(filtroLower)
                                )
                                .toList();
                    }

                    request.setAttribute("repuestos", repuestos);
                    request.getRequestDispatcher("repuestos/lista.jsp").forward(request, response);
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
            boolean esNuevo = request.getParameter("id") == null || request.getParameter("id").isEmpty();
            int id = esNuevo ? repuestoDAO.getNextId() : Integer.parseInt(request.getParameter("id"));

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidadDisponible"));
            boolean pedido = request.getParameter("pedido") != null;

            Repuesto repuesto = new Repuesto(id, nombre, descripcion, precio, cantidad, pedido);

            if (precio < 0 || cantidad < 0) {
                request.setAttribute("repuesto", repuesto);
                request.setAttribute("error", "El precio y la cantidad no pueden ser negativos.");
                request.getRequestDispatcher("repuestos/formulario.jsp").forward(request, response);
                return;
            }

            boolean duplicado = repuestoDAO.findAll().stream()
                    .anyMatch(r -> (esNuevo || r.getId() != id) && r.getNombre().equalsIgnoreCase(nombre));

            if (duplicado) {
                request.setAttribute("repuesto", repuesto);
                request.setAttribute("error", "Ya existe un repuesto con ese nombre.");
                request.getRequestDispatcher("repuestos/formulario.jsp").forward(request, response);
                return;
            }

            repuestoDAO.save(repuesto);
            response.sendRedirect("RepuestoServlet");

        } catch (Exception e) {
            throw new ServletException("Error al guardar el repuesto", e);
        }
    }


}

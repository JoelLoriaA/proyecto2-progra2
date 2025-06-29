package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import org.jdom2.JDOMException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/ClienteServlet"})
public class ClienteServlet extends HttpServlet {

    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        try {
            clienteDAO = new ClienteDAO();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            throw new ServletException("Error al inicializar ClienteDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "list";

        try {
            switch (action) {
                case "new":
                    request.getRequestDispatcher("clientes/formulario.jsp").forward(request, response);
                    break;
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Cliente clienteEdit = clienteDAO.findById(idEdit);
                    request.setAttribute("cliente", clienteEdit);
                    request.getRequestDispatcher("clientes/formulario.jsp").forward(request, response);
                    break;
                case "delete":
                    int idDelete = Integer.parseInt(request.getParameter("id"));
                    clienteDAO.delete(idDelete);
                    response.sendRedirect("ClienteServlet");
                    break;
                default:
                    List<Cliente> clientes = clienteDAO.findAll();
                    request.setAttribute("clientes", clientes);
                    request.getRequestDispatcher("clientes/lista.jsp").forward(request, response);
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
            int id = esNuevo ? clienteDAO.getNextId() : Integer.parseInt(request.getParameter("id"));

            String nombre = request.getParameter("nombre");
            String primerApellido = request.getParameter("primerApellido");
            String segundoApellido = request.getParameter("segundoApellido");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            String email = request.getParameter("email");

            Cliente cliente = new Cliente(id, nombre, primerApellido, segundoApellido, telefono, direccion, email);

            boolean duplicado = clienteDAO.findAll().stream()
                    .anyMatch(c -> (esNuevo || c.getId() != id) &&
                            (c.getEmail().equalsIgnoreCase(email) || c.getId() == id));

            if (duplicado) {
                request.setAttribute("cliente", cliente);
                request.setAttribute("error", "Ya existe un cliente con ese correo o cédula.");
                request.getRequestDispatcher("clientes/formulario.jsp").forward(request, response);
                return;
            }

            clienteDAO.save(cliente);
            response.sendRedirect("ClienteServlet");

        } catch (Exception e) {
            throw new ServletException("Error al guardar el cliente", e);
        }
    }
}

package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import org.jdom2.JDOMException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        String filtro = request.getParameter("filtro") != null ? request.getParameter("filtro").trim().toLowerCase() : "";

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
                    // Redirigir manteniendo el filtro si existe
                    if (!filtro.isEmpty()) {
                        response.sendRedirect("ClienteServlet?filtro=" + filtro);
                    } else {
                        response.sendRedirect("ClienteServlet");
                    }
                    break;

                default:
                    List<Cliente> clientes = clienteDAO.findAll();

                    if (!filtro.isEmpty()) {
                        clientes = clientes.stream()
                            .filter(c ->
                                String.valueOf(c.getId()).contains(filtro) ||
                                c.getNombre().toLowerCase().contains(filtro) ||
                                c.getPrimerApellido().toLowerCase().contains(filtro) ||
                                c.getSegundoApellido().toLowerCase().contains(filtro))
                            .collect(Collectors.toList());
                    }

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

            // Validar formato de email
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                Cliente cliente = new Cliente(id, nombre, primerApellido, segundoApellido, telefono, direccion, email);
                request.setAttribute("cliente", cliente);
                request.setAttribute("error", "El formato del correo electrónico no es válido.");
                request.getRequestDispatcher("clientes/formulario.jsp").forward(request, response);
                return;
            }

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

            String filtro = request.getParameter("filtro");
            if (filtro != null && !filtro.trim().isEmpty()) {
                response.sendRedirect("ClienteServlet?filtro=" + filtro.trim());
            } else {
                response.sendRedirect("ClienteServlet");
            }

        } catch (Exception e) {
            throw new ServletException("Error al guardar el cliente", e);
        }
    }
}

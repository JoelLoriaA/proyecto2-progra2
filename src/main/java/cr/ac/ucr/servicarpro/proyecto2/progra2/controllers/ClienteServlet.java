/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import java.io.IOException;
import java.util.List;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import cr.ac.ucr.servicarpro.proyecto2.progra2.servicies.ClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Emanuel Araya
 */
@WebServlet(name = "ClienteServlet", urlPatterns = {"/ClienteServlet"})
public class ClienteServlet extends HttpServlet {
    private ClienteService clienteService = new ClienteService();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";
        switch (action) {
            case "showForm":
                request.getRequestDispatcher("/clientes/formulario.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Cliente clienteEdit = clienteService.buscarPorId(idEdit);
                request.setAttribute("cliente", clienteEdit);
                request.getRequestDispatcher("/clientes/formulario.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                clienteService.borrarCliente(idDelete);
                response.sendRedirect("ClienteServlet");
                break;
            default: // list
                List<Cliente> clientes = clienteService.listarClientes();
                request.setAttribute("clientes", clientes);
                request.getRequestDispatcher("/clientes/lista.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String nombre = request.getParameter("nombre");
            // ...otros campos...
            Cliente nuevo = new Cliente();
            nuevo.setNombre(nombre);
            // ...set otros campos...
            clienteService.agregarCliente(nuevo);
            response.sendRedirect("ClienteServlet");
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            // ...otros campos...
            Cliente cliente = clienteService.buscarPorId(id);
            cliente.setNombre(nombre);
            // ...set otros campos...
            clienteService.actualizarCliente(cliente);
            response.sendRedirect("ClienteServlet");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

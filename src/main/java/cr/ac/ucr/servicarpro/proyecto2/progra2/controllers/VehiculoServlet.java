package cr.ac.ucr.servicarpro.proyecto2.progra2.controllers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.servicies.VehiculoService;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Vehiculo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "VehiculoServlet", urlPatterns = {"/VehiculoServlet"})
public class VehiculoServlet extends HttpServlet {

    private VehiculoService vehiculoService;

    @Override
    public void init() throws ServletException {
        try {
            vehiculoService = new VehiculoService();
        } catch (Exception e) {
            throw new ServletException("Error al inicializar VehiculoService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "list";

        try {
            switch (action) {
                case "new":
                    request.getRequestDispatcher("vehiculos/formulario.jsp").forward(request, response);
                    break;

                case "edit":
                    String placaEdit = request.getParameter("placa");
                    if (placaEdit != null) {
                        Vehiculo vehiculoEdit = vehiculoService.buscarPorPlaca(placaEdit);
                        request.setAttribute("vehiculo", vehiculoEdit);
                    }
                    request.getRequestDispatcher("vehiculos/formulario.jsp").forward(request, response);
                    break;

                case "delete":
                    String placaDelete = request.getParameter("placa");
                    if (placaDelete != null) {
                        vehiculoService.borrarVehiculo(placaDelete);
                    }
                    response.sendRedirect("VehiculoServlet");
                    break;

                default:
                    String filtro = request.getParameter("filtro");
                    List<Vehiculo> vehiculos = vehiculoService.listarVehiculos();

                    if (filtro != null && !filtro.trim().isEmpty()) {
                        String filtroLower = filtro.trim().toLowerCase();
                        vehiculos = vehiculos.stream()
                                .filter(v ->
                                        v.getNumeroPlaca().toLowerCase().contains(filtroLower) ||
                                                v.getMarca().toLowerCase().contains(filtroLower) ||
                                                v.getEstilo().toLowerCase().contains(filtroLower) ||
                                                v.getDuenio().toLowerCase().contains(filtroLower)
                                )
                                .toList();
                    }

                    request.setAttribute("vehiculos", vehiculos);
                    request.getRequestDispatcher("vehiculos/lista.jsp").forward(request, response);
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
            String numeroPlaca = request.getParameter("numeroPlaca");
            String color = request.getParameter("color");
            String marca = request.getParameter("marca");
            String estilo = request.getParameter("estilo");
            int anio = Integer.parseInt(request.getParameter("anio"));
            String vin = request.getParameter("vin");
            double cilindraje = Double.parseDouble(request.getParameter("cilindraje"));
            String duenio = request.getParameter("duenio");

            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setNumeroPlaca(numeroPlaca);
            vehiculo.setColor(color);
            vehiculo.setMarca(marca);
            vehiculo.setEstilo(estilo);
            vehiculo.setAnio(anio);
            vehiculo.setVin(vin);
            vehiculo.setCilindraje(cilindraje);
            vehiculo.setDuenio(duenio);

            // Determinar si es edición basándose en si ya existe el vehículo
            Vehiculo existente = vehiculoService.buscarPorPlaca(numeroPlaca);

            if (existente == null) {
                vehiculoService.agregarVehiculo(vehiculo);
            } else {
                vehiculoService.actualizarVehiculo(vehiculo);
            }

            response.sendRedirect("VehiculoServlet");

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("vehiculos/formulario.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al procesar el vehículo", e);
        }
    }
}
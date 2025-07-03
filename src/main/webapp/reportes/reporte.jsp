<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.*, cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*" %>
    <%
        List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
        List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
        List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
        List<Vehiculo> vehiculos = (List<Vehiculo>) request.getAttribute("vehiculos");
        List<OrdenDeTrabajo> ordenes = (List<OrdenDeTrabajo>) request.getAttribute("ordenes");

        Integer totalClientes = (Integer) request.getAttribute("totalClientes");
        Integer totalVehiculos = (Integer) request.getAttribute("totalVehiculos");
        Integer totalOrdenes = (Integer) request.getAttribute("totalOrdenes");
        Long ordenesActivas = (Long) request.getAttribute("ordenesActivas");
        Double totalValorRepuestos = (Double) request.getAttribute("totalValorRepuestos");
        Double totalIngresosServicios = (Double) request.getAttribute("totalIngresosServicios");
        Double totalIngresosPorOrdenes = (Double) request.getAttribute("totalIngresosPorOrdenes");
    %>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Reporte General</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <style>
            body {
                background-color: #1a1a1a;
                color: #f1f1f1;
                font-family: 'Roboto', sans-serif;
                padding: 40px;
                line-height: 1.6;
            }

            h1 {
                text-align: center;
                font-size: 2.2em;
                color: #ff4500;
                margin-bottom: 40px;
            }

            h2 {
                font-size: 1.5em;
                color: #ff8c42;
                margin-top: 40px;
                margin-bottom: 15px;
                border-bottom: 2px solid #ff3c00;
                padding-bottom: 5px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 40px;
                background-color: #262626;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 0 12px rgba(255, 60, 0, 0.2);
            }

            th, td {
                padding: 12px 15px;
                text-align: left;
                border-bottom: 1px solid #3a3a3a;
            }

            th {
                background-color: #333;
                color: #ff3c00;
                font-weight: bold;
                font-size: 15px;
            }

            tr:hover {
                background-color: #2e2e2e;
            }

            td {
                font-size: 14px;
                color: #eaeaea;
                vertical-align: middle;
            }

            .btn-inicio {
                display: block;
                width: fit-content;
                margin: 0 auto 30px;
                padding: 12px 24px;
                background-color: #ff3c00;
                color: white;
                text-decoration: none;
                border-radius: 6px;
                font-weight: bold;
                transition: background-color 0.3s ease;
                font-size: 15px;
            }

            .btn-inicio:hover {
                background-color: #d93800;
            }

            .estado-badge {
                padding: 4px 8px;
                border-radius: 12px;
                font-size: 12px;
                font-weight: bold;
                color: white;
            }

            .estado-1 { background-color: #3498db; }
            .estado-2 { background-color: #f39c12; }
            .estado-3 { background-color: #e67e22; }
            .estado-4 { background-color: #27ae60; }
            .estado-5 { background-color: #95a5a6; }
            .estado-6 { background-color: #e74c3c; }
        </style>
    </head>
    <body>

    <h1>Reporte General del Sistema</h1>

    <a class="btn-inicio" href="inicio.jsp">Ir al Inicio</a>

    <h2>Clientes Registrados</h2>
    <table>
        <tr>
            <th>Nombre Completo</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Email</th>
        </tr>
        <%
            for (Cliente c : clientes) {
        %>
        <tr>
            <td><%= c.getNombre() %> <%= c.getPrimerApellido() %> <%= c.getSegundoApellido() %></td>
            <td><%= c.getTelefono() %></td>
            <td><%= c.getDireccion() %></td>
            <td><%= c.getEmail() %></td>
        </tr>
        <%
            }
        %>
    </table>

    <h2>Servicios Ofrecidos</h2>
    <table>
        <tr>
            <th>Servicio</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Mano de Obra</th>
        </tr>
        <%
            for (Servicio s : servicios) {
        %>
        <tr>
            <td><%= s.getNombre() %></td>
            <td><%= s.getDescripcion() %></td>
            <td>₡<%= String.format("%,.2f", s.getPrecio()) %></td>
            <td>₡<%= String.format("%,.2f", s.getCostoManoObra()) %></td>
        </tr>
        <%
            }
        %>
    </table>

    <h2>Inventario de Repuestos</h2>
    <table>
        <tr>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Cantidad</th>
        </tr>
        <%
            for (Repuesto r : repuestos) {
        %>
        <tr>
            <td><%= r.getNombre() %></td>
            <td><%= r.getDescripcion() %></td>
            <td>₡<%= String.format("%,.2f", r.getPrecio()) %></td>
            <td><%= r.getCantidadDisponible() %></td>
        </tr>
        <%
            }
        %>
    </table>

    <h2>Vehículos Registrados</h2>
    <table>
        <tr>
            <th>Placa</th>
            <th>Marca</th>
            <th>Color</th>
            <th>Estilo</th>
            <th>Año</th>
            <th>VIN</th>
            <th>Cilindraje</th>
            <th>Dueño</th>
        </tr>
        <%
            for (Vehiculo v : vehiculos) {
        %>
        <tr>
            <td><%= v.getNumeroPlaca() %></td>
            <td><%= v.getMarca() %></td>
            <td><%= v.getColor() %></td>
            <td><%= v.getEstilo() %></td>
            <td><%= v.getAnio() %></td>
            <td><%= v.getVin() %></td>
            <td><%= String.format("%.1f L", v.getCilindraje()) %></td>
            <td><%= v.getDuenio() %></td>
        </tr>
        <%
            }
        %>
    </table>

    <h2>Órdenes de Trabajo</h2>
    <table>
        <tr>
            <th>ID Orden</th>
            <th>Fecha Ingreso</th>
            <th>Estado</th>
            <th>Placa Vehículo</th>
            <th>ID Cliente</th>
            <th>Descripción</th>
            <th>Total Detalles</th>
        </tr>
        <%
            for (OrdenDeTrabajo o : ordenes) {
                double totalOrden = 0;
                if (o.getDetalles() != null) {
                    for (DetalleOrden d : o.getDetalles()) {
                        totalOrden += (d.getCantidad() * d.getPrecio()) + d.getCostoManoObra();
                    }
                }
        %>
        <tr>
            <td>#<%= o.getIdOrden() %></td>
            <td><%= o.getFechaIngreso().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></td>
            <td>
                <span class="estado-badge estado-<%= o.getEstado().getId() %>">
                    <%= o.getEstado().getDescripcion() %>
                </span>
            </td>
            <td><%= o.getNumeroPlaca() %></td>
            <td><%= o.getIdCliente() %></td>
            <td><%= o.getDescripcionSolicitud().length() > 50 ?
                     o.getDescripcionSolicitud().substring(0, 50) + "..." :
                     o.getDescripcionSolicitud() %></td>
            <td>₡<%= String.format("%,.2f", totalOrden) %></td>
        </tr>
        <%
            }
        %>
    </table>

    <h2>Estadísticas Generales</h2>
    <table>
        <tr>
            <th>Concepto</th>
            <th>Valor</th>
        </tr>
        <tr>
            <td>Total de Clientes</td>
            <td><%= totalClientes %></td>
        </tr>
        <tr>
            <td>Total de Vehículos</td>
            <td><%= totalVehiculos %></td>
        </tr>
        <tr>
            <td>Total de Órdenes</td>
            <td><%= totalOrdenes %></td>
        </tr>
        <tr>
            <td>Órdenes Activas</td>
            <td><%= ordenesActivas %></td>
        </tr>
        <tr>
            <td>Valor Inventario Repuestos</td>
            <td>₡<%= String.format("%,.2f", totalValorRepuestos) %></td>
        </tr>
        <tr>
            <td>Ingresos Potenciales Servicios</td>
            <td>₡<%= String.format("%,.2f", totalIngresosServicios) %></td>
        </tr>
        <tr>
            <td>Ingresos por Órdenes Entregadas</td>
            <td>₡<%= String.format("%,.2f", totalIngresosPorOrdenes) %></td>
        </tr>
    </table>

    </body>
    </html>
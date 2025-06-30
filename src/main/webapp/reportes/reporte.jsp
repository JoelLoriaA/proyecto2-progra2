<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*" %>
<%
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
    List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
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
            color: #ff8c42; /* naranja suave */
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

</body>
</html>

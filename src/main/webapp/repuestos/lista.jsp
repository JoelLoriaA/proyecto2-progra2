<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto" %>
<%
    List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
    String filtro = request.getParameter("filtro") != null ? request.getParameter("filtro") : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inventario de Repuestos</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #121212;
            padding: 40px;
            color: #f1f1f1;
        }

        .home-button {
            display: inline-block;
            background-color: #ff3c00;
            color: white;
            text-decoration: none;
            padding: 10px 16px;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
            margin-bottom: 25px;
        }

        .home-button:hover {
            background-color: #e03a00;
        }

        h2 {
            text-align: center;
            color: #ff3c00;
            margin-bottom: 10px;
        }

        .top-link {
            display: block;
            width: fit-content;
            margin: 0 auto 20px;
            padding: 10px 16px;
            background-color: #ff3c00;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .top-link:hover {
            background-color: #e03a00;
        }

        .search-form-container {
            display: flex;
            justify-content: flex-start;
            margin-bottom: 20px;
        }

        form.search-form {
            display: flex;
            gap: 8px;
            align-items: center;
        }

        .search-input {
            padding: 8px 12px;
            border-radius: 4px;
            border: none;
            width: 300px;
            font-size: 14px;
            background-color: #1e1e1e;
            color: #f1f1f1;
        }

        .search-button {
            padding: 8px 14px;
            background-color: #ff3c00;
            color: white;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }

        .search-button:hover {
            background-color: #e03a00;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #1e1e1e;
            box-shadow: 0 0 12px rgba(255, 60, 0, 0.3);
            border-radius: 6px;
            overflow: hidden;
        }

        th, td {
            padding: 10px 12px;
            border-bottom: 1px solid #333;
            text-align: center;
            font-size: 14px;
        }

        th {
            background-color: #2a2a2a;
            color: #ff3c00;
            font-weight: 600;
        }

        tr:hover {
            background-color: #2d2d2d;
        }

        .stock-low {
            color: #ff4c4c;
            font-weight: bold;
        }

        .pedido {
            color: #27ae60;
            font-weight: bold;
        }

        .precio {
            font-weight: bold;
            color: #ffd166;
        }

        .acciones a {
            display: inline-block;
            text-decoration: none;
            padding: 6px 10px;
            margin: 2px;
            border-radius: 5px;
            font-weight: bold;
            font-size: 13px;
            color: white;
        }

        .edit-link {
            background-color: #3498db;
        }

        .edit-link:hover {
            background-color: #2980b9;
        }

        .delete-link {
            background-color: #e74c3c;
        }

        .delete-link:hover {
            background-color: #c0392b;
        }

        .no-data {
            text-align: center;
            padding: 20px;
            color: #bbb;
        }

        .icon {
            margin-right: 6px;
        }
    </style>
</head>
<body>

<a href="DashboardServlet" class="home-button"><i class="fas fa-home"></i> Inicio</a>

<h2>Inventario de Repuestos</h2>

<a class="top-link" href="RepuestoServlet?action=new">➕ Agregar Repuesto</a>

<div class="search-form-container">
    <form class="search-form" method="get" action="RepuestoServlet">
        <input type="text" name="filtro" class="search-input" placeholder="Buscar por ID, nombre o descripción..." value="<%= filtro %>">
        <input type="submit" class="search-button" value="Buscar">
    </form>
</div>

<table>
    <tr>
        <th>#</th>
        <th>Nombre</th>
        <th>Descripción</th>
        <th>Precio</th>
        <th>Cantidad</th>
        <th>Estado</th>
        <th>Acciones</th>
    </tr>
<%
    if (repuestos != null && !repuestos.isEmpty()) {
        for (Repuesto r : repuestos) {
            boolean bajoStock = r.getCantidadDisponible() < 5;
            boolean fuePedido = r.isPedido();
%>
    <tr>
        <td><%= r.getId() %></td>
        <td><i class="fas fa-cog icon"></i><%= r.getNombre() %></td>
        <td><%= r.getDescripcion() %></td>
        <td class="precio">₡<%= String.format("%,.2f", r.getPrecio()) %></td>
        <td class="<%= bajoStock ? "stock-low" : "" %>"><%= r.getCantidadDisponible() %></td>
        <td>
            <% if (fuePedido) { %>
                <span class="pedido"><i class="fas fa-check-circle icon"></i>Pedido</span>
            <% } else { %>
                <span style="color:#999;"><i class="fas fa-clock icon"></i>No pedido</span>
            <% } %>
        </td>
        <td class="acciones">
            <a class="edit-link" href="RepuestoServlet?action=edit&id=<%= r.getId() %>">Editar</a>
            <a class="delete-link" href="RepuestoServlet?action=delete&id=<%= r.getId() %>"
               onclick="return confirm('¿Eliminar este repuesto?')">Borrar</a>
        </td>
    </tr>
<%
        }
    } else {
%>
    <tr><td colspan="7" class="no-data">No hay repuestos registrados.</td></tr>
<%
    }
%>
</table>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Servicio" %>
<%
    List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
    String filtroActual = request.getParameter("filtro") != null ? request.getParameter("filtro") : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Servicios del Taller</title>
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
            width: 280px;
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

        .precio {
            color: #ffd166;
            font-weight: bold;
        }

        .manoObra {
            color: #9be2e2;
            font-weight: bold;
        }

        .total {
            color: #aaffaa;
            font-weight: bold;
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

<a href="inicio.jsp" class="home-button"><i class="fas fa-home"></i> Inicio</a>

<h2>Servicios del Taller</h2>

<a class="top-link" href="ServicioServlet?action=new">➕ Agregar Servicio</a>

<div class="search-form-container">
    <form class="search-form" method="get" action="ServicioServlet">
        <input type="hidden" name="action" value="list">
        <input type="text" name="filtro" class="search-input" placeholder="Buscar por nombre, descripción o ID" value="<%= filtroActual %>">
        <input type="submit" class="search-button" value="Buscar">
    </form>
</div>

<table>
    <tr>
        <th>#</th>
        <th>Servicio</th>
        <th>Descripción</th>
        <th>Precio</th>
        <th>Mano de Obra</th>
        <th>Total</th>
        <th>Acciones</th>
    </tr>
<%
    if (servicios != null && !servicios.isEmpty()) {
        for (Servicio s : servicios) {
            double total = s.getPrecio() + s.getCostoManoObra();
%>
    <tr>
        <td><%= s.getId() %></td>
        <td><i class="fas fa-tools icon"></i><%= s.getNombre() %></td>
        <td><%= s.getDescripcion() %></td>
        <td class="precio">₡<%= String.format("%,.2f", s.getPrecio()) %></td>
        <td class="manoObra">₡<%= String.format("%,.2f", s.getCostoManoObra()) %></td>
        <td class="total">₡<%= String.format("%,.2f", total) %></td>
        <td class="acciones">
            <a class="edit-link" href="ServicioServlet?action=edit&id=<%= s.getId() %>">Editar</a>
            <a class="delete-link" href="ServicioServlet?action=delete&id=<%= s.getId() %>"
               onclick="return confirm('¿Eliminar este servicio?')">Borrar</a>
        </td>
    </tr>
<%
        }
    } else {
%>
    <tr><td colspan="7" class="no-data">No hay servicios registrados.</td></tr>
<%
    }
%>
</table>

</body>
</html>

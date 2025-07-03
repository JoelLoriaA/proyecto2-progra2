<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente" %>
<%
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    String filtro = request.getParameter("filtro") != null ? request.getParameter("filtro") : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Clientes Registrados</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #121212;
            margin: 0;
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

        .search-form {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .search-form input[type="text"] {
            padding: 8px 12px;
            font-size: 14px;
            border: none;
            border-radius: 5px;
            background-color: #1e1e1e;
            color: #f1f1f1;
            width: 250px;
        }

        .search-form input[type="submit"] {
            padding: 8px 16px;
            background-color: #ff3c00;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .search-form input[type="submit"]:hover {
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

        .danger {
            color: #ff4c4c;
            font-weight: bold;
        }

        .icon {
            margin-right: 6px;
        }

        .action-btn {
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

        .email-link {
            background-color: #27ae60;
        }

        .email-link:hover {
            background-color: #1e8449;
        }

        .no-data {
            text-align: center;
            padding: 20px;
            color: #bbb;
        }
    </style>
</head>
<body>

<a href="inicio.jsp" class="home-button"><i class="fas fa-home"></i> Inicio</a>

<h2>Clientes Registrados</h2>

<a class="top-link" href="ClienteServlet?action=new<%= !filtro.isEmpty() ? "&filtro=" + filtro : "" %>">➕ Nuevo Cliente</a>

<div class="search-form-container">
    <form method="get" action="ClienteServlet" class="search-form">
        <input type="text" name="filtro" placeholder="Buscar por nombre o cédula" value="<%= filtro %>" />
        <input type="submit" value="Buscar" />
    </form>
</div>

<table>
    <tr>
        <th>#</th>
        <th>Nombre Completo</th>
        <th>Teléfono</th>
        <th>Dirección</th>
        <th>Email</th>
        <th>Acciones</th>
    </tr>
<%
    if (clientes != null && !clientes.isEmpty()) {
        for (Cliente c : clientes) {
            boolean tieneEmail = c.getEmail() != null && !c.getEmail().isBlank();
            boolean sinTelefono = c.getTelefono() == null || c.getTelefono().isBlank();
%>
    <tr>
        <td><%= c.getId() %></td>
        <td><i class="fas fa-user icon"></i><%= c.getNombre() %> <%= c.getPrimerApellido() %> <%= c.getSegundoApellido() %></td>
        <td class="<%= sinTelefono ? "danger" : "" %>">
            <i class="fas fa-phone icon"></i>
            <%= sinTelefono ? "No registrado" : c.getTelefono() %>
        </td>
        <td><i class="fas fa-map-marker-alt icon"></i><%= c.getDireccion() %></td>
        <td>
            <i class="fas fa-envelope icon"></i>
            <%= tieneEmail ? c.getEmail() : "—" %>
        </td>
        <td>
            <%
                String filtroParam = filtro.isEmpty() ? "" : "&filtro=" + filtro;
            %>
            <a class="action-btn edit-link" href="ClienteServlet?action=edit&id=<%= c.getId() %><%= filtroParam %>">Editar</a>
            <a class="action-btn delete-link" href="ClienteServlet?action=delete&id=<%= c.getId() %><%= filtroParam %>"
               onclick="return confirm('¿Eliminar este cliente?')">Borrar</a>
        </td>
    </tr>
<%
        }
    } else {
%>
    <tr><td colspan="6" class="no-data">No hay clientes registrados.</td></tr>
<%
    }
%>
</table>

</body>
</html>


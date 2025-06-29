<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente" %>
<html>
<head>
    <title>Lista de Clientes</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #121212;
            margin: 0;
            padding: 40px;
            color: #f1f1f1;
        }

        h2 {
            text-align: center;
            color: #ff3c00;
            margin-bottom: 25px;
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

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #1e1e1e;
            box-shadow: 0 0 12px rgba(255, 60, 0, 0.3);
            border-radius: 6px;
            overflow: hidden;
        }

        th, td {
            padding: 12px 15px;
            border-bottom: 1px solid #333;
            text-align: center;
        }

        th {
            background-color: #2a2a2a;
            color: #ff3c00;
            font-weight: 600;
        }

        tr:hover {
            background-color: #2d2d2d;
        }

        td a {
            display: inline-block;
            text-decoration: none;
            padding: 8px 14px;
            border-radius: 5px;
            font-weight: 600;
            font-size: 14px;
            margin: 0 5px;
            transition: background-color 0.25s ease, transform 0.2s ease;
            box-shadow: 0 2px 4px rgba(0,0,0,0.3);
        }

        .edit-link {
            background-color: #3498db;
            color: white;
        }

        .edit-link:hover {
            background-color: #2980b9;
            transform: translateY(-2px);
        }

        .delete-link {
            background-color: #e74c3c;
            color: white;
        }

        .delete-link:hover {
            background-color: #c0392b;
            transform: translateY(-2px);
        }

        .no-data {
            text-align: center;
            padding: 20px;
            color: #bbb;
        }
    </style>
</head>
<body>
    <h2>Clientes</h2>
    <a class="top-link" href="ClienteServlet?action=showForm">➕ Añadir Cliente</a>
    <table>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Primer Apellido</th>
            <th>Segundo Apellido</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Email</th>
            <th>Acciones</th>
        </tr>
        <%
            List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
            if (clientes != null && !clientes.isEmpty()) {
                for (Cliente c : clientes) {
        %>
        <tr>
            <td><%= c.getId() %></td>
            <td><%= c.getNombre() %></td>
            <td><%= c.getPrimerApellido() %></td>
            <td><%= c.getSegundoApellido() %></td>
            <td><%= c.getTelefono() %></td>
            <td><%= c.getDireccion() %></td>
            <td><%= c.getEmail() %></td>
            <td>
                <a class="edit-link" href="ClienteServlet?action=edit&id=<%=c.getId()%>">Editar</a>
                <a class="delete-link" href="ClienteServlet?action=delete&id=<%=c.getId()%>"
                   onclick="return confirm('¿Seguro que desea borrar este cliente?');">Borrar</a>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="8" class="no-data">No hay clientes registrados.</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>

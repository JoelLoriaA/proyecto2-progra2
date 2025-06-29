<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto" %>
<%
    Repuesto r = (Repuesto) request.getAttribute("repuesto");
    boolean editando = (r != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= editando ? "Editar Repuesto" : "Nuevo Repuesto" %></title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #121212;
            color: #f1f1f1;
            padding: 40px;
        }

        h2 {
            text-align: center;
            color: #ff3c00;
            margin-bottom: 25px;
        }

        form {
            max-width: 500px;
            margin: 0 auto;
            background-color: #1e1e1e;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 12px rgba(255, 60, 0, 0.3);
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
        }

        input[type="text"],
        input[type="number"],
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 16px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
        }

        input[type="text"],
        input[type="number"] {
            background-color: #2a2a2a;
            color: #f1f1f1;
        }

        input[type="checkbox"] {
            transform: scale(1.2);
            margin-right: 8px;
        }

        input[type="submit"] {
            background-color: #ff3c00;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #e03a00;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #ff3c00;
            font-weight: bold;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h2><%= editando ? "Editar Repuesto" : "Nuevo Repuesto" %></h2>
<form action="RepuestoServlet" method="post">
    <% if (editando) { %>
        <input type="hidden" name="id" value="<%= r.getId() %>"/>
    <% } %>

    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" value="<%= editando ? r.getNombre() : "" %>" required/>

    <label for="descripcion">Descripción:</label>
    <input type="text" name="descripcion" id="descripcion" value="<%= editando ? r.getDescripcion() : "" %>" required/>

    <label for="precio">Precio:</label>
    <input type="number" step="0.01" name="precio" id="precio" value="<%= editando ? r.getPrecio() : "" %>" required/>

    <label for="cantidadDisponible">Cantidad Disponible:</label>
    <input type="number" name="cantidadDisponible" id="cantidadDisponible" value="<%= editando ? r.getCantidadDisponible() : "" %>" required/>

    <label>
        <input type="checkbox" name="pedido" <%= (editando && r.isPedido()) ? "checked" : "" %> />
        ¿Fue pedido?
    </label>

    <input type="submit" value="Guardar"/>
</form>

<a class="back-link" href="RepuestoServlet">← Volver a la lista</a>
</body>
</html>

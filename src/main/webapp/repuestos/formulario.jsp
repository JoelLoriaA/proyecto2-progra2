<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto" %>
<%
    Repuesto r = (Repuesto) request.getAttribute("repuesto");
    boolean editando = (r != null);
    String error = (String) request.getAttribute("error");
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
            width: 100%;
            max-width: 500px;
            margin: 0 auto;
            background-color: #1e1e1e;
            padding: 30px 20px;
            border-radius: 12px;
            box-shadow: 0 0 14px rgba(255, 60, 0, 0.35);
            display: flex;
            flex-direction: column;
        }

        label {
            margin-bottom: 6px;
            font-weight: 500;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 18px;
            border: none;
            border-radius: 6px;
            background-color: #2a2a2a;
            color: #f1f1f1;
            font-size: 15px;
        }

        input[type="number"] {
            min: 0;
        }

        .checkbox-group {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .checkbox-group input[type="checkbox"] {
            transform: scale(1.3);
            margin-right: 10px;
            accent-color: #ff3c00;
        }

        input[type="submit"] {
            background-color: #ff3c00;
            color: white;
            padding: 12px;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #e03a00;
        }

        .back-link {
            text-align: center;
            margin-top: 22px;
            text-decoration: none;
            color: #ff3c00;
            font-weight: bold;
            display: block;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        .error-message {
            color: #ff4d4d;
            background-color: #2a2a2a;
            border: 1px solid #ff4d4d;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 6px;
            text-align: center;
        }
    </style>
</head>
<body>
<h2><%= editando ? "Editar Repuesto" : "Nuevo Repuesto" %></h2>

<form action="RepuestoServlet" method="post">
    <% if (error != null) { %>
        <div class="error-message"><%= error %></div>
    <% } %>

    <% if (editando) { %>
        <input type="hidden" name="id" value="<%= r.getId() %>"/>
    <% } %>

    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" value="<%= editando ? r.getNombre() : "" %>" required/>

    <label for="descripcion">Descripción:</label>
    <input type="text" name="descripcion" id="descripcion" value="<%= editando ? r.getDescripcion() : "" %>" required/>

    <label for="precio">Precio:</label>
    <input type="number" step="0.01" min="0" name="precio" id="precio" value="<%= editando ? r.getPrecio() : "" %>" required/>

    <label for="cantidadDisponible">Cantidad Disponible:</label>
    <input type="number" min="0" name="cantidadDisponible" id="cantidadDisponible" value="<%= editando ? r.getCantidadDisponible() : "" %>" required/>

    <div class="checkbox-group">
        <input type="checkbox" name="pedido" id="pedido" <%= (editando && r.isPedido()) ? "checked" : "" %> />
        <label for="pedido">¿Fue pedido?</label>
    </div>

    <input type="submit" value="Guardar"/>
</form>

<a class="back-link" href="RepuestoServlet">← Volver a la lista</a>
</body>
</html>

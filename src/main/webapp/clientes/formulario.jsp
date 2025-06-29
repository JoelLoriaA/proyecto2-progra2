<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente" %>
<%
    Cliente c = (Cliente) request.getAttribute("cliente");
    boolean editando = (c != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= editando ? "Editar Cliente" : "Nuevo Cliente" %></title>
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
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 16px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
        }

        input[type="text"] {
            background-color: #2a2a2a;
            color: #f1f1f1;
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
<h2><%= editando ? "Editar Cliente" : "Nuevo Cliente" %></h2>
<form action="ClienteServlet" method="post">
    <% if (editando) { %>
        <input type="hidden" name="id" value="<%= c.getId() %>"/>
    <% } %>

    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" value="<%= editando ? c.getNombre() : "" %>" required/>

    <label for="primerApellido">Primer Apellido:</label>
    <input type="text" name="primerApellido" id="primerApellido" value="<%= editando ? c.getPrimerApellido() : "" %>" required/>

    <label for="segundoApellido">Segundo Apellido:</label>
    <input type="text" name="segundoApellido" id="segundoApellido" value="<%= editando ? c.getSegundoApellido() : "" %>" required/>

    <label for="telefono">Teléfono:</label>
    <input type="text" name="telefono" id="telefono" value="<%= editando ? c.getTelefono() : "" %>" required/>

    <label for="direccion">Dirección:</label>
    <input type="text" name="direccion" id="direccion" value="<%= editando ? c.getDireccion() : "" %>" required/>

    <label for="email">Correo Electrónico:</label>
    <input type="text" name="email" id="email" value="<%= editando ? c.getEmail() : "" %>" required/>

    <input type="submit" value="Guardar"/>
</form>

<a class="back-link" href="ClienteServlet">← Volver a la lista</a>
</body>
</html>

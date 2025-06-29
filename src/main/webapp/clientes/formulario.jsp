<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente" %>
<html>
<head>
    <title>Formulario Cliente</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #121212;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #f1f1f1;
        }

        .form-container {
            background-color: #1e1e1e;
            padding: 35px 40px;
            border-radius: 10px;
            box-shadow: 0 0 18px rgba(255, 60, 0, 0.4);
            width: 440px;
            border: 1px solid #333;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #ff3c00;
            font-weight: 700;
            font-size: 24px;
            letter-spacing: 1px;
        }

        label {
            display: block;
            margin-top: 18px;
            margin-bottom: 6px;
            font-weight: 500;
            color: #bbbbbb;
        }

        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #444;
            border-radius: 5px;
            background-color: #2c2c2c;
            color: #fff;
            transition: border-color 0.3s ease, background-color 0.3s ease;
            font-size: 15px;
        }

        input[type="text"]:focus,
        input[type="email"]:focus {
            border-color: #ff3c00;
            background-color: #2a2a2a;
            outline: none;
        }

        input[type="submit"] {
            width: 100%;
            padding: 12px;
            margin-top: 30px;
            background-color: #ff3c00;
            border: none;
            color: white;
            font-weight: 600;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #e03a00;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #ff3c00;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .back-link:hover {
            color: #ffa07a;
        }

        ::placeholder {
            color: #888;
        }
    </style>
</head>
<body>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    boolean editando = (cliente != null);
%>
<div class="form-container">
    <h2><%= editando ? "Editar" : "Añadir" %> Cliente</h2>
    <form action="ClienteServlet" method="post">
        <input type="hidden" name="action" value="<%= editando ? "update" : "add" %>"/>
        <% if (editando) { %>
            <input type="hidden" name="id" value="<%= cliente.getId() %>"/>
        <% } %>

        <label for="nombre">Nombre</label>
        <input type="text" name="nombre" placeholder="Juan" value="<%= editando ? cliente.getNombre() : "" %>" required/>

        <label for="primerApellido">Primer Apellido</label>
        <input type="text" name="primerApellido" placeholder="Pérez" value="<%= editando ? cliente.getPrimerApellido() : "" %>" required/>

        <label for="segundoApellido">Segundo Apellido</label>
        <input type="text" name="segundoApellido" placeholder="Gómez" value="<%= editando ? cliente.getSegundoApellido() : "" %>" required/>

        <label for="telefono">Teléfono</label>
        <input type="text" name="telefono" placeholder="8888-1234" value="<%= editando ? cliente.getTelefono() : "" %>" required/>

        <label for="direccion">Dirección</label>
        <input type="text" name="direccion" placeholder="San José, Costa Rica" value="<%= editando ? cliente.getDireccion() : "" %>" required/>

        <label for="email">Email</label>
        <input type="email" name="email" placeholder="correo@dominio.com" value="<%= editando ? cliente.getEmail() : "" %>" required/>

        <input type="submit" value="Guardar"/>
    </form>
    <a class="back-link" href="ClienteServlet">← Volver a la lista</a>
</div>
</body>
</html>

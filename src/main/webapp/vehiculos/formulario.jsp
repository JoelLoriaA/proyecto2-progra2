<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Vehiculo" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente" %>
<%@ page import="java.util.List" %>
<%
    Vehiculo v = (Vehiculo) request.getAttribute("vehiculo");
    boolean editando = (v != null);
    String error = (String) request.getAttribute("error");
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= editando ? "Editar Vehículo" : "Nuevo Vehículo" %></title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background: #121212; color: #f1f1f1; display: flex; justify-content: center; align-items: center; min-height: 100vh; padding: 20px; }
        .form-container { background: #1e1e1e; padding: 35px 40px; border-radius: 10px; box-shadow: 0 0 18px rgba(255,60,0,0.4); width: 480px; border: 1px solid #333; }
        h2 { text-align: center; color: #ff3c00; margin-bottom: 25px; }
        label { display: block; margin-top: 18px; margin-bottom: 6px; color: #bbbbbb; }
        input, select { width: 100%; padding: 10px 12px; border: 1px solid #444; border-radius: 5px; background: #2c2c2c; color: #fff; margin-bottom: 10px; }
        input[type="submit"] { background: #ff3c00; color: #fff; font-weight: 600; cursor: pointer; border: none; }
        input[type="submit"]:hover { background: #e03a00; }
        .back-link { display: block; text-align: center; margin-top: 20px; color: #ff3c00; text-decoration: none; }
        .back-link:hover { color: #ffa07a; }
        .error-message { color: #ff4d4d; background-color: #2a2a2a; border: 1px solid #ff4d4d; padding: 10px; margin-bottom: 20px; border-radius: 6px; text-align: center; }
        .help-text { font-size: 12px; color: #888; margin-top: -8px; margin-bottom: 10px; }
    </style>
</head>
<body>
<div class="form-container">
    <h2><%= editando ? "Editar" : "Nuevo" %> Vehículo</h2>

    <form action="VehiculoServlet" method="post">
        <% if (error != null) { %>
        <div class="error-message"><%= error %></div>
        <% } %>

        <label for="numeroPlaca">Número de Placa:</label>
        <input type="text" name="numeroPlaca" id="numeroPlaca"
               pattern="[A-Za-z]{3}[-]?[0-9]{3}"
               oninput="this.value = this.value.replace(/[^a-zA-Z0-9-]/g, '').toUpperCase()"
               value="<%= editando ? v.getNumeroPlaca() : "" %>"
                <%= editando ? "readonly" : "" %> required/>
        <div class="help-text">Formato: ABC123 o ABC-123</div>

        <label for="marca">Marca:</label>
        <input type="text" name="marca" id="marca"
               pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 ]{1,50}"
               oninput="this.value = this.value.replace(/[^a-zA-ZÁÉÍÓÚáéíóúñÑ0-9 ]/g, '')"
               maxlength="50"
               value="<%= editando ? v.getMarca() : "" %>" required/>

        <label for="estilo">Estilo:</label>
        <input type="text" name="estilo" id="estilo"
               pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 ]{1,50}"
               oninput="this.value = this.value.replace(/[^a-zA-ZÁÉÍÓÚáéíóúñÑ0-9 ]/g, '')"
               maxlength="50"
               value="<%= editando ? v.getEstilo() : "" %>" required/>

        <label for="color">Color:</label>
        <input type="text" name="color" id="color"
               pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ ]{1,30}"
               oninput="this.value = this.value.replace(/[^a-zA-ZÁÉÍÓÚáéíóúñÑ ]/g, '')"
               maxlength="30"
               value="<%= editando ? v.getColor() : "" %>" required/>

        <label for="anio">Año:</label>
        <input type="number" name="anio" id="anio" min="1900" max="2026"
               value="<%= editando ? v.getAnio() : "" %>" required/>

        <label for="vin">VIN:</label>
        <input type="text" name="vin" id="vin"
               pattern="[A-HJ-NPR-Z0-9]{17}"
               oninput="this.value = this.value.replace(/[^A-HJ-NPR-Z0-9]/g, '').toUpperCase()"
               maxlength="17"
               value="<%= editando ? v.getVin() : "" %>" required/>
        <div class="help-text">17 caracteres alfanuméricos (sin I, O, Q)</div>

        <label for="cilindraje">Cilindraje (CC):</label>
        <input type="number" name="cilindraje" id="cilindraje" min="1" max="10000" step="0.1"
               value="<%= editando ? v.getCilindraje() : "" %>" required/>

        <label for="duenio">Dueño:</label>
        <input type="text" name="duenio" id="duenio"
               pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ ]{1,100}"
               oninput="this.value = this.value.replace(/[^a-zA-ZÁÉÍÓÚáéíóúñÑ ]/g, '')"
               maxlength="100"
               value="<%= editando ? v.getDuenio() : "" %>" required/>

        <input type="submit" value="Guardar"/>
    </form>

    <a class="back-link" href="VehiculoServlet">← Volver a la lista</a>
</div>
</body>
</html>
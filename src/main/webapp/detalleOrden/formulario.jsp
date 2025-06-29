<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.DetalleOrden" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.TipoDetalle" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Estado" %>
<html>
<head>
    <title>Detalle de Orden</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background: #121212; color: #f1f1f1; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .form-container { background: #1e1e1e; padding: 35px 40px; border-radius: 10px; box-shadow: 0 0 18px rgba(255,60,0,0.4); width: 480px; border: 1px solid #333; }
        h2 { text-align: center; color: #ff3c00; margin-bottom: 25px; }
        label { display: block; margin-top: 18px; margin-bottom: 6px; color: #bbbbbb; }
        input, select, textarea { width: 100%; padding: 10px 12px; border: 1px solid #444; border-radius: 5px; background: #2c2c2c; color: #fff; margin-bottom: 10px; }
        input[type="submit"] { background: #ff3c00; color: #fff; font-weight: 600; cursor: pointer; border: none; }
        input[type="submit"]:hover { background: #e03a00; }
        .back-link { display: block; text-align: center; margin-top: 20px; color: #ff3c00; text-decoration: none; }
        .back-link:hover { color: #ffa07a; }
    </style>
</head>
<body>
<%
    DetalleOrden detalle = (DetalleOrden) request.getAttribute("detalle");
    boolean editando = (detalle != null);
    int idOrdenTrabajo = (Integer) request.getAttribute("idOrdenTrabajo");
    java.util.List<TipoDetalle> tipos = (java.util.List<TipoDetalle>) request.getAttribute("tiposDetalle");
    java.util.List<Estado> estados = (java.util.List<Estado>) request.getAttribute("estados");
%>
<div class="form-container">
    <h2><%= editando ? "Editar" : "Añadir" %> Detalle de Orden</h2>
    <form action="DetalleOrdenServlet" method="post">
        <input type="hidden" name="action" value="<%= editando ? "update" : "add" %>"/>
        <% if (editando) { %>
            <input type="hidden" name="idDetalleOrden" value="<%= detalle.getIdDetalleOrden() %>"/>
        <% } %>
        <input type="hidden" name="idOrdenTrabajo" value="<%= idOrdenTrabajo %>"/>

        <label for="tipoDetalle">Tipo de Detalle</label>
        <select name="tipoDetalleId" required>
            <option value="">Seleccione...</option>
            <% for (TipoDetalle tipo : tipos) { %>
                <option value="<%= tipo.getId() %>" <%= editando && detalle.getTipoDetalle().getId() == tipo.getId() ? "selected" : "" %>><%= tipo.getNombre() %></option>
            <% } %>
        </select>

        <label for="estado">Estado</label>
        <select name="estadoId" required>
            <option value="">Seleccione...</option>
            <% for (Estado estado : estados) { %>
                <option value="<%= estado.getId() %>" <%= editando && detalle.getEstado().getId() == estado.getId() ? "selected" : "" %>><%= estado.getDescripcion() %></option>
            <% } %>
        </select>

        <label for="cantidad">Cantidad</label>
        <input type="number" name="cantidad" min="1" value="<%= editando ? detalle.getCantidad() : "" %>" required/>

        <label for="nombreRepuesto">Nombre Repuesto</label>
        <input type="text" name="nombreRepuesto" value="<%= editando ? detalle.getNombreRepuesto() : "" %>"/>

        <label for="precio">Precio</label>
        <input type="number" step="0.01" name="precio" value="<%= editando ? detalle.getPrecio() : "" %>" required/>

        <label for="costoManoObra">Costo Mano de Obra</label>
        <input type="number" step="0.01" name="costoManoObra" value="<%= editando ? detalle.getCostoManoObra() : "" %>"/>

        <label for="repuestoPedido">¿Repuesto Pedido?</label>
        <select name="repuestoPedido">
            <option value="false" <%= editando && !detalle.isRepuestoPedido() ? "selected" : "" %>>No</option>
            <option value="true" <%= editando && detalle.isRepuestoPedido() ? "selected" : "" %>>Sí</option>
        </select>

        <label for="observaciones">Observaciones</label>
        <textarea name="observaciones" rows="3"><%= editando ? detalle.getObservaciones() : "" %></textarea>

        <input type="submit" value="Guardar"/>
    </form>
    <a class="back-link" href="DetalleOrdenServlet?idOrdenTrabajo=<%= idOrdenTrabajo %>">← Volver a la lista</a>
</div>
</body>
</html>
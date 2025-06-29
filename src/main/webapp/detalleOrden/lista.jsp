<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.DetalleOrden" %>
<html>
<head>
    <title>Detalles de la Orden</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background: #121212; color: #f1f1f1; padding: 40px; }
        h2 { text-align: center; color: #ff3c00; margin-bottom: 25px; }
        .top-link { display: block; width: fit-content; margin: 0 auto 20px; padding: 10px 16px; background: #ff3c00; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; }
        .top-link:hover { background: #e03a00; }
        table { width: 100%; border-collapse: collapse; background: #1e1e1e; box-shadow: 0 0 12px rgba(255,60,0,0.3); border-radius: 6px; }
        th, td { padding: 12px 15px; border-bottom: 1px solid #333; text-align: center; }
        th { background: #2a2a2a; color: #ff3c00; }
        tr:hover { background: #2d2d2d; }
        td a { display: inline-block; text-decoration: none; padding: 8px 14px; border-radius: 5px; font-weight: 600; font-size: 14px; margin: 0 5px; }
        .edit-link { background: #3498db; color: white; }
        .edit-link:hover { background: #2980b9; }
        .delete-link { background: #e74c3c; color: white; }
        .delete-link:hover { background: #c0392b; }
        .no-data { text-align: center; padding: 20px; color: #bbb; }
    </style>
</head>
<body>
    <h2>Detalles de la Orden #<%= request.getAttribute("idOrdenTrabajo") %></h2>
    <a class="top-link" href="DetalleOrdenServlet?action=showForm&idOrdenTrabajo=<%= request.getAttribute("idOrdenTrabajo") %>">➕ Añadir Detalle</a>
    <table>
        <tr>
            <th>ID</th>
            <th>Tipo</th>
            <th>Estado</th>
            <th>Cantidad</th>
            <th>Repuesto</th>
            <th>Precio</th>
            <th>Mano de Obra</th>
            <th>Pedido</th>
            <th>Observaciones</th>
            <th>Acciones</th>
        </tr>
        <%
            List<DetalleOrden> detalles = (List<DetalleOrden>) request.getAttribute("detalles");
            if (detalles != null && !detalles.isEmpty()) {
                for (DetalleOrden d : detalles) {
        %>
        <tr>
            <td><%= d.getIdDetalleOrden() %></td>
            <td><%= d.getTipoDetalle().getNombre() %></td>
            <td><%= d.getEstado().getDescripcion() %></td>
            <td><%= d.getCantidad() %></td>
            <td><%= d.getNombreRepuesto() %></td>
            <td><%= d.getPrecio() %></td>
            <td><%= d.getCostoManoObra() %></td>
            <td><%= d.isRepuestoPedido() ? "Sí" : "No" %></td>
            <td><%= d.getObservaciones() %></td>
            <td>
                <a class="edit-link" href="DetalleOrdenServlet?action=edit&idDetalleOrden=<%=d.getIdDetalleOrden()%>&idOrdenTrabajo=<%=d.getIdOrdenTrabajo()%>">Editar</a>
                <a class="delete-link" href="DetalleOrdenServlet?action=delete&idDetalleOrden=<%=d.getIdDetalleOrden()%>&idOrdenTrabajo=<%=d.getIdOrdenTrabajo()%>"
                   onclick="return confirm('¿Seguro que desea borrar este detalle?');">Borrar</a>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="10" class="no-data">No hay detalles registrados para esta orden.</td>
        </tr>
        <%
            }
        %>
    </table>
    <a class="top-link" href="OrdenServlet">← Volver a Órdenes</a>
</body>
</html>
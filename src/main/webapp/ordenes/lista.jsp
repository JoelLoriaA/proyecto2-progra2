<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.OrdenDeTrabajo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<OrdenDeTrabajo> ordenes = (List<OrdenDeTrabajo>) request.getAttribute("ordenes");
    String filtro = request.getParameter("filtro") != null ? request.getParameter("filtro") : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Órdenes de Trabajo</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #121212; padding: 40px; color: #f1f1f1; }
        .home-button { display: inline-block; background-color: #ff3c00; color: white; text-decoration: none; padding: 10px 16px; border-radius: 5px; font-weight: bold; transition: background-color 0.3s; margin-bottom: 25px; }
        .home-button:hover { background-color: #e03a00; }
        h2 { text-align: center; color: #ff3c00; margin-bottom: 30px; }
        .top-link { display: block; width: fit-content; margin: 0 auto 20px; padding: 12px 20px; background-color: #ff3c00; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; transition: background-color 0.3s ease; }
        .top-link:hover { background-color: #e03a00; }
        .search-form { display: flex; justify-content: center; margin-bottom: 25px; gap: 10px; }
        .search-form input[type="text"] { padding: 10px 15px; font-size: 14px; border: 1px solid #444; border-radius: 5px; background-color: #1e1e1e; color: #f1f1f1; width: 300px; }
        .search-form input[type="submit"] { padding: 10px 20px; background-color: #ff3c00; color: white; font-weight: bold; border: none; border-radius: 5px; cursor: pointer; }
        .search-form input[type="submit"]:hover { background-color: #e03a00; }
        table { width: 100%; border-collapse: collapse; background-color: #1e1e1e; box-shadow: 0 0 12px rgba(255, 60, 0, 0.3); border-radius: 8px; overflow: hidden; }
        th, td { padding: 12px; border-bottom: 1px solid #333; text-align: center; font-size: 14px; }
        th { background-color: #2a2a2a; color: #ff3c00; font-weight: 600; }
        tr:hover { background-color: #2d2d2d; }
        .estado { padding: 4px 8px; border-radius: 12px; font-size: 12px; font-weight: bold; }
        .estado-1 { background-color: #3498db; color: white; }
        .estado-2 { background-color: #f39c12; color: white; }
        .estado-3 { background-color: #27ae60; color: white; }
        .estado-4 { background-color: #95a5a6; color: white; }
        .estado-5 { background-color: #e74c3c; color: white; }
        .action-btn { display: inline-block; text-decoration: none; padding: 6px 10px; margin: 2px; border-radius: 4px; font-weight: bold; font-size: 12px; color: white; }
        .view-btn { background-color: #3498db; }
        .view-btn:hover { background-color: #2980b9; }
        .edit-btn { background-color: #f39c12; }
        .edit-btn:hover { background-color: #e67e22; }
        .delete-btn { background-color: #e74c3c; }
        .delete-btn:hover { background-color: #c0392b; }
        .no-data { text-align: center; padding: 40px; color: #bbb; font-style: italic; }
    </style>
</head>
<body>

<a href="inicio.jsp" class="home-button"><i class="fas fa-home"></i> Inicio</a>

<h2><i class="fas fa-clipboard-list"></i> Órdenes de Trabajo</h2>

<a class="top-link" href="OrdenDeTrabajoServlet?action=nuevo">
    <i class="fas fa-plus"></i> Nueva Orden de Trabajo
</a>

<form method="get" action="OrdenDeTrabajoServlet" class="search-form">
    <input type="text" name="filtro" placeholder="Buscar por ID, placa, cliente o descripción" value="<%= filtro %>" />
    <input type="submit" value="Buscar" />
</form>

<table>
    <tr>
        <th>ID</th>
        <th>Fecha Ingreso</th>
        <th>Vehículo</th>
        <th>Cliente</th>
        <th>Descripción</th>
        <th>Estado</th>
        <th>Acciones</th>
    </tr>
    <%
        if (ordenes != null && !ordenes.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (OrdenDeTrabajo orden : ordenes) {
    %>
    <tr>
        <td><strong>#<%= orden.getIdOrden() %></strong></td>
        <td><%= orden.getFechaIngreso().format(formatter) %></td>
        <td><%= orden.getNumeroPlaca() %></td>
        <td><%= orden.getIdCliente() %></td>
        <td><%= orden.getDescripcionSolicitud().length() > 30 ? orden.getDescripcionSolicitud().substring(0, 30) + "..." : orden.getDescripcionSolicitud() %></td>
        <td>
            <span class="estado estado-<%= orden.getEstado().getId() %>">
                <%= orden.getEstado().getDescripcion() %>
            </span>
        </td>
        <td>
           <a class="action-btn view-btn" href="OrdenDeTrabajoServlet?action=detalle&id=<%= orden.getIdOrden() %>">
                <i class="fas fa-eye"></i> Ver
            </a>
            <% if (orden.getEstado().getId() < 4) { %>
            <a class="action-btn edit-btn" href="OrdenDeTrabajoServlet?action=editar&id=<%= orden.getIdOrden() %>">
                <i class="fas fa-edit"></i> Editar
            </a>
            <% } %>
            <a class="action-btn delete-btn" href="OrdenDeTrabajoServlet?action=eliminar&id=<%= orden.getIdOrden() %>"
               onclick="return confirm('¿Está seguro de eliminar esta orden de trabajo?')">
                <i class="fas fa-trash"></i> Eliminar
            </a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr><td colspan="7" class="no-data">No hay órdenes de trabajo registradas.</td></tr>
    <%
        }
    %>
</table>

</body>
</html>
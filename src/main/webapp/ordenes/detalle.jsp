<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*" %>
    <%@ page import="java.time.format.DateTimeFormatter" %>
    <%
        OrdenDeTrabajo orden = (OrdenDeTrabajo) request.getAttribute("orden");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Boolean puedeModificarDetalles = (Boolean) request.getAttribute("puedeModificarDetalles");
        boolean puedeEditar = puedeModificarDetalles == null || puedeModificarDetalles;
    %>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Orden de Trabajo #<%= orden.getIdOrden() %></title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body { font-family: 'Roboto', sans-serif; background: #121212; color: #f1f1f1; padding: 20px; }
            .container { max-width: 900px; margin: 0 auto; background: #1e1e1e; padding: 30px; border-radius: 10px; box-shadow: 0 0 18px rgba(255,60,0,0.4); border: 1px solid #333; }
            .header { text-align: center; margin-bottom: 30px; border-bottom: 2px solid #ff3c00; padding-bottom: 20px; }
            .header h1 { color: #ff3c00; margin: 0; }
            .orden-id { font-size: 24px; color: #ffffff; margin-top: 10px; }
            .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; margin-bottom: 30px; }
            .info-section { background: #2a2a2a; padding: 20px; border-radius: 8px; border: 1px solid #444; }
            .info-section h3 { color: #ff3c00; margin: 0 0 15px 0; border-bottom: 1px solid #444; padding-bottom: 8px; }
            .info-row { display: flex; justify-content: space-between; margin-bottom: 10px; }
            .info-label { font-weight: 500; color: #bbbbbb; }
            .info-value { color: #ffffff; }
            .estado { padding: 6px 12px; border-radius: 15px; font-size: 14px; font-weight: bold; }
            .estado-1 { background-color: #3498db; color: white; }
            .estado-2 { background-color: #f39c12; color: white; }
            .estado-3 { background-color: #27ae60; color: white; }
            .estado-4 { background-color: #95a5a6; color: white; }
            .estado-5 { background-color: #e74c3c; color: white; }
            .estado-controls { margin-top: 15px; }
            .estado-controls select { padding: 8px; border-radius: 5px; background: #2c2c2c; color: #fff; border: 1px solid #444; margin-right: 10px; }
            .estado-controls button { padding: 8px 15px; background: #ff3c00; color: white; border: none; border-radius: 5px; cursor: pointer; }
            .estado-controls button:hover { background: #e03a00; }
            .description-box { grid-column: 1 / -1; background: #2a2a2a; padding: 20px; border-radius: 8px; border: 1px solid #444; margin-bottom: 30px; }
            .description-box h3 { color: #ff3c00; margin: 0 0 15px 0; }
            .detalles-section { margin-bottom: 30px; }
            .detalles-section h3 { color: #ff3c00; margin-bottom: 20px; text-align: center; }
            .detalle-card { background: #2a2a2a; border: 1px solid #444; border-radius: 8px; padding: 15px; margin-bottom: 15px; }
            .detalle-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
            .detalle-tipo { padding: 4px 8px; border-radius: 10px; font-size: 12px; font-weight: bold; }
            .tipo-1 { background-color: #3498db; color: white; }
            .tipo-2 { background-color: #27ae60; color: white; }
            .detalle-grid { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 15px; }
            .detalle-info { display: flex; flex-direction: column; }
            .detalle-info strong { color: #ff3c00; font-size: 12px; margin-bottom: 3px; }
            .cost-breakdown { background: #2a2a2a; padding: 20px; border-radius: 8px; border: 1px solid #444; }
            .cost-breakdown h3 { color: #ff3c00; margin: 0 0 15px 0; text-align: center; }
            .cost-row { display: flex; justify-content: space-between; margin-bottom: 8px; padding: 5px 0; }
            .cost-label { color: #bbbbbb; }
            .cost-value { color: #ffffff; font-weight: 500; }
            .total-final { border-top: 2px solid #ff3c00; margin-top: 15px; padding-top: 15px; font-size: 18px; font-weight: bold; }
            .total-final .cost-value { color: #27ae60; }
            .actions { display: flex; justify-content: center; gap: 15px; margin-top: 30px; }
            .action-btn { padding: 12px 20px; border: none; border-radius: 5px; font-weight: bold; text-decoration: none; color: white; cursor: pointer; }
            .edit-btn { background-color: #f39c12; }
            .edit-btn:hover { background-color: #e67e22; }
            .print-btn { background-color: #3498db; }
            .print-btn:hover { background-color: #2980b9; }
            .back-btn { background-color: #95a5a6; }
            .back-btn:hover { background-color: #7f8c8d; }
            .no-detalles { text-align: center; color: #bbb; padding: 20px; font-style: italic; }
            .alert-info { background-color: #3498db; color: white; padding: 10px 15px; border-radius: 5px; margin: 10px 0; display: flex; align-items: center; gap: 8px; }
            .alert-warning { background-color: #f39c12; color: white; padding: 10px 15px; border-radius: 5px; margin: 10px 0; display: flex; align-items: center; gap: 8px; }
            @media print {
                body { background: white; color: black; }
                .container { box-shadow: none; border: none; }
                .actions, .estado-controls, .alert-info, .alert-warning { display: none; }
            }
        </style>
    </head>
    <body>
    <div class="container">
        <div class="header">
            <h1><i class="fas fa-clipboard-list"></i> Orden de Trabajo</h1>
            <div class="orden-id">#<%= orden.getIdOrden() %></div>
        </div>

        <% if (!puedeEditar) { %>
        <div class="alert-warning">
            <i class="fas fa-info-circle"></i>
            Esta orden está <%= orden.getEstado().getDescripcion().toLowerCase() %>. No se pueden agregar más repuestos/servicios.
        </div>
        <% } else { %>
        <div class="alert-info">
            <i class="fas fa-tools"></i>
            El vehículo está en el taller. Puede agregar repuestos/servicios según sea necesario.
        </div>
        <% } %>

        <div class="info-grid">
            <div class="info-section">
                <h3><i class="fas fa-info-circle"></i> Información General</h3>
                <div class="info-row">
                    <span class="info-label">Fecha de Ingreso:</span>
                    <span class="info-value"><%= orden.getFechaIngreso().format(formatter) %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Estado:</span>
                    <span class="estado estado-<%= orden.getEstado().getId() %>">
                        <%= orden.getEstado().getDescripcion() %>
                    </span>
                </div>
                <% if (orden.getEstado().getId() < 4) { %>
                <div class="estado-controls">
                    <select id="nuevoEstado">
                        <option value="1" <%= orden.getEstado().getId() == 1 ? "selected" : "" %>>Recibida</option>
                        <option value="2" <%= orden.getEstado().getId() == 2 ? "selected" : "" %>>En reparación</option>
                        <option value="3" <%= orden.getEstado().getId() == 3 ? "selected" : "" %>>En espera de repuestos</option>
                        <option value="4">Marcar como Entregado</option>
                        <option value="5">Cancelar Orden</option>
                    </select>
                    <button onclick="cambiarEstado()">
                        <i class="fas fa-sync-alt"></i> Cambiar Estado
                    </button>
                </div>
                <% } %>
            </div>

            <div class="info-section">
                <h3><i class="fas fa-car"></i> Vehículo y Cliente</h3>
                <div class="info-row">
                    <span class="info-label">Número de Placa:</span>
                    <span class="info-value"><%= orden.getNumeroPlaca() %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">ID Cliente:</span>
                    <span class="info-value"><%= orden.getIdCliente() %></span>
                </div>
            </div>
        </div>

        <div class="description-box">
            <h3><i class="fas fa-file-alt"></i> Descripción de la Solicitud</h3>
            <p><%= orden.getDescripcionSolicitud() %></p>
        </div>

        <% if (orden.getObservacionesRecepcion() != null && !orden.getObservacionesRecepcion().trim().isEmpty()) { %>
        <div class="description-box">
            <h3><i class="fas fa-sticky-note"></i> Observaciones de Recepción</h3>
            <p><%= orden.getObservacionesRecepcion() %></p>
        </div>
        <% } %>

        <div class="detalles-section">
            <h3><i class="fas fa-tasks"></i> Detalles de Trabajo</h3>
            <% if (orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
                for (DetalleOrden detalle : orden.getDetalles()) {
            %>
            <div class="detalle-card">
                <div class="detalle-header">
                    <h4 style="margin: 0; color: #ffffff;"><%= detalle.getNombreRepuesto() %></h4>
                    <span class="detalle-tipo tipo-<%= detalle.getTipoDetalle().getId() %>">
                        <%= detalle.getTipoDetalle().getNombre() %>
                    </span>
                </div>
                <div class="detalle-grid">
                    <div class="detalle-info">
                        <strong>Cantidad:</strong>
                        <span><%= detalle.getCantidad() %></span>
                    </div>
                    <div class="detalle-info">
                        <strong>Precio Unitario:</strong>
                        <span>₡<%= String.format("%,.2f", detalle.getPrecio()) %></span>
                    </div>
                    <div class="detalle-info">
                        <strong>Subtotal:</strong>
                        <span>₡<%= String.format("%,.2f", detalle.getCantidad() * detalle.getPrecio()) %></span>
                    </div>
                    <div class="detalle-info">
                        <strong>Mano de Obra:</strong>
                        <span>₡<%= String.format("%,.2f", detalle.getCostoManoObra()) %></span>
                    </div>
                    <div class="detalle-info">
                        <strong>Estado del Repuesto:</strong>
                        <span><%= detalle.isRepuestoPedido() ? "Pedido" : "En stock" %></span>
                    </div>
                    <% if (detalle.getObservaciones() != null && !detalle.getObservaciones().trim().isEmpty()) { %>
                    <div class="detalle-info">
                        <strong>Observaciones:</strong>
                        <span><%= detalle.getObservaciones() %></span>
                    </div>
                    <% } %>
                </div>
            </div>
            <% }
            } else { %>
            <div class="no-detalles">
                <i class="fas fa-exclamation-circle"></i> No hay detalles registrados para esta orden.
            </div>
            <% } %>
        </div>

        <% if (orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
            double totalRepuestos = 0;
            double totalServicios = 0;
            double totalManoObra = 0;

            for (DetalleOrden detalle : orden.getDetalles()) {
                double subtotal = detalle.getCantidad() * detalle.getPrecio();
                if (detalle.getTipoDetalle().getId() == 1) {
                    totalRepuestos += subtotal;
                } else {
                    totalServicios += subtotal;
                }
                totalManoObra += detalle.getCostoManoObra();
            }
            double totalGeneral = totalRepuestos + totalServicios + totalManoObra;
        %>
        <div class="cost-breakdown">
            <h3><i class="fas fa-calculator"></i> Resumen de Costos</h3>
            <div class="cost-row">
                <span class="cost-label">Total en Repuestos:</span>
                <span class="cost-value">₡<%= String.format("%,.2f", totalRepuestos) %></span>
            </div>
            <div class="cost-row">
                <span class="cost-label">Total en Servicios:</span>
                <span class="cost-value">₡<%= String.format("%,.2f", totalServicios) %></span>
            </div>
            <div class="cost-row">
                <span class="cost-label">Total Mano de Obra:</span>
                <span class="cost-value">₡<%= String.format("%,.2f", totalManoObra) %></span>
            </div>
            <div class="cost-row total-final">
                <span class="cost-label">TOTAL GENERAL:</span>
                <span class="cost-value">₡<%= String.format("%,.2f", totalGeneral) %></span>
            </div>
        </div>
        <% } %>

        <div class="actions">
            <% if (puedeEditar) { %>
            <a href="OrdenDeTrabajoServlet?action=editar&id=<%= orden.getIdOrden() %>" class="action-btn edit-btn">
                <i class="fas fa-edit"></i> Editar Orden
            </a>
            <% } %>
            <button onclick="window.print()" class="action-btn print-btn">
                <i class="fas fa-print"></i> Imprimir
            </button>
            <a href="OrdenDeTrabajoServlet" class="action-btn back-btn">
                <i class="fas fa-arrow-left"></i> Volver a Lista
            </a>
        </div>
    </div>

    <script>
    function cambiarEstado() {
        const nuevoEstado = document.getElementById('nuevoEstado').value;
        const estadoTexto = document.getElementById('nuevoEstado').selectedOptions[0].text;

        let mensaje = '¿Está seguro de cambiar el estado a "' + estadoTexto + '"?';

        if (nuevoEstado === '4') {
            mensaje += '\n\nAl marcar como "Entregado", no se podrán agregar más repuestos/servicios.';
        } else if (nuevoEstado === '5') {
            mensaje += '\n\nAl cancelar la orden, no se podrán realizar más modificaciones.';
        }

        if (confirm(mensaje)) {
            window.location.href = 'OrdenDeTrabajoServlet?action=cambiarEstado&id=<%= orden.getIdOrden() %>&estado=' + nuevoEstado;
        }
    }
    </script>
    </body>
    </html>
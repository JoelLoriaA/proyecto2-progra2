<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    OrdenDeTrabajo orden = (OrdenDeTrabajo) request.getAttribute("orden");
    List<Vehiculo> vehiculos = (List<Vehiculo>) request.getAttribute("vehiculos");
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    List<Repuesto> repuestos = (List<Repuesto>) request.getAttribute("repuestos");
    List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
    String error = (String) request.getAttribute("error");
    boolean esNuevo = orden == null || orden.getIdOrden() == 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= esNuevo ? "Nueva" : "Editar" %> Orden de Trabajo</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%);
            color: #f1f1f1;
            padding: 20px;
            margin: 0;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
            background: #2a2a2a;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            border: 1px solid #444;
        }
        .header {
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 2px solid #ff3c00;
            padding-bottom: 15px;
        }
        .header h1 {
            color: #ff3c00;
            margin: 0;
            font-size: 28px;
            font-weight: 700;
        }
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group.full-width {
            grid-column: 1 / -1;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #ccc;
            font-size: 14px;
        }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #555;
            border-radius: 8px;
            background-color: #3a3a3a;
            color: #f1f1f1;
            box-sizing: border-box;
            font-size: 14px;
        }
        .form-group input:focus, .form-group select:focus, .form-group textarea:focus {
            outline: none;
            border-color: #ff3c00;
            box-shadow: 0 0 0 2px rgba(255, 60, 0, 0.2);
        }
        .detalles-section {
            margin-top: 30px;
            background: #333;
            padding: 20px;
            border-radius: 10px;
        }
        .detalles-section h3 {
            color: #ff3c00;
            margin: 0 0 20px 0;
            font-size: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .btn-add-detail {
            background: #28a745;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 12px;
            font-weight: bold;
        }
        .btn-add-detail:hover {
            background: #218838;
        }
        .detalle-form {
            background: #444;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            border: 1px solid #555;
        }
        .detalle-row {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr 1fr;
            gap: 15px;
            margin-bottom: 15px;
        }
        .detalle-row-2 {
            display: grid;
            grid-template-columns: 2fr 2fr 1fr;
            gap: 15px;
            align-items: end;
        }
        .checkbox-group {
            display: flex;
            align-items: center;
            gap: 8px;
            padding-top: 25px;
        }
        .checkbox-group input[type="checkbox"] {
            width: auto;
        }
        .detalles-lista {
            margin-top: 20px;
        }
        .detalle-item {
            background: #3a3a3a;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 10px;
            border-left: 4px solid #ff3c00;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .detalle-content {
            flex-grow: 1;
        }
        .detalle-content h4 {
            margin: 0 0 8px 0;
            color: #fff;
        }
        .detalle-content p {
            margin: 0;
            color: #ccc;
            font-size: 13px;
        }
        .btn-remove {
            background: #dc3545;
            color: white;
            border: none;
            padding: 6px 10px;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-remove:hover {
            background: #c82333;
        }
        .resumen-costos {
            background: #333;
            padding: 20px;
            border-radius: 10px;
            margin-top: 20px;
        }
        .resumen-costos h3 {
            color: #ff3c00;
            margin: 0 0 15px 0;
        }
        .costo-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
            padding: 5px 0;
        }
        .total-final {
            border-top: 2px solid #ff3c00;
            margin-top: 15px;
            padding-top: 15px;
            font-size: 18px;
            font-weight: bold;
            color: #28a745;
        }
        .btn-group {
            display: flex;
            gap: 15px;
            justify-content: center;
            margin-top: 30px;
        }
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            font-size: 14px;
        }
        .btn-primary {
            background: #ff3c00;
            color: white;
        }
        .btn-primary:hover {
            background: #e03a00;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        .error {
            background: #e74c3c;
            color: white;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .home-button {
            display: inline-block;
            background: #ff3c00;
            color: white;
            text-decoration: none;
            padding: 10px 16px;
            border-radius: 6px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .tipo-badge {
            padding: 3px 8px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: bold;
        }
        .tipo-repuesto {
            background: #3498db;
            color: white;
        }
        .tipo-servicio {
            background: #27ae60;
            color: white;
        }
    </style>
</head>
<body>
<a href="inicio.jsp" class="home-button"><i class="fas fa-home"></i> Inicio</a>

<div class="container">
    <div class="header">
        <h1><%= esNuevo ? "Nueva" : "Editar" %> Orden de Trabajo</h1>
        <% if (!esNuevo) { %>
        <p>Orden #<%= orden.getIdOrden() %></p>
        <% } %>
    </div>

    <% if (error != null) { %>
    <div class="error">
        <i class="fas fa-exclamation-circle"></i> <%= error %>
    </div>
    <% } %>

    <form method="post" action="OrdenDeTrabajoServlet" id="ordenForm">
        <% if (!esNuevo) { %>
        <input type="hidden" name="id" value="<%= orden.getIdOrden() %>">
        <% } %>

        <div class="form-row">
            <div class="form-group">
                <label for="numeroPlaca">Vehículo (Placa):</label>
                <select id="numeroPlaca" name="numeroPlaca" required <%= esNuevo ? "" : "disabled" %>>
                    <option value="">Seleccione un vehículo</option>
                    <% if (vehiculos != null) {
                        for (Vehiculo vehiculo : vehiculos) { %>
                    <option value="<%= vehiculo.getNumeroPlaca() %>"
                            <%= (orden != null && orden.getNumeroPlaca() != null && orden.getNumeroPlaca().equals(vehiculo.getNumeroPlaca())) ? "selected" : "" %>>
                        <%= vehiculo.getNumeroPlaca() %> - <%= vehiculo.getMarca() %> <%= vehiculo.getAnio() %>
                    </option>
                    <% } } %>
                </select>
                <% if (!esNuevo) { %>
                <input type="hidden" name="numeroPlaca" value="<%= orden.getNumeroPlaca() %>">
                <% } %>
            </div>

            <div class="form-group">
                <label for="idCliente">Cliente:</label>
                <select id="idCliente" name="idCliente" required <%= esNuevo ? "" : "disabled" %>>
                    <option value="">Seleccione un cliente</option>
                    <% if (clientes != null) {
                        for (Cliente cliente : clientes) { %>
                    <option value="<%= cliente.getId() %>"
                            <%= (orden != null && orden.getIdCliente() == cliente.getId()) ? "selected" : "" %>>
                        <%= cliente.getNombre() %> <%= cliente.getPrimerApellido() %>
                    </option>
                    <% } } %>
                </select>
                <% if (!esNuevo) { %>
                <input type="hidden" name="idCliente" value="<%= orden.getIdCliente() %>">
                <% } %>
            </div>
        </div>

        <div class="form-group full-width">
            <label for="descripcionSolicitud">Descripción de la Solicitud:</label>
            <textarea id="descripcionSolicitud" name="descripcionSolicitud" rows="4" required><%= (orden != null && orden.getDescripcionSolicitud() != null) ? orden.getDescripcionSolicitud() : "" %></textarea>
        </div>

        <div class="form-group full-width">
            <label for="observacionesRecepcion">Observaciones de Recepción:</label>
            <textarea id="observacionesRecepcion" name="observacionesRecepcion" rows="3"><%= (orden != null && orden.getObservacionesRecepcion() != null) ? orden.getObservacionesRecepcion() : "" %></textarea>
        </div>

        <div class="detalles-section">
            <h3>
                Detalles de Trabajo
                <button type="button" class="btn-add-detail" onclick="mostrarFormularioDetalle()">
                    + Agregar Detalle
                </button>
            </h3>

            <div id="formularioDetalle" class="detalle-form" style="display: none;">
                <div class="detalle-row">
                    <div class="form-group">
                        <label for="tipoDetalle">Tipo:</label>
                        <select id="tipoDetalle" onchange="actualizarOpciones()">
                            <option value="">Seleccione</option>
                            <option value="repuesto">Repuesto</option>
                            <option value="servicio">Servicio</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="cantidad">Cantidad:</label>
                        <input type="number" id="cantidad" min="1" value="1">
                    </div>
                    <div class="form-group">
                        <label for="precioUnitario">Precio Unitario:</label>
                        <input type="number" id="precioUnitario" step="0.01" readonly>
                    </div>
                    <div class="form-group">
                        <label for="costoManoObra">Costo Mano de Obra:</label>
                        <input type="number" id="costoManoObra" step="0.01" readonly>
                    </div>
                </div>
                <div class="detalle-row-2">
                    <div class="form-group">
                        <label for="itemSelect">Nombre/Descripción:</label>
                        <select id="itemSelect" onchange="actualizarPrecio()">
                            <option value="">Primero seleccione el tipo</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="observaciones">Observaciones:</label>
                        <input type="text" id="observaciones" placeholder="Observaciones adicionales">
                    </div>
                    <div class="checkbox-group">
                        <input type="checkbox" id="repuestoPedido" disabled>
                        <label for="repuestoPedido">Repuesto Pedido</label>
                    </div>
                </div>
                <div style="text-align: center; margin-top: 15px;">
                    <button type="button" class="btn btn-primary" onclick="agregarDetalle()">
                        Agregar Detalle
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="cancelarDetalle()">
                        Cancelar
                    </button>
                </div>
            </div>

            <div id="detallesLista" class="detalles-lista">
                <!-- Los detalles se agregarán aquí dinámicamente -->
            </div>
        </div>

        <div class="resumen-costos">
            <h3>Resumen de Costos</h3>
            <div class="costo-row">
                <span>Total Repuestos:</span>
                <span id="totalRepuestos">₡0,00</span>
            </div>
            <div class="costo-row">
                <span>Total Servicios:</span>
                <span id="totalServicios">₡0,00</span>
            </div>
            <div class="costo-row">
                <span>Total Mano de Obra:</span>
                <span id="totalManoObra">₡0,00</span>
            </div>
            <div class="costo-row total-final">
                <span>TOTAL GENERAL:</span>
                <span id="totalGeneral">₡0,00</span>
            </div>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> <%= esNuevo ? "Guardar" : "Actualizar" %> Orden
            </button>
            <a href="OrdenDeTrabajoServlet" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Cancelar
            </a>
        </div>
    </form>
</div>

<script>
    const repuestos = [
        <% if (repuestos != null) {
            for (int i = 0; i < repuestos.size(); i++) {
                Repuesto r = repuestos.get(i); %>
        {
            id: <%= r.getId() %>,
            nombre: "<%= r.getNombre() %>",
            precio: <%= r.getPrecio() %>,
            pedido: <%= r.isPedido() %>,
            cantidad: <%= r.getCantidadDisponible() %>
        }<%= i < repuestos.size() - 1 ? "," : "" %>
        <% } } %>
    ];

    const servicios = [
        <% if (servicios != null) {
            for (int i = 0; i < servicios.size(); i++) {
                Servicio s = servicios.get(i); %>
        {
            id: <%= s.getId() %>,
            nombre: "<%= s.getNombre() %>",
            precio: <%= s.getPrecio() %>,
            manoObra: <%= s.getCostoManoObra() %>
        }<%= i < servicios.size() - 1 ? "," : "" %>
        <% } } %>
    ];

    let detalleCounter = 0;

    function mostrarFormularioDetalle() {
        document.getElementById('formularioDetalle').style.display = 'block';
    }

    function cancelarDetalle() {
        document.getElementById('formularioDetalle').style.display = 'none';
        limpiarFormulario();
    }

    function actualizarOpciones() {
        const tipo = document.getElementById('tipoDetalle').value;
        const itemSelect = document.getElementById('itemSelect');
        const repuestoPedidoCheckbox = document.getElementById('repuestoPedido');

        itemSelect.innerHTML = '<option value="">Seleccione un item</option>';
        document.getElementById('precioUnitario').value = '';
        document.getElementById('costoManoObra').value = '';
        repuestoPedidoCheckbox.checked = false;

        if (tipo === 'repuesto') {
            repuestos.forEach(repuesto => {
                itemSelect.innerHTML += '<option value="' + repuesto.id + '" data-precio="' + repuesto.precio + '" data-pedido="' + repuesto.pedido + '">' + repuesto.nombre + '</option>';
            });
            repuestoPedidoCheckbox.disabled = false;
        } else if (tipo === 'servicio') {
            servicios.forEach(servicio => {
                itemSelect.innerHTML += '<option value="' + servicio.id + '" data-precio="' + servicio.precio + '" data-mano-obra="' + servicio.manoObra + '">' + servicio.nombre + '</option>';
            });
            repuestoPedidoCheckbox.disabled = true;
            repuestoPedidoCheckbox.checked = false;
        } else {
            repuestoPedidoCheckbox.disabled = true;
        }
    }

    function actualizarPrecio() {
        const itemSelect = document.getElementById('itemSelect');
        const selectedOption = itemSelect.options[itemSelect.selectedIndex];

        if (selectedOption.value) {
            document.getElementById('precioUnitario').value = selectedOption.getAttribute('data-precio') || 0;
            document.getElementById('costoManoObra').value = selectedOption.getAttribute('data-mano-obra') || 0;

            const repuestoPedidoCheckbox = document.getElementById('repuestoPedido');
            if (selectedOption.getAttribute('data-pedido') === 'true') {
                repuestoPedidoCheckbox.checked = true;
            } else {
                repuestoPedidoCheckbox.checked = false;
            }
        }
    }

    function agregarDetalle() {
        const tipo = document.getElementById('tipoDetalle').value;
        const itemSelect = document.getElementById('itemSelect');
        const cantidad = document.getElementById('cantidad').value;
        const precio = document.getElementById('precioUnitario').value;
        const manoObra = document.getElementById('costoManoObra').value;
        const observaciones = document.getElementById('observaciones').value;
        const repuestoPedido = document.getElementById('repuestoPedido').checked;

        if (!tipo || !itemSelect.value || !cantidad || !precio) {
            alert('Por favor complete todos los campos requeridos');
            return;
        }

        const nombreItem = itemSelect.options[itemSelect.selectedIndex].text;
        const tipoTexto = tipo === 'repuesto' ? 'Repuesto' : 'Servicio';
        const subtotal = parseFloat(cantidad) * parseFloat(precio);

        const detalleHTML =
            '<div class="detalle-item" id="detalle-' + detalleCounter + '">' +
            '<div class="detalle-content">' +
            '<h4>' + nombreItem + ' <span class="tipo-badge tipo-' + tipo + '">' + tipoTexto + '</span></h4>' +
            '<p><strong>Cantidad:</strong> ' + cantidad + ' | ' +
            '<strong>Precio:</strong> ₡' + parseFloat(precio).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',') + ' | ' +
            '<strong>Subtotal:</strong> ₡' + subtotal.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '</p>' +
            (parseFloat(manoObra) > 0 ? '<p><strong>Mano de Obra:</strong> ₡' + parseFloat(manoObra).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '</p>' : '') +
            (observaciones ? '<p><strong>Observaciones:</strong> ' + observaciones + '</p>' : '') +
            (repuestoPedido ? '<p><strong>Estado:</strong> Repuesto Pedido</p>' : '') +
            '</div>' +
            '<button type="button" class="btn-remove" onclick="eliminarDetalle(' + detalleCounter + ')">' +
            '<i class="fas fa-trash"></i>' +
            '</button>' +
            '<input type="hidden" name="tipoDetalle" value="' + tipo + '">' +
            '<input type="hidden" name="itemId" value="' + itemSelect.value + '">' +
            '<input type="hidden" name="cantidad" value="' + cantidad + '">' +
            '<input type="hidden" name="observacionesDetalle" value="' + observaciones + '">' +
            '</div>';

        document.getElementById('detallesLista').innerHTML += detalleHTML;
        detalleCounter++;

        cancelarDetalle();
        actualizarResumen();
    }

    function eliminarDetalle(index) {
        if (confirm('¿Está seguro de eliminar este detalle?')) {
            document.getElementById('detalle-' + index).remove();
            actualizarResumen();
        }
    }

    function limpiarFormulario() {
        document.getElementById('tipoDetalle').value = '';
        document.getElementById('itemSelect').innerHTML = '<option value="">Primero seleccione el tipo</option>';
        document.getElementById('cantidad').value = '1';
        document.getElementById('precioUnitario').value = '';
        document.getElementById('costoManoObra').value = '';
        document.getElementById('observaciones').value = '';
        document.getElementById('repuestoPedido').checked = false;
        document.getElementById('repuestoPedido').disabled = true;
    }

    function actualizarResumen() {
        let totalRepuestos = 0;
        let totalServicios = 0;
        let totalManoObra = 0;

        const detalles = document.querySelectorAll('.detalle-item');

        detalles.forEach(detalle => {
            const tipo = detalle.querySelector('input[name="tipoDetalle"]').value;
            const cantidad = parseFloat(detalle.querySelector('input[name="cantidad"]').value);

            const itemId = detalle.querySelector('input[name="itemId"]').value;

            if (tipo === 'repuesto') {
                const repuesto = repuestos.find(r => r.id == itemId);
                if (repuesto) {
                    totalRepuestos += cantidad * repuesto.precio;
                }
            } else if (tipo === 'servicio') {
                const servicio = servicios.find(s => s.id == itemId);
                if (servicio) {
                    totalServicios += cantidad * servicio.precio;
                    totalManoObra += servicio.manoObra;
                }
            }
        });

        document.getElementById('totalRepuestos').textContent = '₡' + totalRepuestos.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        document.getElementById('totalServicios').textContent = '₡' + totalServicios.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        document.getElementById('totalManoObra').textContent = '₡' + totalManoObra.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        document.getElementById('totalGeneral').textContent = '₡' + (totalRepuestos + totalServicios + totalManoObra).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }

    // Cargar detalles existentes si es edición
    <% if (!esNuevo && orden != null && orden.getDetalles() != null) {
        for (DetalleOrden detalle : orden.getDetalles()) { %>
    // Código para cargar detalles existentes aquí si es necesario
    <% } } %>
</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
        <%@ page import="cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*" %>
        <%@ page import="java.util.List" %>
        <%@ page import="java.time.LocalDate" %>
        <%
            OrdenDeTrabajo orden = (OrdenDeTrabajo) request.getAttribute("orden");
            List<Object[]> vehiculos = (List<Object[]>) request.getAttribute("vehiculos");
            List<Object[]> clientes = (List<Object[]>) request.getAttribute("clientes");
            boolean editando = (orden != null);
            String error = (String) request.getAttribute("error");
            Boolean puedeModificarDetalles = (Boolean) request.getAttribute("puedeModificarDetalles");
            boolean puedeEditar = puedeModificarDetalles == null || puedeModificarDetalles;
        %>
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title><%= editando ? "Editar Orden de Trabajo" : "Nueva Orden de Trabajo" %></title>
            <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
            <style>
                body { font-family: 'Roboto', sans-serif; background: #121212; color: #f1f1f1; padding: 20px; }
                .form-container { max-width: 800px; margin: 0 auto; background: #1e1e1e; padding: 30px; border-radius: 10px; box-shadow: 0 0 18px rgba(255,60,0,0.4); border: 1px solid #333; }
                h2 { text-align: center; color: #ff3c00; margin-bottom: 25px; }
                .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 25px; }
                .form-group { display: flex; flex-direction: column; }
                .form-group.full-width { grid-column: 1 / -1; }
                label { margin-bottom: 5px; color: #bbbbbb; font-weight: 500; }
                input, select, textarea { padding: 10px; border: 1px solid #444; border-radius: 5px; background: #2c2c2c; color: #fff; }
                textarea { resize: vertical; min-height: 80px; }
                .detalles-section { margin-top: 30px; }
                .detalles-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; }
                .detalles-header h3 { color: #ff3c00; margin: 0; }
                .add-detalle-btn { background: #27ae60; color: white; border: none; padding: 8px 15px; border-radius: 5px; cursor: pointer; }
                .add-detalle-btn:hover { background: #1e8449; }
                .add-detalle-btn:disabled { background: #666; cursor: not-allowed; opacity: 0.6; }
                .detalle-item {
                    background: #2a2a2a;
                    border: 1px solid #444;
                    border-radius: 8px;
                    padding: 30px 15px 15px 15px;
                    margin-bottom: 15px;
                    position: relative;
                }
                .detalle-item:hover {
                    border-color: #ff3c00;
                    box-shadow: 0 0 8px rgba(255, 60, 0, 0.3);
                }
                .detalle-grid { display: grid; grid-template-columns: 1fr 1fr 1fr 1fr; gap: 10px; }
                .detalle-grid .form-group { margin-bottom: 10px; }
                .remove-detalle-btn {
                    position: absolute;
                    top: 8px;
                    right: 8px;
                    background: #e74c3c;
                    color: white;
                    border: none;
                    width: 24px;
                    height: 24px;
                    border-radius: 50%;
                    cursor: pointer;
                    font-size: 14px;
                    font-weight: bold;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    transition: background-color 0.3s;
                }
                .remove-detalle-btn:hover {
                    background: #c0392b;
                    transform: scale(1.1);
                }
                .totales { background: #2a2a2a; padding: 15px; border-radius: 8px; margin-top: 20px; }
                .totales h4 { color: #ff3c00; margin: 0 0 10px 0; }
                .total-row { display: flex; justify-content: space-between; margin-bottom: 5px; }
                .total-final { font-weight: bold; font-size: 18px; color: #27ae60; border-top: 1px solid #444; padding-top: 10px; margin-top: 10px; }
                .submit-btn { background: #ff3c00; color: #fff; font-weight: 600; cursor: pointer; border: none; padding: 12px 30px; border-radius: 5px; margin-top: 20px; width: 100%; }
                .submit-btn:hover { background: #e03a00; }
                .back-link { display: block; text-align: center; margin-top: 20px; color: #ff3c00; text-decoration: none; }
                .back-link:hover { color: #ffa07a; }
                .error-message { color: #ff4d4d; background-color: #2a2a2a; border: 1px solid #ff4d4d; padding: 10px; margin-bottom: 20px; border-radius: 6px; text-align: center; }
                .alert-warning {
                    background-color: #f39c12;
                    color: white;
                    padding: 10px 15px;
                    border-radius: 5px;
                    margin: 10px 0;
                    display: flex;
                    align-items: center;
                    gap: 8px;
                }
                .alert-info {
                    background-color: #3498db;
                    color: white;
                    padding: 10px 15px;
                    border-radius: 5px;
                    margin: 10px 0;
                    display: flex;
                    align-items: center;
                    gap: 8px;
                }
            </style>
        </head>
        <body>
        <div class="form-container">
            <h2><%= editando ? "Editar" : "Nueva" %> Orden de Trabajo</h2>

            <form id="ordenForm" action="OrdenDeTrabajoServlet" method="post">
                <% if (error != null) { %>
                <div class="error-message"><%= error %></div>
                <% } %>

                <% if (editando && !puedeEditar) { %>
                <div class="alert-warning">
                    <i class="fas fa-exclamation-triangle"></i>
                    No se pueden agregar más repuestos/servicios. Estado actual: <%= orden.getEstado().getDescripcion() %>
                </div>
                <% } else if (editando && puedeEditar) { %>
                <div class="alert-info">
                    <i class="fas fa-info-circle"></i>
                    Puede agregar repuestos/servicios mientras el vehículo esté en el taller.
                </div>
                <% } %>

                <% if (editando) { %>
                <input type="hidden" name="idOrden" value="<%= orden.getIdOrden() %>"/>
                <% } %>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="numeroPlaca">Vehículo (Placa):</label>
                        <select name="numeroPlaca" id="numeroPlaca" required <%= editando ? "disabled" : "" %>>
                            <option value="">Seleccione un vehículo</option>
                            <% if (vehiculos != null) {
                                for (Object[] vehiculo : vehiculos) { %>
                            <option value="<%= vehiculo[0] %>"
                                    <%= editando && orden.getNumeroPlaca().equals(vehiculo[0]) ? "selected" : "" %>>
                                <%= vehiculo[0] %> - <%= vehiculo[1] %> <%= vehiculo[2] %> (<%= vehiculo[3] %>)
                            </option>
                            <% }
                            } %>
                        </select>
                        <% if (editando) { %>
                        <input type="hidden" name="numeroPlaca" value="<%= orden.getNumeroPlaca() %>"/>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="idCliente">Cliente:</label>
                        <select name="idCliente" id="idCliente" required <%= editando ? "disabled" : "" %>>
                            <option value="">Seleccione un cliente</option>
                            <% if (clientes != null) {
                                for (Object[] cliente : clientes) { %>
                            <option value="<%= cliente[0] %>"
                                    <%= editando && orden.getIdCliente() == (Integer)cliente[0] ? "selected" : "" %>>
                                <%= cliente[0] %> - <%= cliente[1] %> <%= cliente[2] %>
                            </option>
                            <% }
                            } %>
                        </select>
                        <% if (editando) { %>
                        <input type="hidden" name="idCliente" value="<%= orden.getIdCliente() %>"/>
                        <% } %>
                    </div>

                    <div class="form-group full-width">
                        <label for="descripcionSolicitud">Descripción de la Solicitud:</label>
                        <textarea name="descripcionSolicitud" id="descripcionSolicitud" required><%= editando ? orden.getDescripcionSolicitud() : "" %></textarea>
                    </div>

                    <div class="form-group full-width">
                        <label for="observacionesRecepcion">Observaciones de Recepción:</label>
                        <textarea name="observacionesRecepcion" id="observacionesRecepcion"><%= editando ? orden.getObservacionesRecepcion() : "" %></textarea>
                    </div>
                </div>

                <div class="detalles-section">
                    <div class="detalles-header">
                        <h3>Detalles de Trabajo</h3>
                        <button type="button" class="add-detalle-btn" onclick="addDetalle()"
                                <%= !puedeEditar ? "disabled" : "" %>>
                            <i class="fas fa-plus"></i> Agregar Detalle
                        </button>
                    </div>
                    <div id="detallesContainer">
                        <% if (editando && orden.getDetalles() != null) {
                            for (int i = 0; i < orden.getDetalles().size(); i++) {
                                DetalleOrden detalle = orden.getDetalles().get(i);
                        %>
                        <div class="detalle-item">
                            <% if (puedeEditar) { %>
                            <button type="button" class="remove-detalle-btn" onclick="removeDetalle(this)" title="Eliminar detalle">×</button>
                            <% } %>
                            <div class="detalle-grid">
                                <div class="form-group">
                                    <label>Tipo:</label>
                                    <select name="tipoDetalle" required <%= !puedeEditar ? "disabled" : "" %>>
                                        <option value="1" <%= detalle.getTipoDetalle().getId() == 1 ? "selected" : "" %>>Repuesto</option>
                                        <option value="2" <%= detalle.getTipoDetalle().getId() == 2 ? "selected" : "" %>>Servicio</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Cantidad:</label>
                                    <input type="number" name="cantidad" min="1" value="<%= detalle.getCantidad() %>"
                                           required onchange="calculateTotal()" <%= !puedeEditar ? "readonly" : "" %>/>
                                </div>
                                <div class="form-group">
                                    <label>Precio Unitario:</label>
                                    <input type="number" name="precio" min="0" step="0.01" value="<%= detalle.getPrecio() %>"
                                           required onchange="calculateTotal()" <%= !puedeEditar ? "readonly" : "" %>/>
                                </div>
                                <div class="form-group">
                                    <label>Costo Mano de Obra:</label>
                                    <input type="number" name="costoManoObra" min="0" step="0.01" value="<%= detalle.getCostoManoObra() %>"
                                           onchange="calculateTotal()" <%= !puedeEditar ? "readonly" : "" %>/>
                                </div>
                                <div class="form-group">
                                    <label>Nombre/Descripción:</label>
                                    <input type="text" name="nombreRepuesto" value="<%= detalle.getNombreRepuesto() %>"
                                           required <%= !puedeEditar ? "readonly" : "" %>/>
                                </div>
                                <div class="form-group">
                                    <label>Observaciones:</label>
                                    <input type="text" name="observaciones" value="<%= detalle.getObservaciones() %>"
                                           <%= !puedeEditar ? "readonly" : "" %>/>
                                </div>
                                <div class="form-group">
                                    <label style="display: flex; align-items: center; gap: 5px;">
                                        <input type="checkbox" name="repuestoPedido"
                                               <%= detalle.isRepuestoPedido() ? "checked" : "" %>
                                               <%= !puedeEditar ? "disabled" : "" %>/>
                                        Repuesto Pedido
                                    </label>
                                </div>
                            </div>
                        </div>
                        <% }
                        } %>
                    </div>
                </div>

                <div class="totales">
                    <h4>Resumen de Costos</h4>
                    <div class="total-row">
                        <span>Total Repuestos:</span>
                        <span id="totalRepuestos">₡0.00</span>
                    </div>
                    <div class="total-row">
                        <span>Total Servicios:</span>
                        <span id="totalServicios">₡0.00</span>
                    </div>
                    <div class="total-row">
                        <span>Total Mano de Obra:</span>
                        <span id="totalManoObra">₡0.00</span>
                    </div>
                    <div class="total-row total-final">
                        <span>TOTAL GENERAL:</span>
                        <span id="totalGeneral">₡0.00</span>
                    </div>
                </div>

                <input type="submit" value="Guardar Orden" class="submit-btn"/>
            </form>

            <a class="back-link" href="OrdenDeTrabajoServlet">← Volver a la lista</a>
        </div>

        <script>
        function validarEstadoOrden() {
            <% if (editando) { %>
            const puedeModificar = <%= puedeEditar %>;
            if (!puedeModificar) {
                alert('No se pueden agregar repuestos/servicios. La orden está en estado: <%= orden.getEstado().getDescripcion() %>');
                return false;
            }
            <% } %>
            return true;
        }

        function addDetalle() {
            if (!validarEstadoOrden()) {
                return;
            }

            const container = document.getElementById('detallesContainer');
            const detalleDiv = document.createElement('div');
            detalleDiv.className = 'detalle-item';
            detalleDiv.innerHTML = `
                <button type="button" class="remove-detalle-btn" onclick="removeDetalle(this)" title="Eliminar detalle">×</button>
                <div class="detalle-grid">
                    <div class="form-group">
                        <label>Tipo:</label>
                        <select name="tipoDetalle" required>
                            <option value="">Seleccione</option>
                            <option value="1">Repuesto</option>
                            <option value="2">Servicio</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Cantidad:</label>
                        <input type="number" name="cantidad" min="1" value="1" required onchange="calculateTotal()"/>
                    </div>
                    <div class="form-group">
                        <label>Precio Unitario:</label>
                        <input type="number" name="precio" min="0" step="0.01" value="0" required onchange="calculateTotal()"/>
                    </div>
                    <div class="form-group">
                        <label>Costo Mano de Obra:</label>
                        <input type="number" name="costoManoObra" min="0" step="0.01" value="0" onchange="calculateTotal()"/>
                    </div>
                    <div class="form-group">
                        <label>Nombre/Descripción:</label>
                        <input type="text" name="nombreRepuesto" required/>
                    </div>
                    <div class="form-group">
                        <label>Observaciones:</label>
                        <input type="text" name="observaciones"/>
                    </div>
                    <div class="form-group">
                        <label style="display: flex; align-items: center; gap: 5px;">
                            <input type="checkbox" name="repuestoPedido"/>
                            Repuesto Pedido
                        </label>
                    </div>
                </div>
            `;
            container.appendChild(detalleDiv);
            calculateTotal();
        }

        function removeDetalle(button) {
            if (!validarEstadoOrden()) {
                return;
            }
            button.closest('.detalle-item').remove();
            calculateTotal();
        }

        function calculateTotal() {
            let totalRepuestos = 0;
            let totalServicios = 0;
            let totalManoObra = 0;

            const detalles = document.querySelectorAll('.detalle-item');
            detalles.forEach(detalle => {
                const tipo = detalle.querySelector('select[name="tipoDetalle"]').value;
                const cantidad = parseFloat(detalle.querySelector('input[name="cantidad"]').value) || 0;
                const precio = parseFloat(detalle.querySelector('input[name="precio"]').value) || 0;
                const costoManoObra = parseFloat(detalle.querySelector('input[name="costoManoObra"]').value) || 0;

                const subtotal = cantidad * precio;
                if (tipo === '1') { // Repuesto
                    totalRepuestos += subtotal;
                } else if (tipo === '2') { // Servicio
                    totalServicios += subtotal;
                }
                totalManoObra += costoManoObra;
            });

            document.getElementById('totalRepuestos').textContent = '₡' + totalRepuestos.toLocaleString('es-CR', {minimumFractionDigits: 2});
            document.getElementById('totalServicios').textContent = '₡' + totalServicios.toLocaleString('es-CR', {minimumFractionDigits: 2});
            document.getElementById('totalManoObra').textContent = '₡' + totalManoObra.toLocaleString('es-CR', {minimumFractionDigits: 2});
            document.getElementById('totalGeneral').textContent = '₡' + (totalRepuestos + totalServicios + totalManoObra).toLocaleString('es-CR', {minimumFractionDigits: 2});
        }

        // Agregar un detalle inicial si es una nueva orden
        <% if (!editando) { %>
        addDetalle();
        <% } %>

        // Calcular total inicial
        calculateTotal();

        // Validación de formulario
        document.getElementById('ordenForm').onsubmit = function(e) {
            const detalles = document.querySelectorAll('.detalle-item');
            if (detalles.length === 0) {
                alert('Debe agregar al menos un detalle a la orden de trabajo.');
                e.preventDefault();
                return false;
            }
            return true;
        };
        </script>
        </body>
        </html>
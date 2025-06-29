<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Inicio - MovaSystem</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #121212;
            margin: 0;
            padding: 0;
            color: #f1f1f1;
        }

        .navbar {
            background-color: #1e1e1e;
            padding: 30px 20px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(255, 60, 0, 0.2);
        }

        .navbar h1 {
            color: #ff3c00;
            margin: 0;
            font-size: 36px;
            font-weight: 700;
            letter-spacing: 1px;
            text-transform: uppercase;
        }

        .dashboard {
            max-width: 1100px;
            margin: 40px auto;
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 30px;
        }

        .card {
            background-color: #1f1f1f;
            padding: 30px 20px;
            border-radius: 10px;
            width: 250px;
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: 1px solid #333;
            box-shadow: 0 0 10px rgba(255, 60, 0, 0.15);
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 0 20px rgba(255, 60, 0, 0.4);
        }

        .card i {
            font-size: 40px;
            color: #ff3c00;
            margin-bottom: 15px;
        }

        .card h3 {
            color: #ff3c00;
            margin-bottom: 10px;
        }

        .card p {
            margin-bottom: 15px;
        }

        .card a {
            display: inline-block;
            padding: 10px 15px;
            background-color: #ff3c00;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .card a:hover {
            background-color: #e03a00;
        }

        .footer {
            margin-top: 60px;
            text-align: center;
            font-size: 13px;
            color: #777;
        }
    </style>
</head>
<body>

    <div class="navbar">
        <h1>MovaSystem</h1>
    </div>

    <div class="dashboard">

        <div class="card">
            <i class="fas fa-user"></i>
            <h3>Clientes</h3>
            <p>Ver y administrar clientes registrados.</p>
            <a href="ClienteServlet">Ir a Clientes</a>
        </div>

        <div class="card">
            <i class="fas fa-car-side"></i>
            <h3>Vehículos</h3>
            <p>Registrar y administrar vehículos de clientes.</p>
            <a href="VehiculoServlet">Ir a Vehículos</a>
        </div>

        <div class="card">
            <i class="fas fa-file-invoice"></i>
            <h3>Órdenes de Trabajo</h3>
            <p>Gestionar órdenes de reparación de vehículos.</p>
            <a href="OrdenServlet">Ver Órdenes</a>
        </div>

        <div class="card">
            <i class="fas fa-clipboard-check"></i>
            <h3>Recepción</h3>
            <p>Registrar detalles al recibir un vehículo.</p>
            <a href="RecepcionServlet">Recepcionar Vehículo</a>
        </div>

        <div class="card">
            <i class="fas fa-tools"></i>
            <h3>Servicios</h3>
            <p>Agregar repuestos y trabajos a la orden.</p>
            <a href="ServiciosServlet">Gestionar Servicios</a>
        </div>

        <div class="card">
            <i class="fas fa-chart-bar"></i>
            <h3>Reportes</h3>
            <p>Estadísticas e informes generales.</p>
            <a href="ReportesServlet">Ver Reportes</a>
        </div>

    </div>

    <div class="footer">
        © 2025 MovaSystem - Todos los derechos reservados.
    </div>

</body>
</html>

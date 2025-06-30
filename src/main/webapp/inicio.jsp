<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>MovaSystem - Inicio</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;600;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link rel="icon" href="assets/favicon.png" type="image/png">
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #121212;
            font-family: 'Roboto', sans-serif;
            color: #f1f1f1;
        }

        header {
            background-color: #1e1e1e;
            padding: 15px 30px;
            display: flex;
            justify-content: center;
            align-items: center;
            box-shadow: 0 4px 12px rgba(255, 60, 0, 0.2);
        }

        header .logo {
            font-size: 28px;
            font-weight: 700;
            color: #ff3c00;
            letter-spacing: 1px;
        }

        .welcome {
            text-align: center;
            padding: 30px 20px 10px;
        }

        .welcome h2 {
            font-size: 28px;
            color: #ff3c00;
            margin-bottom: 10px;
        }

        .dashboard {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            max-width: 1100px;
            margin: auto;
            gap: 30px;
            padding-bottom: 50px;
        }

        .card {
            background-color: #1f1f1f;
            border-radius: 10px;
            width: 240px;
            height: 230px;
            box-shadow: 0 0 12px rgba(255, 60, 0, 0.2);
            text-align: center;
            padding: 30px 20px;
            transition: transform 0.3s, box-shadow 0.3s;
            border: 1px solid #333;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .card:hover {
            transform: translateY(-6px);
            box-shadow: 0 0 20px rgba(255, 60, 0, 0.4);
        }

        .card i {
            font-size: 40px;
            color: #ff3c00;
            margin-bottom: 15px;
        }

        .card h3 {
            margin: 0 0 10px;
            color: #ff3c00;
        }

        .card p {
            font-size: 14px;
            margin-bottom: 20px;
            color: #ccc;
        }

        .card a {
            padding: 10px 20px;
            background-color: #ff3c00;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
        }

        .card a:hover {
            background-color: #e03a00;
        }

        footer {
            text-align: center;
            padding: 30px;
            font-size: 13px;
            color: #777;
            border-top: 1px solid #333;
        }

        @media (max-width: 600px) {
            .card {
                width: 90%;
                height: auto;
                padding: 20px 15px;
            }
        }
    </style>
</head>
<body>

<header>
    <div class="logo"><i class="fas fa-tools"></i> MovaSystem</div>
</header>

<div class="welcome">
    <h2>Bienvenido a tu panel de gestión</h2>
    <p>Administra clientes, vehículos, órdenes y más.</p>
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
        <a href="RecepcionServlet">Recepcionar</a>
    </div>

    <div class="card">
        <i class="fas fa-tools"></i>
        <h3>Servicios</h3>
        <p>Agregar servicios y mano de obra a la orden.</p>
        <a href="ServicioServlet">Gestionar Servicios</a>
    </div>

    <div class="card">
        <i class="fas fa-cogs"></i>
        <h3>Repuestos</h3>
        <p>Gestionar stock y pedidos de repuestos.</p>
        <a href="RepuestoServlet">Ver Repuestos</a>
    </div>

    <div class="card">
        <i class="fas fa-chart-bar"></i>
        <h3>Reportes</h3>
        <p>Estadísticas e informes generales.</p>
        <a href="ReportesServlet">Ver Reportes</a>
    </div>
</div>

<footer>
    © 2025 MovaSystem - Todos los derechos reservados.
</footer>

</body>
</html>

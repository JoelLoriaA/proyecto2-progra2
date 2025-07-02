package cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.*;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrdenDeTrabajoDAOTest {

    private OrdenDeTrabajoDAO dao;
    private static final String TEST_FILE_PATH = "test_data/ordenes_test.xml";

    @BeforeEach
    void setUp() throws JDOMException, IOException {
        // Crear directorio de prueba si no existe
        File testDir = new File("test_data");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }

        // Eliminar archivo de prueba si existe
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }

        dao = new OrdenDeTrabajoDAO(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        // Limpiar archivo de prueba
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    private OrdenDeTrabajo crearOrdenDePrueba(int id, String descripcion, LocalDate fecha) {
        Estado estado = new Estado(1, "DIAGNÓSTICO");
        List<DetalleOrden> detalles = new ArrayList<>();

        return new OrdenDeTrabajo(id, descripcion, fecha, estado, "ABC123", 12345678,
                                 "Observaciones de prueba", detalles);
    }

    @Test
    @DisplayName("Crear (INSERT) - Debe insertar una nueva orden de trabajo")
    void testInsertOrdenDeTrabajo() throws IOException {
        // Arrange
        OrdenDeTrabajo orden = crearOrdenDePrueba(1, "Reparación motor", LocalDate.now());

        // Act
        dao.insertOrUpdate(orden);

        // Assert
        List<OrdenDeTrabajo> ordenes = dao.findAll();
        assertEquals(1, ordenes.size());
        assertEquals("Reparación motor", ordenes.get(0).getDescripcionSolicitud());
    }

    @Test
    @DisplayName("Leer (SELECT) - Debe obtener todas las órdenes ordenadas")
    void testFindAllOrdenes() throws IOException {
        // Arrange
        OrdenDeTrabajo orden1 = crearOrdenDePrueba(3, "Tercera orden", LocalDate.now());
        OrdenDeTrabajo orden2 = crearOrdenDePrueba(1, "Primera orden", LocalDate.now().plusDays(1));
        OrdenDeTrabajo orden3 = crearOrdenDePrueba(2, "Segunda orden", LocalDate.now().plusDays(2));

        // Act
        dao.insertOrUpdate(orden1);
        dao.insertOrUpdate(orden2);
        dao.insertOrUpdate(orden3);

        // Assert
        List<OrdenDeTrabajo> ordenes = dao.findAll();
        assertEquals(3, ordenes.size());
        assertEquals(1, ordenes.get(0).getIdOrden());
        assertEquals(2, ordenes.get(1).getIdOrden());
        assertEquals(3, ordenes.get(2).getIdOrden());
    }

    @Test
    @DisplayName("Actualizar (UPDATE) - Debe modificar una orden existente")
    void testUpdateOrdenDeTrabajo() throws IOException {
        // Arrange
        OrdenDeTrabajo ordenOriginal = crearOrdenDePrueba(1, "Descripción original", LocalDate.now());
        dao.insertOrUpdate(ordenOriginal);

        // Act
        OrdenDeTrabajo ordenModificada = crearOrdenDePrueba(1, "Descripción modificada", LocalDate.now());
        dao.insertOrUpdate(ordenModificada);

        // Assert
        List<OrdenDeTrabajo> ordenes = dao.findAll();
        assertEquals(1, ordenes.size());
        assertEquals("Descripción modificada", ordenes.get(0).getDescripcionSolicitud());
    }

    @Test
    @DisplayName("Eliminar (DELETE) - Debe eliminar una orden por ID")
    void testDeleteOrdenDeTrabajo() throws IOException {
        // Arrange
        OrdenDeTrabajo orden1 = crearOrdenDePrueba(1, "Primera orden", LocalDate.now());
        OrdenDeTrabajo orden2 = crearOrdenDePrueba(2, "Segunda orden", LocalDate.now());
        dao.insertOrUpdate(orden1);
        dao.insertOrUpdate(orden2);

        // Act
        dao.delete(1);

        // Assert
        List<OrdenDeTrabajo> ordenes = dao.findAll();
        assertEquals(1, ordenes.size());
        assertEquals(2, ordenes.get(0).getIdOrden());
    }

    @Test
    @DisplayName("Búsqueda Binaria - Debe encontrar orden por ID usando búsqueda binaria")
    void testBusquedaBinariaEncontrarOrden() throws IOException {
        // Arrange
        OrdenDeTrabajo orden1 = crearOrdenDePrueba(1, "Primera orden", LocalDate.now());
        OrdenDeTrabajo orden2 = crearOrdenDePrueba(3, "Tercera orden", LocalDate.now());
        OrdenDeTrabajo orden3 = crearOrdenDePrueba(5, "Quinta orden", LocalDate.now());
        dao.insertOrUpdate(orden1);
        dao.insertOrUpdate(orden2);
        dao.insertOrUpdate(orden3);

        // Act
        Optional<OrdenDeTrabajo> resultado = dao.findByKey(3);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Tercera orden", resultado.get().getDescripcionSolicitud());
    }

    @Test
    @DisplayName("Búsqueda Binaria - No debe encontrar orden inexistente")
    void testBusquedaBinariaOrdenInexistente() throws IOException {
        // Arrange
        OrdenDeTrabajo orden = crearOrdenDePrueba(1, "Primera orden", LocalDate.now());
        dao.insertOrUpdate(orden);

        // Act
        Optional<OrdenDeTrabajo> resultado = dao.findByKey(999);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("findById - Debe encontrar orden por ID")
    void testFindById() throws IOException {
        // Arrange
        OrdenDeTrabajo orden = crearOrdenDePrueba(1, "Orden de prueba", LocalDate.now());
        dao.insertOrUpdate(orden);

        // Act
        OrdenDeTrabajo resultado = dao.findById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Orden de prueba", resultado.getDescripcionSolicitud());
        assertEquals(1, resultado.getIdOrden());
    }

    @Test
    @DisplayName("findById - Debe retornar null para ID inexistente")
    void testFindByIdInexistente() throws IOException {
        // Arrange
        OrdenDeTrabajo orden = crearOrdenDePrueba(1, "Orden existente", LocalDate.now());
        dao.insertOrUpdate(orden);

        // Act
        OrdenDeTrabajo resultado = dao.findById(999);

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("Persistencia - Los datos deben persistir entre instancias del DAO")
    void testPersistenciaEntreSesiones() throws IOException, JDOMException {
        // Arrange
        OrdenDeTrabajo orden = crearOrdenDePrueba(1, "Orden persistente", LocalDate.now());
        dao.insertOrUpdate(orden);

        // Act - Crear nueva instancia del DAO
        OrdenDeTrabajoDAO nuevoDao = new OrdenDeTrabajoDAO(TEST_FILE_PATH);
        List<OrdenDeTrabajo> ordenes = nuevoDao.findAll();

        // Assert
        assertEquals(1, ordenes.size());
        assertEquals("Orden persistente", ordenes.get(0).getDescripcionSolicitud());
    }

    @Test
    @DisplayName("Ordenamiento - Las órdenes deben mantenerse ordenadas después de múltiples operaciones")
    void testOrdenamientoMultiplesOperaciones() throws IOException {
        // Arrange y Act
        dao.insertOrUpdate(crearOrdenDePrueba(5, "Quinta", LocalDate.now()));
        dao.insertOrUpdate(crearOrdenDePrueba(2, "Segunda", LocalDate.now()));
        dao.insertOrUpdate(crearOrdenDePrueba(8, "Octava", LocalDate.now()));
        dao.insertOrUpdate(crearOrdenDePrueba(1, "Primera", LocalDate.now()));
        dao.delete(2);
        dao.insertOrUpdate(crearOrdenDePrueba(3, "Tercera", LocalDate.now()));

        // Assert
        List<OrdenDeTrabajo> ordenes = dao.findAll();
        assertEquals(4, ordenes.size());
        assertEquals(1, ordenes.get(0).getIdOrden());
        assertEquals(3, ordenes.get(1).getIdOrden());
        assertEquals(5, ordenes.get(2).getIdOrden());
        assertEquals(8, ordenes.get(3).getIdOrden());
    }

    @Test
    @DisplayName("Validar estructura completa - Debe preservar todos los campos y detalles")
    void testEstructuraCompletaOrden() throws IOException {
        // Arrange
        Estado estado = new Estado(2, "COMPLETADO");
        List<DetalleOrden> detalles = new ArrayList<>();

        TipoDetalle tipoDetalle1 = new TipoDetalle(1, "REPUESTO");
        Estado estadoDetalle1 = new Estado(1, "COMPLETADO");
        DetalleOrden detalle1 = new DetalleOrden(1, 2, "Filtros nuevos", 1,
                tipoDetalle1, estadoDetalle1, 25000.0,
                "Filtro de aire", true, 8000.0);

        TipoDetalle tipoDetalle2 = new TipoDetalle(2, "SERVICIO");
        Estado estadoDetalle2 = new Estado(2, "EN_PROCESO");
        DetalleOrden detalle2 = new DetalleOrden(2, 1, "Cambio de aceite", 1,
                tipoDetalle2, estadoDetalle2, 35000.0,
                "Aceite sintético", false, 15000.0);

        detalles.add(detalle1);
        detalles.add(detalle2);

        OrdenDeTrabajo orden = new OrdenDeTrabajo(1, "Mantenimiento completo",
                LocalDate.of(2024, 1, 15), estado, "ABC123", 12345678,
                "Vehículo en buen estado general", detalles);

        // Act
        dao.insertOrUpdate(orden);
        OrdenDeTrabajo ordenRecuperada = dao.findById(1);

        // Assert
        assertNotNull(ordenRecuperada);
        assertEquals(1, ordenRecuperada.getIdOrden());
        assertEquals("Mantenimiento completo", ordenRecuperada.getDescripcionSolicitud());
        assertEquals(LocalDate.of(2024, 1, 15), ordenRecuperada.getFechaIngreso());
        assertEquals("COMPLETADO", ordenRecuperada.getEstado().getDescripcion());
        assertEquals("ABC123", ordenRecuperada.getNumeroPlaca());
        assertEquals(12345678, ordenRecuperada.getIdCliente());
        assertEquals("Vehículo en buen estado general", ordenRecuperada.getObservacionesRecepcion());

        // Verificar detalles
        assertNotNull(ordenRecuperada.getDetalles());
        assertEquals(2, ordenRecuperada.getDetalles().size());

        DetalleOrden detalleRecuperado1 = ordenRecuperada.getDetalles().get(0);
        assertEquals(2, detalleRecuperado1.getCantidad());
        assertEquals("Filtros nuevos", detalleRecuperado1.getObservaciones());
        assertEquals(25000.0, detalleRecuperado1.getPrecio());
        assertEquals("Filtro de aire", detalleRecuperado1.getNombreRepuesto());
        assertTrue(detalleRecuperado1.isRepuestoPedido());
        assertEquals(8000.0, detalleRecuperado1.getCostoManoObra());

        DetalleOrden detalleRecuperado2 = ordenRecuperada.getDetalles().get(1);
        assertEquals(1, detalleRecuperado2.getCantidad());
        assertEquals("Cambio de aceite", detalleRecuperado2.getObservaciones());
        assertEquals(35000.0, detalleRecuperado2.getPrecio());
        assertEquals("Aceite sintético", detalleRecuperado2.getNombreRepuesto());
        assertFalse(detalleRecuperado2.isRepuestoPedido());
        assertEquals(15000.0, detalleRecuperado2.getCostoManoObra());
    }
}
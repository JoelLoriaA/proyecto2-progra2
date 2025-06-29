package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Vehiculo;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VehiculoDaoTest {

    private VehiculoDao vehiculoDao;
    private final String testFilePath = "xml_data/vehiculos_test.xml";

    @BeforeEach
    void setUp() throws JDOMException, IOException {
        // Eliminar archivo de test si existe
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }

        vehiculoDao = new VehiculoDao();
    }

    @AfterEach
    void tearDown() {
        // Limpiar archivo de test después de cada prueba
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testInsertVehiculo() throws IOException {
        // Arrange
        Vehiculo vehiculo = new Vehiculo("ABC-123", "Rojo", "Toyota", "Sedán",
                                       2020, "1HGBH41JXMN109186", 1.8, "Juan Pérez");

        // Act
        vehiculoDao.insertOrUpdate(vehiculo);

        // Assert
        Optional<Vehiculo> found = vehiculoDao.findByKey("ABC-123");
        assertTrue(found.isPresent());
        assertEquals("ABC-123", found.get().getNumeroPlaca());
        assertEquals("Toyota", found.get().getMarca());
    }

    @Test
    void testUpdateVehiculo() throws IOException {
        // Arrange
        Vehiculo vehiculo = new Vehiculo("DEF-456", "Azul", "Honda", "SUV",
                                       2019, "2HGBH41JXMN109187", 2.0, "María López");
        vehiculoDao.insertOrUpdate(vehiculo);

        // Act - Actualizar el vehículo
        vehiculo.setColor("Verde");
        vehiculo.setDuenio("Carlos Rodríguez");
        vehiculoDao.insertOrUpdate(vehiculo);

        // Assert
        Optional<Vehiculo> updated = vehiculoDao.findByKey("DEF-456");
        assertTrue(updated.isPresent());
        assertEquals("Verde", updated.get().getColor());
        assertEquals("Carlos Rodríguez", updated.get().getDuenio());
    }

    @Test
    void testFindByKeyExistente() throws IOException {
        // Arrange
        Vehiculo vehiculo = new Vehiculo("GHI-789", "Negro", "Nissan", "Pickup",
                                       2021, "3HGBH41JXMN109188", 2.5, "Ana García");
        vehiculoDao.insertOrUpdate(vehiculo);

        // Act
        Optional<Vehiculo> found = vehiculoDao.findByKey("GHI-789");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("GHI-789", found.get().getNumeroPlaca());
        assertEquals("Nissan", found.get().getMarca());
    }

    @Test
    void testFindByKeyNoExistente() {
        // Act
        Optional<Vehiculo> found = vehiculoDao.findByKey("XYZ-999");

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAllVehiculos() throws IOException {
        // Arrange
        Vehiculo vehiculo1 = new Vehiculo("AAA-111", "Blanco", "Ford", "Hatchback",
                                        2018, "4HGBH41JXMN109189", 1.6, "Pedro Sánchez");
        Vehiculo vehiculo2 = new Vehiculo("BBB-222", "Gris", "Chevrolet", "Sedán",
                                        2022, "5HGBH41JXMN109190", 2.2, "Laura Jiménez");

        vehiculoDao.insertOrUpdate(vehiculo1);
        vehiculoDao.insertOrUpdate(vehiculo2);

        // Act
        List<Vehiculo> vehiculos = vehiculoDao.findAll();

        // Assert
        assertEquals(2, vehiculos.size());
        // Verificar que están ordenados por número de placa
        assertEquals("AAA-111", vehiculos.get(0).getNumeroPlaca());
        assertEquals("BBB-222", vehiculos.get(1).getNumeroPlaca());
    }

    @Test
    void testBusquedaBinariaConMultiplesVehiculos() throws IOException {
        // Arrange - Insertar varios vehículos
        String[] placas = {"CCC-333", "AAA-111", "EEE-555", "BBB-222", "DDD-444"};
        String[] marcas = {"Toyota", "Honda", "Nissan", "Ford", "Chevrolet"};

        for (int i = 0; i < placas.length; i++) {
            Vehiculo vehiculo = new Vehiculo(placas[i], "Color" + i, marcas[i], "Estilo" + i,
                                           2020 + i, "VIN" + i, 1.5 + i, "Dueño" + i);
            vehiculoDao.insertOrUpdate(vehiculo);
        }

        // Act & Assert - Probar búsqueda binaria
        Optional<Vehiculo> found = vehiculoDao.findByKey("CCC-333");
        assertTrue(found.isPresent());
        assertEquals("Toyota", found.get().getMarca());

        found = vehiculoDao.findByKey("AAA-111");
        assertTrue(found.isPresent());
        assertEquals("Honda", found.get().getMarca());

        found = vehiculoDao.findByKey("ZZZ-999");
        assertFalse(found.isPresent());
    }

    @Test
    void testOrdenamientoAlfabetico() throws IOException {
        // Arrange
        String[] placasDesordenadas = {"ZZZ-999", "AAA-111", "MMM-555", "BBB-222"};

        for (String placa : placasDesordenadas) {
            Vehiculo vehiculo = new Vehiculo(placa, "Color", "Marca", "Estilo",
                                           2020, "VIN", 1.8, "Dueño");
            vehiculoDao.insertOrUpdate(vehiculo);
        }

        // Act
        List<Vehiculo> vehiculos = vehiculoDao.findAll();

        // Assert - Verificar orden alfabético
        assertEquals("AAA-111", vehiculos.get(0).getNumeroPlaca());
        assertEquals("BBB-222", vehiculos.get(1).getNumeroPlaca());
        assertEquals("MMM-555", vehiculos.get(2).getNumeroPlaca());
        assertEquals("ZZZ-999", vehiculos.get(3).getNumeroPlaca());
    }
}
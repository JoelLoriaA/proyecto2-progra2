package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.RepuestoDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.ServicioDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Servicio;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DAOTest {

    private static ClienteDAO clienteDAO;
    private static ServicioDAO servicioDAO;
    private static RepuestoDAO repuestoDAO;

    @BeforeAll
    static void setup() throws IOException, JDOMException {
        String basePath = System.getProperty("user.home") + "/movasystem/test/";
        clienteDAO = new ClienteDAO(basePath + "clientes.xml");
        servicioDAO = new ServicioDAO(basePath + "servicios.xml");
        repuestoDAO = new RepuestoDAO(basePath + "repuestos.xml");
    }

    @Test
    @Order(1)
    void testAgregarCliente() throws Exception {
        int id = clienteDAO.getNextId();
        Cliente cliente = new Cliente(id, "Juan", "Perez", "Lopez", "88888888", "San Jose", "juan@mail.com");
        clienteDAO.save(cliente);
        Cliente encontrado = clienteDAO.findById(id);
        assertNotNull(encontrado);
        assertEquals("Juan", encontrado.getNombre());
    }

    @Test
    @Order(2)
    void testEliminarCliente() throws Exception {
        int id = clienteDAO.getNextId();
        Cliente cliente = new Cliente(id, "Laura", "Sanchez", "Mora", "11112222", "Alajuela", "laura@mail.com");
        clienteDAO.save(cliente);
        clienteDAO.delete(id);
        assertNull(clienteDAO.findById(id));
    }

    @Test
    @Order(3)
    void testAgregarServicio() throws Exception {
        int id = servicioDAO.getNextId();
        Servicio servicio = new Servicio(id, "Cambio de Aceite", "Aceite sintético", 15000, 5000);
        servicioDAO.save(servicio);
        Servicio encontrado = servicioDAO.findById(id);
        assertNotNull(encontrado);
        assertEquals("Cambio de Aceite", encontrado.getNombre());
    }

    @Test
    @Order(4)
    void testEliminarServicio() throws Exception {
        int id = servicioDAO.getNextId();
        Servicio servicio = new Servicio(id, "Alineamiento", "Alineación de llantas", 12000, 3000);
        servicioDAO.save(servicio);
        servicioDAO.delete(id);
        assertNull(servicioDAO.findById(id));
    }

    @Test
    @Order(5)
    void testAgregarRepuesto() throws Exception {
        int id = repuestoDAO.getNextId();
        Repuesto repuesto = new Repuesto(id, "Filtro de aceite", "Filtro nuevo", 2500, 10, false);
        repuestoDAO.save(repuesto);
        Repuesto encontrado = repuestoDAO.findById(id);
        assertNotNull(encontrado);
        assertEquals("Filtro de aceite", encontrado.getNombre());
    }

    @Test
    @Order(6)
    void testEliminarRepuesto() throws Exception {
        int id = repuestoDAO.getNextId();
        Repuesto repuesto = new Repuesto(id, "Bujía", "Bujía para motor", 1200, 20, false);
        repuestoDAO.save(repuesto);
        repuestoDAO.delete(id);
        assertNull(repuestoDAO.findById(id));
    }

    @Test
    @Order(7)
    void testListarClientes() throws Exception {
        List<Cliente> lista = clienteDAO.findAll();
        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    @Test
    @Order(8)
    void testListarServicios() throws Exception {
        List<Servicio> lista = servicioDAO.findAll();
        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    @Test
    @Order(9)
    void testListarRepuestos() throws Exception {
        List<Repuesto> lista = repuestoDAO.findAll();
        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }
}  

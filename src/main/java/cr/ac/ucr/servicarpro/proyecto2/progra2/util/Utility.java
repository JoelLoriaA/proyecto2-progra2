
package cr.ac.ucr.servicarpro.proyecto2.progra2.util;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.ClienteMapper;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.GenericXmlRepository;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.jdom2.JDOMException;

/**
 *
 * @author Emanuel Araya
 */
public class Utility {

    public static void main(String[] args) {
        try {
            // Crear repositorio de Cliente con JDOM
            GenericXmlRepository<Cliente, Integer> clienteRepo =
                    new GenericXmlRepository<>(
                            "xml_data/clientes_jdom.xml", // ubicación del archivo XML
                            "clientes",                   // nombre de la raíz
                            "cliente",                    // nombre de la entidad
                            Cliente::getId,               // extractor de clave
                            Comparator.comparing(Cliente::getId), // criterio de ordenamiento
                            new ClienteMapper()           // mapper de cliente
                    );

            // Insertar 5 clientes
            Cliente c1 = new Cliente(clienteRepo.getNextId(), "Ana", "Sánchez", "Gómez", "8888-1111", "Cartago", "ana@gmail.com");
            clienteRepo.insertOrUpdate(c1);

            Cliente c2 = new Cliente(clienteRepo.getNextId(), "Luis", "Rodríguez", "Pérez", "8888-2222", "San José", "luis@gmail.com");
            clienteRepo.insertOrUpdate(c2);

            Cliente c3 = new Cliente(clienteRepo.getNextId(), "Carla", "Jiménez", "Solís", "8888-3333", "Heredia", "carla@gmail.com");
            clienteRepo.insertOrUpdate(c3);

            Cliente c4 = new Cliente(clienteRepo.getNextId(), "José", "Hernández", "Mora", "8888-4444", "Alajuela", "jose@gmail.com");
            clienteRepo.insertOrUpdate(c4);

            Cliente c5 = new Cliente(clienteRepo.getNextId(), "Sofía", "Vargas", "Castro", "8888-5555", "Puntarenas", "sofia@gmail.com");
            clienteRepo.insertOrUpdate(c5);

            // Mostrar clientes en consola para validar orden y persistencia
            List<Cliente> clientes = clienteRepo.findAll();
            System.out.println("=== CLIENTES EN XML ORDENADOS POR NOMBRE ===");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }

            System.out.println("\n Archivo 'clientes_jdom.xml' generado y actualizado correctamente en 'xml_data/'.");

        } catch (JDOMException | IOException e) {
            System.err.println(" Error en persistencia XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

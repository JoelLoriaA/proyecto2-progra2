package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Comparator;

/**
 * DAO para acceder a los clientes almacenados en archivo XML.
 */
public class ClienteDAO extends GenericXmlRepository<Cliente, Integer> {

    public ClienteDAO() throws JDOMException, IOException {
        super(
            "xml_data/clientes.xml",            // Archivo XML
            "clientes",                         // Tag raíz
            "cliente",                          // Tag de entidad
            Cliente::getId,                     // Clave primaria
            Comparator.comparing(Cliente::getPrimerApellido).thenComparing(Cliente::getNombre), // Orden por apellido y nombre
            new ClienteMapper()                 // Mapper JDOM
        );
    }
}

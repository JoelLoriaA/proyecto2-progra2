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
            System.getProperty("user.home") + "/movasystem/clientes.xml",
            "clientes",
            "cliente",
            Cliente::getId,
            Comparator.comparing(Cliente::getPrimerApellido).thenComparing(Cliente::getNombre),
            new ClienteMapper()
        );

    }
}

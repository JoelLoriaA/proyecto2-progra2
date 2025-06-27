package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import org.jdom2.JDOMException;
import java.io.IOException;
import java.util.Comparator;

public class ClienteDAO extends GenericXmlRepository<Cliente, Integer> {
    public ClienteDAO() throws JDOMException, IOException {
        super(
            "xml_data/clientes.xml",
            "clientes",
            "cliente",
            Cliente::getId,
            Comparator.comparing(Cliente::getId),
            new ClienteMapper()
        );
    }
}
package cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers.ClienteMapper;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.GenericXmlRepository;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Comparator;


public class ClienteDAO extends GenericXmlRepository<Cliente, Integer> {

    public ClienteDAO() throws JDOMException, IOException {
        this(System.getProperty("user.home") + "/movasystem/clientes.xml");
    }

    public ClienteDAO(String filePath) throws JDOMException, IOException {
        super(
            filePath,
            "clientes",
            "cliente",
            Cliente::getId,
            Comparator.comparing(Cliente::getPrimerApellido).thenComparing(Cliente::getNombre),
            new ClienteMapper()
        );
    }
}

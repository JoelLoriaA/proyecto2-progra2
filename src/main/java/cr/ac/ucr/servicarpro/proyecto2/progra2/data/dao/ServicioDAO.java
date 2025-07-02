package cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.GenericXmlRepository;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers.ServicioMapper;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Servicio;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Comparator;

public class ServicioDAO extends GenericXmlRepository<Servicio, Integer> {

    public ServicioDAO() throws JDOMException, IOException {
        this(System.getProperty("user.home") + "/movasystem/servicios.xml");
    }

    public ServicioDAO(String filePath) throws JDOMException, IOException {
        super(
            filePath,
            "servicios",                    // Tag raíz
            "servicio",                     // Tag de cada entidad
            Servicio::getId,                // Clave primaria
            Comparator.comparing(Servicio::getNombre), // Orden por nombre
            new ServicioMapper()            // Mapper
        );
    }
}

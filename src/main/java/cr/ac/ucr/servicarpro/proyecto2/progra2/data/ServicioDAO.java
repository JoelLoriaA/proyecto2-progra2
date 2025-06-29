package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Servicio;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Comparator;

/**
 * DAO para acceder a los servicios almacenados en archivo XML.
 */
public class ServicioDAO extends GenericXmlRepository<Servicio, Integer> {

    public ServicioDAO() throws JDOMException, IOException {
        super(
            "xml_data/servicios.xml",     // Ruta del archivo XML
            "servicios",                  // Tag raíz
            "servicio",                   // Tag de cada entidad
            Servicio::getId,              // Clave primaria
            Comparator.comparing(Servicio::getNombre), // Orden alfabético por nombre
            new ServicioMapper()          // Mapper personalizado
        );
    }

    
}

package cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.GenericXmlRepository;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers.VehiculoMapper;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Vehiculo;
import org.jdom2.JDOMException;
import java.io.IOException;
import java.util.Comparator;

public class VehiculoDAO extends GenericXmlRepository<Vehiculo, String> {
    // Constructor por defecto para producción
    public VehiculoDAO() throws JDOMException, IOException {
        this(System.getProperty("user.home") + "/movasystem/vehiculos.xml");
    }

    // Constructor que acepta path personalizado para testing
    public VehiculoDAO(String filePath) throws JDOMException, IOException {
        super(
                filePath,
                "vehiculos",
                "vehiculo",
                Vehiculo::getNumeroPlaca,
                Comparator.comparing(Vehiculo::getNumeroPlaca),
                new VehiculoMapper()
        );
    }
}
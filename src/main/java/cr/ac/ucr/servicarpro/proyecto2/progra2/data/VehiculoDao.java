package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Vehiculo;
import org.jdom2.JDOMException;
import java.io.IOException;
import java.util.Comparator;

public class VehiculoDao extends GenericXmlRepository<Vehiculo, String> {
    public VehiculoDao() throws JDOMException, IOException {
        super(
            "xml_data/vehiculos.xml",
            "vehiculos",
            "vehiculo",
            Vehiculo::getNumeroPlaca,
            Comparator.comparing(Vehiculo::getNumeroPlaca),
            new VehiculoMapper()
        );
    }
}
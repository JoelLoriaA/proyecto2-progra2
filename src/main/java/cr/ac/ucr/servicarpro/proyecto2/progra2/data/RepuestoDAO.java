package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Comparator;

public class RepuestoDAO extends GenericXmlRepository<Repuesto, Integer> {

    public RepuestoDAO() throws JDOMException, IOException {
        super(
            "xml_data/repuestos.xml",
            "repuestos",
            "repuesto",
            Repuesto::getId,
            Comparator.comparing(Repuesto::getNombre), // Orden por nombre
            new RepuestoMapper()
        );
    }
}

package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Comparator;

public class RepuestoDAO extends GenericXmlRepository<Repuesto, Integer> {

    public RepuestoDAO() throws JDOMException, IOException {
        this(System.getProperty("user.home") + "/movasystem/repuestos.xml");
    }

    public RepuestoDAO(String filePath) throws JDOMException, IOException {
        super(
            filePath,
            "repuestos",
            "repuesto",
            Repuesto::getId,
            Comparator.comparing(Repuesto::getNombre),
            new RepuestoMapper()
        );
    }
}

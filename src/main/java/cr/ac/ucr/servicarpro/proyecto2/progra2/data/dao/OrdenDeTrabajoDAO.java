package cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.GenericXmlRepository;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers.OrdenDeTrabajoMapper;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.OrdenDeTrabajo;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Comparator;

public class OrdenDeTrabajoDAO extends GenericXmlRepository<OrdenDeTrabajo, Integer> {

    // Constructor por defecto para producción
    public OrdenDeTrabajoDAO() throws JDOMException, IOException {
        this(System.getProperty("user.home") + "/movasystem/ordenesDeTrabajo.xml");
    }

    // Constructor que acepta path personalizado para testing
    public OrdenDeTrabajoDAO(String filePath) throws JDOMException, IOException {
        super(
                filePath,
                "ordenes",
                "orden",
                OrdenDeTrabajo::getIdOrden,
                Comparator.comparing(OrdenDeTrabajo::getIdOrden),
                new OrdenDeTrabajoMapper()
        );
    }
}
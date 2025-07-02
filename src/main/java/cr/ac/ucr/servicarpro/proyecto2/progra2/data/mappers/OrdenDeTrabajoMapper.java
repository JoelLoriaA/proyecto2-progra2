package cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.DetalleOrden;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Estado;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.OrdenDeTrabajo;
import org.jdom2.Element;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenDeTrabajoMapper implements EntityMapper<OrdenDeTrabajo, Integer> {

    private final DetalleOrdenMapper detalleMapper = new DetalleOrdenMapper();

    @Override
    public OrdenDeTrabajo elementToEntity(Element e) {
        int idOrden = Integer.parseInt(e.getAttributeValue("idOrden"));
        String descripcionSolicitud = e.getChildText("descripcionSolicitud");
        LocalDate fechaIngreso = LocalDate.parse(e.getChildText("fechaIngreso"));

        // Mapear Estado correctamente como objeto
        Element estadoElem = e.getChild("estado");
        Estado estado = new Estado(
            Integer.parseInt(estadoElem.getChildText("id")),
            estadoElem.getChildText("descripcion")
        );

        String numeroPlaca = e.getChildText("numeroPlaca");
        int idCliente = Integer.parseInt(e.getChildText("idCliente"));
        String observacionesRecepcion = e.getChildText("observacionesRecepcion");

        // Procesar detalles usando el DetalleOrdenMapper
        List<DetalleOrden> detalles = new ArrayList<>();
        Element detallesElement = e.getChild("detalles");
        if (detallesElement != null) {
            List<Element> detalleElements = detallesElement.getChildren("detalleOrden");
            for (Element detalleElement : detalleElements) {
                DetalleOrden detalle = detalleMapper.elementToEntity(detalleElement);
                detalles.add(detalle);
            }
        }

        return new OrdenDeTrabajo(idOrden, descripcionSolicitud, fechaIngreso, estado,
                                 numeroPlaca, idCliente, observacionesRecepcion, detalles);
    }

    @Override
    public Element entityToElement(OrdenDeTrabajo orden) {
        Element e = new Element("orden");
        e.setAttribute("idOrden", String.valueOf(orden.getIdOrden()));

        e.addContent(new Element("descripcionSolicitud").setText(orden.getDescripcionSolicitud()));
        e.addContent(new Element("fechaIngreso").setText(orden.getFechaIngreso().toString()));

        // Mapear Estado como elemento complejo
        Element estadoElem = new Element("estado");
        estadoElem.addContent(new Element("id").setText(String.valueOf(orden.getEstado().getId())));
        estadoElem.addContent(new Element("descripcion").setText(orden.getEstado().getDescripcion()));
        e.addContent(estadoElem);

        e.addContent(new Element("numeroPlaca").setText(orden.getNumeroPlaca()));
        e.addContent(new Element("idCliente").setText(String.valueOf(orden.getIdCliente())));
        e.addContent(new Element("observacionesRecepcion").setText(orden.getObservacionesRecepcion()));

        // Procesar detalles usando el DetalleOrdenMapper
        Element eDetalles = new Element("detalles");
        for (DetalleOrden detalle : orden.getDetalles()) {
            Element detalleElement = detalleMapper.entityToElement(detalle);
            eDetalles.addContent(detalleElement);
        }
        e.addContent(eDetalles);

        return e;
    }

    @Override
    public Integer getKeyFromElement(Element element) {
        return Integer.parseInt(element.getAttributeValue("idOrden"));
    }
}
package cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Servicio;
import org.jdom2.Element;

/**
 * Mapper JDOM para convertir entre Elementos XML y objetos Servicio.
 */
public class ServicioMapper implements EntityMapper<Servicio, Integer> {

    @Override
    public Servicio elementToEntity(Element e) {
        int id = Integer.parseInt(e.getAttributeValue("id"));
        String nombre = e.getChildText("nombre");
        String descripcion = e.getChildText("descripcion");
        double precio = Double.parseDouble(e.getChildText("precio"));
        double costoManoObra = Double.parseDouble(e.getChildText("costoManoObra"));

        return new Servicio(id, nombre, descripcion, precio, costoManoObra);
    }

    @Override
    public Element entityToElement(Servicio servicio) {
        Element e = new Element("servicio");
        e.setAttribute("id", String.valueOf(servicio.getId()));

        e.addContent(new Element("nombre").setText(servicio.getNombre()));
        e.addContent(new Element("descripcion").setText(servicio.getDescripcion()));
        e.addContent(new Element("precio").setText(String.valueOf(servicio.getPrecio())));
        e.addContent(new Element("costoManoObra").setText(String.valueOf(servicio.getCostoManoObra())));

        return e;
    }

    @Override
    public Integer getKeyFromElement(Element element) {
        return Integer.parseInt(element.getAttributeValue("id"));
    }
}

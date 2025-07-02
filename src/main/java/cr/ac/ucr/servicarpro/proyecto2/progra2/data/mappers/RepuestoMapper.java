package cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Repuesto;
import org.jdom2.Element;

public class RepuestoMapper implements EntityMapper<Repuesto, Integer> {

    @Override
    public Repuesto elementToEntity(Element e) {
        int id = Integer.parseInt(e.getAttributeValue("id"));
        String nombre = e.getChildText("nombre");
        String descripcion = e.getChildText("descripcion");
        double precio = Double.parseDouble(e.getChildText("precio"));
        int cantidad = Integer.parseInt(e.getChildText("cantidadDisponible"));
        boolean pedido = Boolean.parseBoolean(e.getChildText("pedido"));

        return new Repuesto(id, nombre, descripcion, precio, cantidad, pedido);
    }

    @Override
    public Element entityToElement(Repuesto repuesto) {
        Element e = new Element("repuesto");
        e.setAttribute("id", String.valueOf(repuesto.getId()));

        e.addContent(new Element("nombre").setText(repuesto.getNombre()));
        e.addContent(new Element("descripcion").setText(repuesto.getDescripcion()));
        e.addContent(new Element("precio").setText(String.valueOf(repuesto.getPrecio())));
        e.addContent(new Element("cantidadDisponible").setText(String.valueOf(repuesto.getCantidadDisponible())));
        e.addContent(new Element("pedido").setText(String.valueOf(repuesto.isPedido())));

        return e;
    }

    @Override
    public Integer getKeyFromElement(Element element) {
        return Integer.parseInt(element.getAttributeValue("id"));
    }
}

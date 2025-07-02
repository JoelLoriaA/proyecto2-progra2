package cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import org.jdom2.Element;

/**
 * Mapper JDOM para convertir entre Elementos XML y objetos Cliente.
 */
public class ClienteMapper implements EntityMapper<Cliente, Integer> {

    @Override
    public Cliente elementToEntity(Element e) {
        int id = Integer.parseInt(e.getAttributeValue("id"));
        String nombre = e.getChildText("nombre");
        String primerApellido = e.getChildText("primerApellido");
        String segundoApellido = e.getChildText("segundoApellido");
        String telefono = e.getChildText("telefono");
        String direccion = e.getChildText("direccion");
        String email = e.getChildText("email");

        return new Cliente(id, nombre, primerApellido, segundoApellido, telefono, direccion, email);
    }

    @Override
    public Element entityToElement(Cliente cliente) {
        Element e = new Element("cliente");
        e.setAttribute("id", String.valueOf(cliente.getId()));

        e.addContent(new Element("nombre").setText(cliente.getNombre()));
        e.addContent(new Element("primerApellido").setText(cliente.getPrimerApellido()));
        e.addContent(new Element("segundoApellido").setText(cliente.getSegundoApellido()));
        e.addContent(new Element("telefono").setText(cliente.getTelefono()));
        e.addContent(new Element("direccion").setText(cliente.getDireccion()));
        e.addContent(new Element("email").setText(cliente.getEmail()));

        return e;
    }

    @Override
    public Integer getKeyFromElement(Element element) {
        return Integer.parseInt(element.getAttributeValue("id"));
    }
}

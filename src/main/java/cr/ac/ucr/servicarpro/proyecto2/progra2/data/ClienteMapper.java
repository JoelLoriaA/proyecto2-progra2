/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;
import org.jdom2.Element;

/**
 * Mapper para Cliente con JDOM
 * Convierte entre Element <-> Cliente
 * @author
 */
public class ClienteMapper implements EntityMapper<Cliente, Integer> {

    @Override
    public Cliente elementToEntity(Element e) {
        int id = Integer.parseInt(e.getAttributeValue("id"));
        String nombre = e.getChildText("nombre");
        String apellido = e.getChildText("apellido");
        String segundoApellido = e.getChildText("segundoApellido");
        String telefono = e.getChildText("telefono");
        String direccion = e.getChildText("direccion");
        String email = e.getChildText("email");

        return new Cliente(id, nombre, apellido, segundoApellido, telefono, direccion, email);
    }

    @Override
    public Element entityToElement(Cliente cliente) {
        Element e = new Element("cliente");
        e.setAttribute("id", String.valueOf(cliente.getId()));

        Element eNombre = new Element("nombre").setText(cliente.getNombre());
        Element eApellido = new Element("apellido").setText(cliente.getPrimerApellido());
        Element eSegundoApellido = new Element("segundoApellido").setText(cliente.getSegundoApellido());
        Element eTelefono = new Element("telefono").setText(cliente.getTelefono());
        Element eDireccion = new Element("direccion").setText(cliente.getDireccion());
        Element eEmail = new Element("email").setText(cliente.getEmail());

        e.addContent(eNombre);
        e.addContent(eApellido);
        e.addContent(eSegundoApellido);
        e.addContent(eTelefono);
        e.addContent(eDireccion);
        e.addContent(eEmail);

        return e;
    }

    @Override
    public Integer getKeyFromElement(Element element) {
        return Integer.parseInt(element.getAttributeValue("id"));
    }
}

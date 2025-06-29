/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.DetalleOrden;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Estado;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.TipoDetalle;
import org.jdom2.Element;

public class DetalleOrdenMapper implements EntityMapper<DetalleOrden, Integer> {

    @Override
    public DetalleOrden elementToEntity(Element e) {
        int idDetalleOrden = Integer.parseInt(e.getAttributeValue("idDetalleOrden"));
        int cantidad = Integer.parseInt(e.getChildText("cantidad"));
        String observaciones = e.getChildText("observaciones");
        int idOrdenTrabajo = Integer.parseInt(e.getChildText("idOrdenTrabajo"));
        double precio = Double.parseDouble(e.getChildText("precio"));
        String nombreRepuesto = e.getChildText("nombreRepuesto");
        boolean repuestoPedido = Boolean.parseBoolean(e.getChildText("repuestoPedido"));
        double costoManoObra = Double.parseDouble(e.getChildText("costoManoObra"));

        // Mapear TipoDetalle y Estado (puedes mejorar esto según tu diseño)
        Element tipoDetalleElem = e.getChild("tipoDetalle");
        TipoDetalle tipoDetalle = new TipoDetalle(
            Integer.parseInt(tipoDetalleElem.getChildText("id")),
            tipoDetalleElem.getChildText("nombre")
        );

        Element estadoElem = e.getChild("estado");
        Estado estado = new Estado(
            Integer.parseInt(estadoElem.getChildText("id")),
            estadoElem.getChildText("descripcion")
        );

        return new DetalleOrden(idDetalleOrden, cantidad, observaciones, idOrdenTrabajo,
                tipoDetalle, estado, precio, nombreRepuesto, repuestoPedido, costoManoObra);
    }

    @Override
    public Element entityToElement(DetalleOrden d) {
        Element e = new Element("detalleOrden");
        e.setAttribute("idDetalleOrden", String.valueOf(d.getIdDetalleOrden()));

        e.addContent(new Element("cantidad").setText(String.valueOf(d.getCantidad())));
        e.addContent(new Element("observaciones").setText(d.getObservaciones()));
        e.addContent(new Element("idOrdenTrabajo").setText(String.valueOf(d.getIdOrdenTrabajo())));
        e.addContent(new Element("precio").setText(String.valueOf(d.getPrecio())));
        e.addContent(new Element("nombreRepuesto").setText(d.getNombreRepuesto()));
        e.addContent(new Element("repuestoPedido").setText(String.valueOf(d.isRepuestoPedido())));
        e.addContent(new Element("costoManoObra").setText(String.valueOf(d.getCostoManoObra())));

        // TipoDetalle
        Element tipoDetalleElem = new Element("tipoDetalle");
        tipoDetalleElem.addContent(new Element("id").setText(String.valueOf(d.getTipoDetalle().getId())));
        tipoDetalleElem.addContent(new Element("nombre").setText(d.getTipoDetalle().getNombre()));
        e.addContent(tipoDetalleElem);

        // Estado
        Element estadoElem = new Element("estado");
        estadoElem.addContent(new Element("id").setText(String.valueOf(d.getEstado().getId())));
        estadoElem.addContent(new Element("descripcion").setText(d.getEstado().getDescripcion()));
        e.addContent(estadoElem);

        return e;
    }

    @Override
    public Integer getKeyFromElement(Element element) {
        return Integer.parseInt(element.getAttributeValue("idDetalleOrden"));
    }
}
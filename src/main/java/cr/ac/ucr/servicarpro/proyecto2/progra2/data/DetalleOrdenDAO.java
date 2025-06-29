/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.DetalleOrden;
import org.jdom2.JDOMException;
import java.io.IOException;
import java.util.Comparator;

public class DetalleOrdenDAO extends GenericXmlRepository<DetalleOrden, Integer> {
    public DetalleOrdenDAO() throws JDOMException, IOException {
        super(
            "xml_data/detalles_orden.xml",
            "detallesOrden",
            "detalleOrden",
            DetalleOrden::getIdDetalleOrden,
            Comparator.comparing(DetalleOrden::getIdDetalleOrden),
            new DetalleOrdenMapper()
        );
    }
}
package cr.ac.ucr.servicarpro.proyecto2.progra2.data.mappers;

import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Vehiculo;
import org.jdom2.Element;

public class VehiculoMapper implements EntityMapper<Vehiculo, String> {

    @Override
    public Vehiculo elementToEntity(Element e) {
        String numeroPlaca = e.getAttributeValue("numeroPlaca");
        String color = e.getChildText("color");
        String marca = e.getChildText("marca");
        String estilo = e.getChildText("estilo");
        int anio = Integer.parseInt(e.getChildText("anio"));
        String vin = e.getChildText("vin");
        double cilindraje = Double.parseDouble(e.getChildText("cilindraje"));
        String duenio = e.getChildText("duenio");

        return new Vehiculo(numeroPlaca, color, marca, estilo, anio, vin, cilindraje, duenio);
    }

    @Override
    public Element entityToElement(Vehiculo vehiculo) {
        Element e = new Element("vehiculo");
        e.setAttribute("numeroPlaca", vehiculo.getNumeroPlaca());

        Element eColor = new Element("color").setText(vehiculo.getColor());
        Element eMarca = new Element("marca").setText(vehiculo.getMarca());
        Element eEstilo = new Element("estilo").setText(vehiculo.getEstilo());
        Element eAnio = new Element("anio").setText(String.valueOf(vehiculo.getAnio()));
        Element eVin = new Element("vin").setText(vehiculo.getVin());
        Element eCilindraje = new Element("cilindraje").setText(String.valueOf(vehiculo.getCilindraje()));
        Element eDuenio = new Element("duenio").setText(vehiculo.getDuenio());

        e.addContent(eColor);
        e.addContent(eMarca);
        e.addContent(eEstilo);
        e.addContent(eAnio);
        e.addContent(eVin);
        e.addContent(eCilindraje);
        e.addContent(eDuenio);

        return e;
    }

    @Override
    public String getKeyFromElement(Element element) {
        return element.getAttributeValue("numeroPlaca");
    }
}
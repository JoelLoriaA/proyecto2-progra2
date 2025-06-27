package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import org.jdom2.Element;

public interface EntityMapper<T, K extends Comparable<K>> {
    T elementToEntity(Element element);
    Element entityToElement(T entity);
    K getKeyFromElement(Element element);
}
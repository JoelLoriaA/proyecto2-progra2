package cr.ac.ucr.servicarpro.proyecto2.progra2.data;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;

public class GenericXmlRepository<T, K extends Comparable<K>> {

    private final Document document;
    private final Element root;
    private final String filePath;
    private final Function<T, K> keyExtractor;
    private final Comparator<T> comparator;
    private final EntityMapper<T, K> mapper;
    private final String entityTag;

    public GenericXmlRepository(String filePath, String rootTag, String entityTag, Function<T, K> keyExtractor, Comparator<T> comparator, EntityMapper<T, K> mapper) {
        this.filePath = filePath;
        this.keyExtractor = keyExtractor;
        this.comparator = comparator;
        this.mapper = mapper;
        this.entityTag = entityTag;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                SAXBuilder saxBuilder = new SAXBuilder();
                saxBuilder.setIgnoringElementContentWhitespace(true);
                this.document = saxBuilder.build(file);
                this.root = document.getRootElement();
            } else {
                this.root = new Element(rootTag);
                this.document = new Document(root);
                save();
            }
        } catch (JDOMException | IOException e) {
            throw new XmlRepositoryException("Error al inicializar el repositorio XML: " + filePath, e);
        }
    }

    public void insertOrUpdate(T entity) {
        try {
            List<Element> elements = root.getChildren(entityTag);
            K key = keyExtractor.apply(entity);
            boolean updated = false;

            for (int i = 0; i < elements.size(); i++) {
                Element e = elements.get(i);
                K existingKey = mapper.getKeyFromElement(e);
                if (key.compareTo(existingKey) == 0) {
                    Element newElement = mapper.entityToElement(entity);
                    elements.set(i, newElement);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                Element newElement = mapper.entityToElement(entity);
                root.addContent(newElement);
            }
            sortAndSave();
        } catch (Exception e) {
            throw new XmlRepositoryException("Error al insertar o actualizar entidad", e);
        }
    }

    public List<T> findAll() {
        try {
            List<Element> elements = root.getChildren(entityTag);
            List<T> entities = new ArrayList<>();
            for (Element e : elements) {
                entities.add(mapper.elementToEntity(e));
            }
            entities.sort(comparator);
            return entities;
        } catch (Exception e) {
            throw new XmlRepositoryException("Error al obtener todas las entidades", e);
        }
    }

    public Optional<T> findByKey(K key) {
        try {
            return findAll().stream()
                    .filter(entity -> key.equals(keyExtractor.apply(entity)))
                    .findFirst();
        } catch (Exception e) {
            throw new XmlRepositoryException("Error al buscar entidad por clave", e);
        }
    }

    public int getNextId() {
        try {
            return findAll().stream()
                    .map(keyExtractor)
                    .filter(k -> k instanceof Integer)
                    .map(k -> (Integer) k)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (Exception e) {
            throw new XmlRepositoryException("Error al obtener el siguiente ID", e);
        }
    }

    public void edit(T entity) {
        insertOrUpdate(entity);
    }

    private void sortAndSave() {
        try {
            List<Element> elements = new ArrayList<>(root.getChildren(entityTag));
            List<T> entities = new ArrayList<>();

            for (Element e : elements) {
                entities.add(mapper.elementToEntity(e));
            }

            entities.sort(comparator);

            root.removeChildren(entityTag);

            for (T entity : entities) {
                Element newElement = mapper.entityToElement(entity);
                root.addContent(newElement);
            }

            save();
        } catch (Exception e) {
            throw new XmlRepositoryException("Error al ordenar y guardar entidades", e);
        }
    }

    private void save() {
        try {
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            XMLOutputter outputter = new XMLOutputter(format);

            File file = new File(this.filePath);

            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (created) {
                    System.out.println(" Carpeta '" + parentDir.getPath() + "' creada correctamente.");
                } else {
                    System.err.println(" No se pudo crear la carpeta: " + parentDir.getPath());
                }
            }
        
        System.out.println("[XML] Guardando en: " + file.getAbsolutePath());
            try (PrintWriter writer = new PrintWriter(file)) {
                outputter.output(this.document, writer);
            }
        } catch (IOException e) {
            throw new XmlRepositoryException("Error al guardar el archivo XML", e);
        }
    }
}
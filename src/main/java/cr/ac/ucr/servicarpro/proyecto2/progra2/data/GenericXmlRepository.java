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

/**
 * Repositorio XML genérico usando JDOM para cualquier entidad.
 */
public class GenericXmlRepository<T, K extends Comparable<K>> {

    private final Document document;
    private final Element root;
    public String filePath;
    private final Function<T, K> keyExtractor;
    private final Comparator<T> comparator;
    private final EntityMapper<T, K> mapper;
    private final String entityTag;

    public GenericXmlRepository(String filePath, String rootTag, String entityTag, Function<T, K> keyExtractor, Comparator<T> comparator, EntityMapper<T, K> mapper) throws JDOMException, IOException {
        this.filePath = filePath;
        this.keyExtractor = keyExtractor;
        this.comparator = comparator;
        this.mapper = mapper;
        this.entityTag = entityTag;

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
    }

    public void insertOrUpdate(T entity) throws IOException {
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
    }

    public List<T> findAll() {
        List<Element> elements = root.getChildren(entityTag);
        List<T> entities = new ArrayList<>();
        for (Element e : elements) {
            entities.add(mapper.elementToEntity(e));
        }
        entities.sort(comparator);
        return entities;
    }

    public Optional<T> findByKey(K key) {
        return findAll().stream()
                .filter(entity -> key.equals(keyExtractor.apply(entity)))
                .findFirst();
    }

    public int getNextId() {
        return findAll().stream()
                .map(keyExtractor)
                .filter(k -> k instanceof Integer)
                .map(k -> (Integer) k)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    public void edit(T entity) throws IOException {
        insertOrUpdate(entity);
    }

    private void sortAndSave() throws IOException {
        List<Element> elements = new ArrayList<>(root.getChildren(entityTag)); // copiar para evitar ConcurrentModificationException
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
    }

    private void save() throws IOException {
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
    }
    
        public void save(T entity) throws IOException {
        insertOrUpdate(entity);
    }

    public void delete(K key) throws IOException {
        List<Element> elements = root.getChildren(entityTag);
        Element toRemove = null;

        for (Element e : elements) {
            K existingKey = mapper.getKeyFromElement(e);
            if (key.compareTo(existingKey) == 0) {
                toRemove = e;
                break;
            }
        }

        if (toRemove != null) {
            root.removeContent(toRemove);
            save();
        }
    }

    public T findById(K key) {
        return findByKey(key).orElse(null);
    }

}

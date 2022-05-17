package collection;

import data.Product;
import database.ProductDatabaseManager;
import exceptions.CannotAddException;
import exceptions.EmptyCollectionException;
import exceptions.FileException;
import exceptions.NoSuchIdException;
import file.FileManager;
import utils.DateConvertor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class ProductDequeManager implements CollectionManager<Product> {
    private Deque<Product> collection;
    private Set<Long> uniqueIds;
    private LocalDate initDate;

    public ProductDequeManager() {
        uniqueIds = new ConcurrentSkipListSet<>();
        collection = new ConcurrentLinkedDeque<>();
        initDate = java.time.LocalDate.now();
    }

    public long generateNextId() {
        if (collection.isEmpty())
            return 1;
        else {
            long id = collection.element().getId() + 1;
            if (uniqueIds.contains(id)) {
                while (uniqueIds.contains(id)) id += 1;
            }
            return id;
        }
    }

    public void sort() {
        collection = collection.stream().sorted(new Product.SortingComparator()).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
        //Collections.sort(collection, new Product.SortingComparator());
    }

    public Product getById(long id) {
        if (collection.isEmpty()) throw new EmptyCollectionException();
        Optional<Product> product = collection.stream()
                .filter(w->w.getId() == id)
                .findFirst();
        if(!product.isPresent()) {
            throw new NoSuchIdException(id);
        }
        return product.get();
    }

    public Deque<Product> getCollection() {
        return collection;
    }

    public void add(Product product) {
        long id = generateNextId();
        uniqueIds.add(id);
        product.setId(id);
        collection.add(product);
    }

    protected void addWithoutIdGeneration(Product product) {
        uniqueIds.add(product.getId());
        collection.add(product);
    }

    public String getInfo() {
        return "LinkedList of Product, size: " + collection.size() + ", initialization date: " + initDate.toString();
    }

    public boolean chekID(long id) {
        return uniqueIds.contains(id);
    }

    public void removeById(long id) {
        if (collection.isEmpty()) throw new EmptyCollectionException();
        Optional<Product> product = collection.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (product.isPresent()) {
            collection.remove(product.get());
            uniqueIds.remove(id);
        }
    }

    public void removeAll(Collection<Long> ids) {
        Iterator<Long> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next();
            collection.removeIf(product -> product.getId() == id);
            iterator.remove();
        }
    }

    public void updateById(long id, Product newProduct) {
        if (collection.isEmpty()) throw new EmptyCollectionException();
        Optional<Product> product = collection.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (!product.isPresent()) {
            throw new NoSuchIdException(id);
        }
        collection.remove(product.get());
        newProduct.setId(id);
        collection.add(newProduct);
    }

    public void removeGreater(Collection<Long> ids) {
        if (collection.isEmpty()) throw new EmptyCollectionException();
        Iterator<Long> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next();
            collection.removeIf(product -> product.getId() == id);
            iterator.remove();
        }
    }

    public void removeLower(Collection<Long> ids) {
        if (collection.isEmpty()) throw new EmptyCollectionException();
        Iterator<Long> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next();
            collection.removeIf(product -> product.getId() == id);
            iterator.remove();
        }
    }




    public List<Product> filterLessThanManufactureCost(Float cost) {
        List<Product> productList = collection.stream()
                .filter(w -> w.getManufactureCost() < cost)
                .collect(Collectors.toList());
        return productList;
    }

    public void clear() {
        collection.clear();
        uniqueIds.clear();
    }

    public void addIfMin(Product product) {
        if (collection.stream()
                .min(Product::compareTo)
                .filter(w -> w.compareTo(product) == -1)
                .isPresent()) {
            throw new CannotAddException();
        }
        add(product);
    }

    public List<String> getUniqueOwner() {
        List<String> owner = new LinkedList<>();
        owner = collection.stream()
                .map(product -> product.getUniqueOwner())
                .distinct()
                .collect(Collectors.toList());
        return owner;
    }

    public void deserializeCollection() {
    }
}



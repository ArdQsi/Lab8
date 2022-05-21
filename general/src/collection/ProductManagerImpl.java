package collection;

import data.Product;
import exceptions.CannotAddException;
import exceptions.EmptyCollectionException;
import exceptions.NoSuchIdException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ProductManagerImpl<T extends Collection<Product>> implements ProductManager {
    private final LocalDate initDate;


    public ProductManagerImpl() {
        initDate = LocalDate.now();
    }

    public long generateNextId() {
        if (getCollection().isEmpty()) return 1;
        else {
            long id = 1;
            if (getUniqueIds().contains(id)) {
                while (getUniqueIds().contains(id)) id += 1;
            }
            return id;
        }
    }


    public void sort() {

    }


    public Product getById(long id) {
        if (getCollection().isEmpty()) throw new EmptyCollectionException();
        Optional<Product> product = getCollection().stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (!product.isPresent()) {
            throw new NoSuchIdException(id);
        }
        return product.get();
    }

    protected void addWithoutIdGeneration(Product product) {
        getUniqueIds().add(product.getId());
        getCollection().add(product);
    }


    public void add(Product element) {
        long id = generateNextId();
        getUniqueIds().add(id);
        element.setId(id);
        getCollection().add(element);

    }

    @Override
    public String getInfo() {
        return "Database of Product, size: " + getCollection().size() + ", initialization date: " + initDate.toString();
    }


    public boolean chekID(long id) {
        return getUniqueIds().contains(id);
    }


    public void updateById(long id, Product newProduct) {
        if (getCollection().isEmpty()) throw new EmptyCollectionException();
        Optional<Product> product = getCollection().stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (!product.isPresent()) {
            throw new NoSuchIdException(id);
        }
        getCollection().remove(product.get());
        newProduct.setId(id);
        getCollection().add(newProduct);
    }


    public void removeById(long id) {
        if (getCollection().isEmpty()) throw new EmptyCollectionException();
        Optional<Product> product = getCollection().stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (product.isPresent()) {
            getCollection().remove(product.get());
            getUniqueIds().remove(id);
        }
    }


    public void clear() {
        getCollection().clear();
        getUniqueIds().clear();
    }


    public void addIfMin(Product product) {
        if (getCollection().stream()
                .min(Product::compareTo)
                .filter(w -> w.compareTo(product) == -1)
                .isPresent()) {
            throw new CannotAddException();
        }
        add(product);
    }


    public void removeGreater(Collection<Long> ids) {
        if (getCollection().isEmpty()) throw new EmptyCollectionException();
        Iterator<Long> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next();
            getCollection().removeIf(product -> product.getId() == id);
            iterator.remove();
        }
    }


    public List<Product> filterLessThanManufactureCost(Float cost) {
        List<Product> productList = getCollection().stream()
                .filter(w -> w.getManufactureCost() < cost)
                .collect(Collectors.toList());
        return productList;
    }


    public List<String> getUniqueOwner() {
        List<String> owner = new LinkedList<>();
        owner = getCollection().stream()
                .map(product -> product.getUniqueOwner())
                .distinct()
                .collect(Collectors.toList());
        return owner;
    }

    public void removeLower(Collection<Long> ids) {
        if (getCollection().isEmpty()) throw new EmptyCollectionException();
        Iterator<Long> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next();
            getCollection().removeIf(product -> product.getId() == id);
            iterator.remove();
        }
    }

    @Override
    public void deserializeCollection() {

    }

    abstract public Set<Long> getUniqueIds();

    public abstract Collection<Product> getCollection();
}

package collection;

import connection.CollectionOperation;
import connection.Response;
import controllers.MainWindowController;
import data.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProductObservableManager extends ProductManagerImpl<ObservableList<Product>> {
    private ObservableList<Product> collection;
    private Set<Long> uniqueIds;
    private MainWindowController controller;

    public ProductObservableManager() {
        collection = FXCollections.observableArrayList();
        uniqueIds = ConcurrentHashMap.newKeySet();
    }

    public Set<Long> getUniqueIds() {
        return uniqueIds;
    }

    public void applyChanges(Response response) {
        CollectionOperation op = response.getCollectionOperation();
        Collection<Product> changes = response.getCollection();
        ObservableList<Product> old = FXCollections.observableArrayList(collection);
        //System.out.println(changes.size());
        //System.out.println(changes.toString());


        if (op == CollectionOperation.ADD) {
            for (Product product : changes) {
                super.addWithoutIdGeneration(product);
            }
        }
        if (op == CollectionOperation.REMOVE) {
            for (Product product : changes) {
                Collections.copy(old,collection);
                super.removeById(product.getId());
            }
        }
        if (op == CollectionOperation.UPDATE) {
            for (Product product : changes) {
                super.updateById(product.getId(), product);
            }
        }
        if (controller!=null && op!=CollectionOperation.NONE && changes!=null&&!changes.isEmpty()) {
            Platform.runLater(()->{
                controller.refreshTable();
                controller.refreshCanvas(op!=CollectionOperation.REMOVE? collection:old,changes,op);
            });
        }
    }

    public void setController(MainWindowController c) {
        controller = c;
    }

    public MainWindowController getController() {
        return controller;
    }
    public ObservableList<Product> getCollection() {
        return collection;
    }
}

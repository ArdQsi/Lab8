package collection;

import connection.CollectionOperation;
import connection.Response;
import controllers.MainWindowController;
import controllers.MainWindowController1;
import data.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProductObservableManager extends ProductManagerImpl<ObservableList<Product>> {
    private ObservableList<Product> collection;
    private Set<Long> uniqueIds;
    private MainWindowController1 controller;

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

        if (op == CollectionOperation.ADD) {
            for (Product product : changes) {
                super.addWithoutIdGeneration(product);
            }
        }
        if (op == CollectionOperation.REMOVE) {
            for (Product product : changes) {
                super.removeById(product.getId());
            }
        }
        if (op == CollectionOperation.UPDATE) {
            for (Product product : changes) {
                super.updateById(product.getId(), product);
            }
        }
        if (controller!=null && op!=CollectionOperation.NONE) {
            Platform.runLater(()->{
                controller.refreshTable();
            });
        }
    }

    public void setController(MainWindowController1 c) {
        controller = c;
    }

    public MainWindowController1 getController() {
        return controller;
    }
    public ObservableList<Product> getCollection() {
        return collection;
    }
}

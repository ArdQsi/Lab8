package controllers;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import data.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.App;
import tools.ObservableResourceFactory;

public class FilterController {
    ObservableList<Product> collection;
    MainWindowController mainWindowController;
    String columnName;
    Map<Integer, String> column;
    List<Product> filter_list;
    App app;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField filter_field;

    @FXML
    private ComboBox<String> box_column_filter;

    @FXML
    private Button filter_button;

    @FXML
    private Label collection_filter_text;


    @FXML
    void initialize() {
        column = new HashMap<>();
        column.put(1, "Id");
        column.put(2, "name");
        column.put(3, "x");
        column.put(4, "y");
        column.put(5, "date");
        column.put(6, "price");
        column.put(7, "manufacture_cost");
        column.put(8, "unit_of_measure");
        column.put(9, "p_name");
        column.put(10, "p_birthday");
        column.put(11, "p_weight");
        column.put(12, "p_passport_id");
        column.put(13, "user_login");
        box_column_filter.setItems(FXCollections.observableArrayList(column.values()));
        filter();
    }


    private void filter() {

        box_column_filter.setOnAction((events) -> {
            columnName = box_column_filter.getValue();
        });


        filter_button.setOnMouseClicked(event -> {
            String text = filter_field.getText().trim();
            if (text.equals("") || text == null || columnName == null) {
                filter_list = collection;
            }
            else if (columnName.equals("Id")) {
                filter_list = collection.stream().filter((s) -> Long.toString(s.getId()).equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("name")) {

                filter_list = collection.stream().filter((s) -> s.getName().equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("x")) {
                filter_list = collection.stream().filter((s) -> Long.toString(s.getCoordinates().getX()).equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("y")) {
                filter_list = collection.stream().filter((s) -> Float.toString(s.getCoordinates().getY()).equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("date")) {
                filter_list = collection.stream().filter((s) -> s.getCreationDate().toString().equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("price")) {
                filter_list = collection.stream().filter((s) -> s.getPrice().toString().equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("manufacture_cost")) {
                filter_list = collection.stream().filter((s) -> Float.toString(s.getManufactureCost()).equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("unit_of_measure")) {
                filter_list = collection.stream().filter((s) -> s.getUnitOfMeasure().toString().equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("p_name")) {
                filter_list = collection.stream().filter((s) -> s.getOwner().getPersonName().equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("p_birthday")) {
                filter_list = collection.stream().filter((s) -> s.getOwner().getBirthday().toString().equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("p_weight")) {
                filter_list = collection.stream().filter((s) -> Float.toString(s.getOwner().getWeight()).equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("p_passport_id")) {
                filter_list = collection.stream().filter((s) -> s.getOwner().getPassportID().equals(text)).collect(Collectors.toList());
            }
            else if (columnName.equals("user_login")) {
                filter_list = collection.stream().filter((s) -> s.getUserLogin().equals(text)).collect(Collectors.toList());
            }
            ObservableList<Product> observableArrayList =
                    FXCollections.observableArrayList(filter_list);
            mainWindowController.collection_table.setItems(observableArrayList);
        });


}

    public void setMainWindowController(MainWindowController m) {
        mainWindowController = m;
        ObservableResourceFactory observableResourceFactory = m.getResourceFactory();
        filter_field.promptTextProperty().bind(observableResourceFactory.getStringBinding("searchValue"));
        filter_button.textProperty().bind(observableResourceFactory.getStringBinding("apply"));
        collection_filter_text.textProperty().bind(observableResourceFactory.getStringBinding("FilterCollection"));
        box_column_filter.promptTextProperty().bind(observableResourceFactory.getStringBinding("column"));

    }

    public void setCollection(ObservableList<Product> c) {
        this.collection = c;
    }

    public void setApp(App app) {
        this.app = app;
    }
}

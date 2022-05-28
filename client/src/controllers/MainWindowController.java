package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import collection.ProductObservableManager;
import connection.*;
import data.Coordinates;
import exceptions.*;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import client.Client;

import data.Person;
import data.Product;
import data.UnitOfMeasure;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.App;
import tools.ObservableResourceFactory;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.layout.Pane;
import utils.DateConvertor;

import static io.ConsoleOutputter.print;
import static utils.DateConvertor.parseLocalDate;

public class MainWindowController implements Initializable {


    private App app;
    private Tooltip shapeTooltip;
    private Client client;
    private Stage askStage;
    private Stage primaryStage;
    private Map<String, Color> userColorMap;
    private Map<Integer, Text> textMap;
    private ObservableResourceFactory resourceFactory;
    private Map<String, Locale> localeMap;
    Product product;
    private Map<Shape, Long> shapeMap;
    private long idInField;
    private Map<Long, Circle> circleMap;
    private FileChooser fileChooser;
    private ResourceBundle resourceBundle;
    long rand = 1821L;
    Random random = new Random(rand);
    private Map<String, String> pattern;
    private String languagePattern;
    static boolean flag;


    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabMain;

    @FXML
    public TableView<Product> collection_table;

    @FXML
    private TableColumn<Product, Long> id_column;

    @FXML
    private TableColumn<Product, String> name_column;

    @FXML
    private TableColumn<Product, Long> coordinateX_column;

    @FXML
    private TableColumn<Product, Integer> coordinateY_column;

    @FXML
    private TableColumn<Product, String> date_column;

    @FXML
    private TableColumn<Product, Integer> price_column;

    @FXML
    private TableColumn<Product, Float> manufacture_cost_column;

    @FXML
    private TableColumn<Product, UnitOfMeasure> unit_of_measure_column;

    @FXML
    private TableColumn<Product, String> p_name_column;

    @FXML
    private TableColumn<Product, String> p_birthday_column;

    @FXML
    private TableColumn<Product, Float> p_weight_column;

    @FXML
    private TableColumn<Product, String> p_passport_id_column;

    @FXML
    private TableColumn<Product, String> user_login_column;

    @FXML
    private Label add_an_element_text;

    @FXML
    private TextField name_field;

    @FXML
    private TextField coordinateX_field;

    @FXML
    private TextField coordinateY_field;

    @FXML
    private TextField price_field;

    @FXML
    private TextField manufacture_cost_field;

    @FXML
    private TextField unit_of_measure_field;

    @FXML
    private TextField person_name_field;

    @FXML
    private TextField person_birthday_field;

    @FXML
    private TextField person_weight_field;

    @FXML
    private TextField person_passport_id_field;

    @FXML
    private Label commands_text;

    @FXML
    private Button print_unique_owner_but;

    @FXML
    private Button execute_script_but;

    @FXML
    private Button update_id_but;

    @FXML
    private Button add_if_min_but;

    @FXML
    private Button remove_lower_but;

    @FXML
    private Button remove_greater_but;

    @FXML
    private Button exit_but;

    @FXML
    private Button info_but;

    @FXML
    private Button remove_by_id_but;

    @FXML
    private Button clear_but;

    @FXML
    private Button add_but;

    @FXML
    private Label loginuser_field;

    @FXML
    private ComboBox<String> language_box;

    @FXML
    private ImageView filtr_view;

    @FXML
    private ImageView help_but;

    @FXML
    private Tab tabMap;

    @FXML
    private Pane canvas_plane;

    @FXML
    private Label collection_map_text;

    @FXML
    private Label loginuser_field1;
    private TableColumn<Product, Long> d;

    public void initialize(URL location, ResourceBundle resources) {

        filter_view();
        this.resourceBundle = resources;
        initializeTable();
        textMap = new HashMap<>();
        localeMap = new HashMap<>();
        userColorMap = new HashMap<>();
        shapeMap = new HashMap<>();
        circleMap = new HashMap<>();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        localeMap.put("English", new Locale("en"));
        localeMap.put("Nederlands", new Locale("nl"));
        localeMap.put("Ελληνικά", new Locale("grk"));
        localeMap.put("Español", new Locale("spa"));
        localeMap.put("Русский", new Locale("ru"));
        help_button();
        language_box.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        pattern = new HashMap<>();
        pattern.put("English", "dd-MM-yyyy");
        pattern.put("Nederlands", "dd-MM-yyyy");
        pattern.put("Ελληνικά", "dd/MM/yyyy");
        pattern.put("Español", "dd/MM/yyyy");
        pattern.put("Русский", "dd.MM.yyyy");
        languagePattern = "dd-MM-yyyy";
    }


    /**
     * initialize table
     */
    private void initializeTable() {
        id_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        name_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        coordinateX_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
        coordinateY_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        date_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate().format(DateTimeFormatter.ofPattern(languagePattern))));
        price_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        manufacture_cost_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getManufactureCost()));
        unit_of_measure_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUnitOfMeasure()));
        p_name_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getPersonName()));
        p_birthday_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getBirthday().format(DateTimeFormatter.ofPattern(languagePattern))));
        p_weight_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getWeight()));
        p_passport_id_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getPassportID()));
        user_login_column.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUserLogin()));

        collection_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int id = collection_table.getSelectionModel().getSelectedIndex();
            if (id != -1) {
                this.name_field.setText(client.getProductManager().getCollection().get(id).getName());
                this.coordinateX_field.setText(Long.toString(client.getProductManager().getCollection().get(id).getCoordinates().getX()));
                this.coordinateY_field.setText(client.getProductManager().getCollection().get(id).getCoordinates().getY().toString());
                this.price_field.setText(client.getProductManager().getCollection().get(id).getPrice().toString());
                this.manufacture_cost_field.setText(Float.toString(client.getProductManager().getCollection().get(id).getManufactureCost()));
                this.unit_of_measure_field.setText(client.getProductManager().getCollection().get(id).getUnitOfMeasure().toString());
                this.person_name_field.setText(client.getProductManager().getCollection().get(id).getOwner().getPersonName().toString());
                this.person_birthday_field.setText(DateConvertor.dateToString1(client.getProductManager().getCollection().get(id).getOwner().getBirthday()));
                this.person_weight_field.setText(Float.toString(client.getProductManager().getCollection().get(id).getOwner().getWeight()));
                this.person_passport_id_field.setText(client.getProductManager().getCollection().get(id).getOwner().getPassportID().toString());

            }
        });
    }


    public void initLangs(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        for (String localeName : localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceFactory.getResources().getLocale()))
                language_box.getSelectionModel().select(localeName);
        }
        language_box.setOnAction((event) -> {
            Locale locale = localeMap.get(language_box.getValue());
            resourceBundle = ResourceBundle.getBundle(App.BUNDLE, locale);
            resourceFactory.setResources(ResourceBundle.getBundle(App.BUNDLE, locale));
            languagePattern = pattern.get(language_box.getValue());
            bindGuiLanguage();
            refreshTable();
            print_unique_owner_but.setFont(Font.font("System", FontWeight.BOLD, 18));
            add_if_min_but.setFont(Font.font("System", FontWeight.BOLD, 24));
            remove_lower_but.setFont(Font.font("System", FontWeight.BOLD, 18));
            if (language_box.getValue().equals("Русский")) {
                print_unique_owner_but.setFont(Font.font("System", FontWeight.BOLD, 16));
                add_if_min_but.setFont(Font.font("System", FontWeight.BOLD, 22));
            } else if (language_box.getValue().equals("Nederlands")) {
            } else if (language_box.getValue().equals("Ελληνικά")) {
            } else if (language_box.getValue().equals("Español")) {
            }
        });
    }

    private void bindGuiLanguage() {
        commands_text.textProperty().bind(resourceFactory.getStringBinding("commands"));
        add_an_element_text.textProperty().bind(resourceFactory.getStringBinding("add_an_element"));
        tabMain.textProperty().bind(resourceFactory.getStringBinding("main_tab"));
        tabMap.textProperty().bind(resourceFactory.getStringBinding("map_tab"));
        collection_map_text.textProperty().bind(resourceFactory.getStringBinding("collection_map"));
        id_column.textProperty().bind(resourceFactory.getStringBinding("IdColumn"));
        print_unique_owner_but.textProperty().bind(resourceFactory.getStringBinding("PrintUniqueOwnerButton"));
        exit_but.textProperty().bind(resourceFactory.getStringBinding("ExitButton"));
        execute_script_but.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButton"));
        update_id_but.textProperty().bind(resourceFactory.getStringBinding("UpdateIdButton"));
        add_if_min_but.textProperty().bind(resourceFactory.getStringBinding("AddIfMinButton"));
        remove_lower_but.textProperty().bind(resourceFactory.getStringBinding("RemoveLowerButton"));
        remove_greater_but.textProperty().bind(resourceFactory.getStringBinding("RemoveGreaterButton"));
        name_column.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        coordinateX_column.textProperty().bind(resourceFactory.getStringBinding("CoordinateXColumn"));
        coordinateY_column.textProperty().bind(resourceFactory.getStringBinding("CoordinateYColumn"));
        price_column.textProperty().bind(resourceFactory.getStringBinding("PriceColumn"));
        date_column.textProperty().bind(resourceFactory.getStringBinding("DateColumn"));
        manufacture_cost_column.textProperty().bind(resourceFactory.getStringBinding("ManufactureCostColumn"));
        unit_of_measure_column.textProperty().bind(resourceFactory.getStringBinding("UnitOfMeasureColumn"));
        p_name_column.textProperty().bind(resourceFactory.getStringBinding("PersonNameColumn"));
        p_birthday_column.textProperty().bind(resourceFactory.getStringBinding("PersonBirthdayColumn"));
        p_weight_column.textProperty().bind(resourceFactory.getStringBinding("PersonWeightColumn"));
        p_passport_id_column.textProperty().bind(resourceFactory.getStringBinding("PersonPassportIdColumn"));
        user_login_column.textProperty().bind(resourceFactory.getStringBinding("UserLoginColumn"));
        info_but.textProperty().bind(resourceFactory.getStringBinding("InfoButton"));
        remove_by_id_but.textProperty().bind(resourceFactory.getStringBinding("RemoveByIdButton"));
        clear_but.textProperty().bind(resourceFactory.getStringBinding("ClearButton"));
        add_but.textProperty().bind(resourceFactory.getStringBinding("addButton"));
    }

    public TableColumn<Product, ?> getNameColumn() {
        return name_column;
    }

    public void refreshTable() {
        collection_table.refresh();
        canvas_plane.toString();
    }

    public void setClient(Client client) {
        this.client = client;
        collection_table.setItems(client.getProductManager().getCollection());
        client.getProductManager().setController(this);
        client.setResourceFactory(resourceFactory);
    }

    public void setUsername(String username) {
        loginuser_field.setText(username);
        loginuser_field1.setText(username);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setApp(App a) {
        app = a;
    }

    @FXML
    private void removeByIdOnAction() {
        Product product = collection_table.getSelectionModel().getSelectedItem();
        if (product != null) {
            proccessAction(new CommandMsg("remove_by_id").setArgument(Long.toString(product.getId())));
        } else {
            proccessAction(new CommandMsg("remove_by_id").setArgument(Long.toString(idInField)));
        }
    }

    @FXML
    public void printUniqueOwnerOnAction() {
        proccessAction(new CommandMsg("print_unique_owner"));
    }

    @FXML
    public void executeScriptOnAction() {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) return;
        proccessActionScript(selectedFile);
    }

    @FXML
    public void updateIdOnAction() {
        Product product = collection_table.getSelectionModel().getSelectedItem();
        if (product != null) {
            proccessAction(new CommandMsg("update").setArgument(Long.toString(product.getId())).setProduct(readProduct()));
        } else {
            proccessAction(new CommandMsg("update").setArgument(Long.toString(idInField)));
        }
    }

    @FXML
    public void addIfMinOnAction() {
        Product product = readProduct();
        if (product != null) {
            proccessAction(new CommandMsg("add_if_min").setProduct(product));
        }
    }

    @FXML
    public void removeLowerOnAction() {
        Product product = collection_table.getSelectionModel().getSelectedItem();
        if (product != null) {
            proccessAction(new CommandMsg("remove_lower").setArgument(Long.toString(product.getId())));
        } else {
            proccessAction(new CommandMsg("remove_lower").setArgument(Long.toString(idInField)));
        }

    }

    @FXML
    public void removeGreaterOnAction() {
        Product product = collection_table.getSelectionModel().getSelectedItem();
        if (product != null) {
            proccessAction(new CommandMsg("remove_greater").setArgument(Long.toString(product.getId())));
        } else {
            proccessAction(new CommandMsg("remove_greater").setArgument(Long.toString(idInField)));
        }
    }

    @FXML
    public void exitOnAction() {
        FXMLLoader loginWindowLoader = new FXMLLoader();
        loginWindowLoader.setLocation(getClass().getResource("../controllers/login.fxml"));
        try {
            loginWindowLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent signUpWindowRootNode = loginWindowLoader.getRoot();
        Scene signUpWindowScene = new Scene(signUpWindowRootNode);
        ControllerLogin controllerLogin = loginWindowLoader.getController();
        controllerLogin.setApp(app);
        controllerLogin.setClient(client);
        primaryStage.setScene(signUpWindowScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest((e) -> {
            print("Main window close!!!");
            client.close();
        });
        flag = true;
        ProductObservableManager.collection.clear();
    }


    @FXML
    public void infoOnAction() {
        proccessAction(new CommandMsg("info"));
    }

    @FXML
    public void clearOnAction() {
        proccessAction(new CommandMsg("clear"));
    }

    @FXML
    public void addOnAction() {
        Product product = readProduct();
        proccessAction(new CommandMsg("add").setProduct(product));
    }

    public Void proccessAction(Request request) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        client.getCommandManager().runCommand(request);
                        return null;
                    }
                };
            }
        };
        service.start();
        return null;
    }

    public Void proccessActionScript(File selectedFile) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Response msg = null;
                        try {
                            msg = client.getCommandManager().runFile(selectedFile);
                        } catch (FileException | InvalidDataException | ConnectionException | CollectionException | CommandException e) {
                            app.getOutputter().error(e.getMessage());
                        }
                        if (msg != null) {
                            System.out.println(msg.getMessage());
                            if (msg.getStatus() == Response.Status.FINE) app.getOutputter().info(msg.getMessage());
                            else if (msg.getStatus() == Response.Status.ERROR)
                                app.getOutputter().error(msg.getMessage());
                        }
                        return null;
                    }
                };
            }
        };
        service.start();
        return null;
    }

    //работа с полями интерфейса
    public String readName() throws EmptyStringException, StringException {
        String s = name_field.getText();
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(s);
        if (s.equals("")) {
            throw new EmptyStringException("name empty exception");
        }
        if (matcher.find()) throw new StringException();
        return s;
    }

    public long readX() throws InvalidNumberException {
        Long x;
        try {
            x = Long.parseLong(coordinateX_field.getText());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException("x coordinate format exception");
        }
        if (x > 658) {
            throw new InvalidNumberException("x_must_be_less_than_658");
        }
        return x;
    }

    public Integer readY() throws InvalidNumberException {
        Integer y;
        try {
            y = Integer.parseInt(coordinateY_field.getText());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException("y_coordinate_format_exception");
        }
        if (y > 211) throw new InvalidNumberException("y must be less than 211");
        return y;
    }

    public Coordinates readCoordinates() throws InvalidNumberException {
        long x = readX();
        Integer y = readY();
        Coordinates coord = new Coordinates(x, y);
        return coord;
    }

    public Integer readPrice() throws InvalidNumberException {
        Integer s;
        try {
            s = Integer.parseInt(price_field.getText());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        if (s <= 0) throw new InvalidNumberException("price must be greater than 0");
        return s;
    }

    public float readManufactureCost() throws InvalidNumberException {
        float cost;
        try {
            cost = Float.parseFloat(manufacture_cost_field.getText());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        return cost;
    }

    public UnitOfMeasure readUnitOfMeasure() throws InvalidEnumException {
        String s = unit_of_measure_field.getText();
        try {
            return UnitOfMeasure.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumException();
        }
    }

    public String readNamePerson() throws EmptyStringException, StringException {
        String s = person_name_field.getText();
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(s);
        if (s.equals("")) {
            throw new EmptyStringException();
        }
        if (matcher.find()) throw new StringException();
        return s;
    }

    public LocalDateTime readBirthday() throws InvalidFormatDateException {
        String buf = person_birthday_field.getText();
        String s = "";
        if (buf.equals("")) {
            return parseLocalDate(s);
        } else {
            return parseLocalDate(buf);
        }
    }

    public float readWeight() throws InvalidNumberException {
        float weight;
        try {
            weight = Float.parseFloat(person_weight_field.getText());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        if (weight < 0) throw new InvalidNumberException("must be greater than 0");
        return weight;
    }

    public String readPassportId() throws InvalidDataException {
        String s = person_passport_id_field.getText();
        if (s.length() < 7) {
            throw new InvalidDataException("string length must be greater than 7");
        }
        return s;
    }

    public Person readOwner() throws InvalidDataException {
        String namePerson = readNamePerson();
        LocalDateTime birthday = readBirthday();
        float weight = readWeight();
        String passportID = readPassportId();
        return new Person(namePerson, birthday, weight, passportID);
    }

    public Product readProduct() {
        try {
            String name = readName();
            Coordinates coords = readCoordinates();
            Integer price = readPrice();
            float manufactureCost = readManufactureCost();
            UnitOfMeasure unitOfMeasure = readUnitOfMeasure();
            Person owner = readOwner();
            product = new Product(name, coords, price, manufactureCost, unitOfMeasure, owner);
        } catch (EmptyStringException | StringException | InvalidNumberException | InvalidEnumException e) {
            app.getOutputter().error(e.getMessage());
            app.getOutputManager().error(e.getMessage());
        } catch (InvalidDataException e) {
            app.getOutputter().error(e.getMessage());
            app.getOutputManager().error(e.getMessage());
        }
        return product;
    }

    public void setProduct(Product product) {
        name_field.setText(product.getName());
        coordinateX_field.setText(product.getCoordinates().getX() + "");
        coordinateY_field.setText(product.getCoordinates().getY() + "");
        price_field.setText(product.getPrice() + "");
        manufacture_cost_field.setText(product.getManufactureCost() + "");
        unit_of_measure_field.setText(product.getUnitOfMeasure() + "");
        person_name_field.setText(product.getOwner().getPersonName());
        person_birthday_field.setText(product.getOwner().getBirthday() + "");
        person_weight_field.setText(product.getOwner().getWeight() + "");
        person_passport_id_field.setText(product.getOwner().getPassportID());
    }

    public void refreshCanvas(ObservableList<Product> collection, Collection<Product> changes, CollectionOperation op) {
        for (Product product : changes) {
            if (!userColorMap.containsKey(product.getUserLogin())) {
                userColorMap.put(product.getUserLogin(), Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            }
            if (op == CollectionOperation.ADD) {
                addToCanvas(product);
            } else if (op == CollectionOperation.REMOVE) {
                removeFromCanvas(product.getId());
            } else if (op == CollectionOperation.UPDATE) {
                removeFromCanvas(product.getId());
                addToCanvas(product);
            }
        }

    }

    private void removeFromCanvas(Long id) {
        canvas_plane.getChildren().remove(circleMap.get(id));
        circleMap.remove(id);
    }


    public void addToCanvas(Product pr) {
        double size = Math.min(pr.getPrice() / 100, 15);
        if (size < 6) size = 6;
        Circle circle = new Circle(size, userColorMap.get(pr.getUserLogin()));
        circle.setCenterX(pr.getCoordinates().getX() * 1.6);
        circle.setCenterY(pr.getCoordinates().getY() * 2.1);
        circle.setStroke(Color.BLACK);

        circle.setOnMouseClicked(event -> {
            Group group = new Group();
            Button button = new Button(resourceBundle.getString("edit"));
            button.setLayoutX(370);
            button.setLayoutY(250);
            Stage stage = new Stage();
            button.setOnAction(event1 -> {
                this.name_field.setText(pr.getName());
                this.coordinateX_field.setText(pr.getCoordinates().getX() + "");
                this.coordinateY_field.setText(pr.getCoordinates().getY() + "");
                this.price_field.setText(pr.getPrice().toString());
                this.manufacture_cost_field.setText(pr.getManufactureCost() + "");
                this.unit_of_measure_field.setText(pr.getUnitOfMeasure().toString());
                this.person_name_field.setText(pr.getOwner().getPersonName());
                this.person_birthday_field.setText(DateConvertor.dateToString1(pr.getOwner().getBirthday()));
                this.person_weight_field.setText(pr.getOwner().getWeight() + "");
                this.person_passport_id_field.setText(pr.getOwner().getPassportID());
                idInField = pr.getId();
                stage.close();
                this.tabPane.getSelectionModel().selectFirst();
            });
            Label label = new Label(pr.toString());
            group.getChildren().add(button);
            group.getChildren().add(label);
            Scene scene = new Scene(group);
            stage.setScene(scene);
            stage.setWidth(900);
            stage.setHeight(350);
            stage.show();
        });
        circleMap.put(pr.getId(), circle);
        canvas_plane.getChildren().add(circle);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), circle);
        fadeTransition.setFromValue(0.0f);
        fadeTransition.setToValue(1.0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), circle);
        translateTransition.setFromY(circle.getCenterY() * (-1) - 20);
        translateTransition.setToY(0);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(2000), circle);
        scaleTransition.setFromY(2f);
        scaleTransition.setFromX(2f);
        scaleTransition.setToY(1f);
        scaleTransition.setToX(1f);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                fadeTransition,
                translateTransition,
                scaleTransition
        );
        parallelTransition.play();

    }

    private void filter_view() {
        filtr_view.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.setTitle("Filter");
            FXMLLoader filterWindowLoader = new FXMLLoader();
            filterWindowLoader.setLocation(getClass().getResource("../controllers/filter.fxml"));
            try {
                filterWindowLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent filterRootNode = filterWindowLoader.getRoot();
            Scene filterWindowScene = new Scene(filterRootNode);
            FilterController filterController = filterWindowLoader.getController();
            filterController.setCollection(client.getProductManager().getCollection());
            filterController.setMainWindowController(this);
            filterController.setApp(app);
            stage.setScene(filterWindowScene);
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest((e) -> {
                collection_table.setItems(client.getProductManager().getCollection());
            });
        });
    }

    private void help_button() {
        help_but.setOnMouseClicked(event -> {
            Group group = new Group();
            Stage stage = new Stage();
            stage.setTitle("Help");
            Label label = new Label("help : вывести справку по доступным командам\n" +
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                    "add {element} : добавить новый элемент в коллекцию\n" +
                    "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                    "remove_by_id id : удалить элемент из коллекции по его id\n" +
                    "clear : очистить коллекцию\n" +
                    "save : сохранить коллекцию в файл\n" +
                    "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                    "exit : завершить программу (без сохранения в файл)\n" +
                    "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                    "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                    "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                    "filter_less_than_manufacture_cost manufactureCost : вывести элементы, значение поля manufactureCost которых меньше заданного\n" +
                    "print_unique_owner : вывести уникальные значения поля owner всех элементов в коллекции\n");
            group.getChildren().add(label);
            Scene scene = new Scene(group);
            stage.setScene(scene);
            stage.setWidth(1400);
            stage.setHeight(400);
            stage.show();
        });
    }

    public ObservableResourceFactory getResourceFactory() {
        return resourceFactory;
    }
}



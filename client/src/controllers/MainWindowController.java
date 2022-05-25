package controllers;

public class MainWindowController {

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




    private Client client;
    private Stage primaryStage;
    private static ObservableResourceFactory resourceFactory;
    private Map<String, Locale> localeMap;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Product, Long> table_id;

    @FXML
    private TableColumn<Product, String> table_name;

    @FXML
    private TableColumn<Product, Long> table_x;

    @FXML
    private TableColumn<Product, Integer> table_y;

    @FXML
    private TableColumn<Product, LocalDate> table_date;

    @FXML
    private TableColumn<Product, Integer> table_price;

    @FXML
    private TableColumn<Product, Float> table_manufacture_cost;

    @FXML
    private TableColumn<Product, UnitOfMeasure> table_unit_of_measure;

    @FXML
    private TableColumn<Product, String> table_p_name;

    @FXML
    private TableColumn<Product, LocalDateTime> table_p_birthday;

    @FXML
    private TableColumn<Product, Float> table_p_weight;

    @FXML
    private TableColumn<Product, String> table_p_passport_id;

    @FXML
    private TableColumn<Product, String> table_user_login;

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
    private TextField person_weighth_field;

    @FXML
    private TextField person_passport_if_field;

    @FXML
    private Label loginuser_field;

    private Product product;
    private Product resultProduct;


    @FXML
    void initialize() {
        initializeTable();
        //productTableView.setItems(FXCollections.observableArrayList(Position));
        /*productTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            int id = productTableView.getSelectionModel().getSelectedIndex();

        }

        ));*/
    }


    public void setProduct(Product product) {
        table_name.setText(product.getName());
        table_x.setText(product.getCoordinates().getX()+"");
        table_y.setText(product.getCoordinates().getY()+"");
        table_date.setText(product.getCreationDate()+"");
        table_price.setText(product.getPrice()+"");
        table_manufacture_cost.setText(product.getManufactureCost()+"");
        table_unit_of_measure.setText(product.getUnitOfMeasure()+"");
        table_p_name.setText(product.getOwner().getPersonName()+"");
        table_p_birthday.setText(product.getOwner().getBirthday()+"");
        table_p_weight.setText(product.getOwner().getWeight()+"");
        table_p_passport_id.setText(product.getOwner().getPassportID()+"");
        table_user_login.setText(product.getUserLogin());
    }

    public void refreshTable() {
        productTableView.refresh();
    }


    /**
     * initialize table
     */
    private void initializeTable() {
        table_id.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        table_name.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        table_x.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
        table_y.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        table_date.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
        table_price.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        table_manufacture_cost.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getManufactureCost()));
        table_unit_of_measure.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUnitOfMeasure()));
        table_p_name.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getPersonName()));
        table_p_birthday.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getBirthday()));
        table_p_weight.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getWeight()));
        table_p_passport_id.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getPassportID()));
        table_user_login.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUserLogin()));
    }

    /*private void bindGuiLanguage() {
        resourceFactory.setResources(ResourceBundle.getBundle("bundles.gui",));
    }*/

    /*public void initFilter() {
        tableFilter = new TableFilter
    }*/


    /*public void setClient(Client client) {
        this.client = client;
        productTableView.setItems(client.getProductManager().getCollection());
        client.getProductManager().setController(this);

    }*/

    public void setUserName(String userName) {
        loginuser_field.setText(userName);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void initLangs(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        for (String localeName : localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceFactory.getResources().getLocale()))
               language_box.getSelectionModel().select(localeName);
        }
        //language_box.setValue("English");
//        if (language_box.getSelectionModel().getSelectedItem().isEmpty()) {
//            language_box.getSelectionModel().select("English");
//        }
        language_box.setOnAction((event) -> {
            Locale locale = localeMap.get(language_box.getValue());
            resourceFactory.setResources(ResourceBundle.getBundle
                    (App.BUNDLE, locale));
            //DateConverter.setPattern(resourceFactory.getRawString("DateFormat"));
            collection_table.refresh();
        });
    }

    private void bindGuiLanguage() {
        //resourceFactory.setResources(ResourceBundle.getBundle(App.BUNDLE, localeMap.get(language_box.getSelectionModel().getSelectedItem())));
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
        //tableFilter.updateFilters();
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
            client.getCommandManager().runCommand(new CommandMsg("remove_by_id").setArgument(Long.toString(product.getId())));
        } else {
            client.getCommandManager().runCommand(new CommandMsg("remove_by_id").setArgument(Long.toString(idInField)));
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
        Response msg = null;
        try {
            msg = client.getCommandManager().runFile(selectedFile);
        } catch (FileException | InvalidDataException | ConnectionException | CollectionException | CommandException e) {
            //app.getOutputManager().error(e.getMessage());
            app.getOutputter().error(e.getMessage());
        }
        if (msg != null) {
            System.out.println(msg.getMessage());
            if (msg.getStatus() == Response.Status.FINE) app.getOutputter().info(msg.getMessage());
                //if (msg.getStatus() == Response.Status.FINE) app.getOutputManager().info(msg.getMessage());
                //else if (msg.getStatus() == Response.Status.ERROR) app.getOutputManager().error(msg.getMessage());
            else if (msg.getStatus() == Response.Status.ERROR) app.getOutputter().error(msg.getMessage());
        }
    }

    @FXML
    public void updateIdOnAction(ActionEvent actionEvent) {
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
            client.getCommandManager().runCommand(new CommandMsg("remove_lower").setArgument(Long.toString(product.getId())));
        } else {
            client.getCommandManager().runCommand(new CommandMsg("remove_lower").setArgument(Long.toString(idInField)));
        }

    }

    @FXML
    public void removeGreaterOnAction() {
        Product product = collection_table.getSelectionModel().getSelectedItem();
        if (product != null) {
            client.getCommandManager().runCommand(new CommandMsg("remove_greater").setArgument(Long.toString(product.getId())));
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
    }

    @FXML
    public void infoOnAction() {
        proccessAction(new CommandMsg("info"));
    }

    @FXML
    public void clearOnAction() {
        client.getCommandManager().runCommand(new CommandMsg("clear"));
    }

    @FXML
    public void addOnAction() {
        if (flag) {
            Product product = readProduct();
            proccessAction(new CommandMsg("add").setProduct(product));
        }
    }

    public Response proccessAction(Request request) {
        Response response = client.getCommandManager().runCommand(request);
        String msg = response.getMessage();
        return response;
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
            flag = true;
        } catch (EmptyStringException | StringException | InvalidNumberException | InvalidEnumException e) {
            //app.getOutputManager().error(e.getMessage());
            app.getOutputter().error(e.getMessage());
            app.getOutputManager().error(e.getMessage());

        } catch (InvalidDataException e) {
            //app.getOutputManager().error(e.getMessage());
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

    long rand = 1821L;
    Random random = new Random(rand);

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
            Button button = new Button("Редактировать");
            button.setLayoutX(370);
            button.setLayoutY(250);
            Stage stage = new Stage();
            Label label = new Label(pr.toString());
            group.getChildren().add(button);
            group.getChildren().add(label);
            Scene scene = new Scene(group);
            stage.setScene(scene);
            stage.setWidth(900);
            stage.setHeight(350);
            stage.show();
        });
        canvas_plane.getChildren().add(circle);

    }

    private void filter_view() {
        filtr_view.setOnMouseClicked(event -> {
            Stage stage = new Stage();
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

    public ObservableResourceFactory getResourceFactory() {
        return resourceFactory;
    }

    private void initCanvas() {
        ZoomOperator zoomOperator = new ZoomOperator();
        canvas_plane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double zoomFactor = 1.5;
                if (event.getDeltaY() <= 0) {
                    zoomFactor = 1 / zoomFactor;
                }

                double x = 20;
                double y = 20;
                if ((event.getDeltaY() <= 0 && (zoomOperator.getBounds().getHeight() <= 100 || zoomOperator.getBounds().getWidth() <= 100)))
                    return;
                zoomOperator.zoom(canvas_plane, zoomFactor, x, y);
            }
        });
        //zoomOperator.draggable(canvas_plane);
        canvas_plane.setMinHeight(20);
        canvas_plane.setMinWidth(20);
    }
}



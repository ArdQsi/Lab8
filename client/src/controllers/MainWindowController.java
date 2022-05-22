package controllers;

public class MainWindowController {

    private App app;
    private Tooltip shapeTooltip;
    private TableFilter<Product> tableFilter;
    private Client client;
    private Stage askStage;
    private Stage primaryStage;
    ;
    private Map<Integer, Text> textMap;
    private ObservableResourceFactory resourceFactory;
    private Map<String, Locale> localeMap;
    Product product;



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
        if (language_box.getSelectionModel().getSelectedItem().isEmpty()){
            if(localeMap.containsValue(Locale.getDefault())) language_box.getSelectionModel().select(MapUtils.getKeyByValue(localeMap,Locale.getDefault()));
            else language_box.getSelectionModel().selectFirst();
        }

        language_box.setOnAction((event) ->{
            Locale locale = localeMap.get(language_box.getValue());
            resourceFactory.setResources(ResourceBundle.getBundle
                    (App.BUNDLE, locale));
            //DateConverter.setPattern(resourceFactory.getRawString("DateFormat"));
            collection_table.refresh();
        });
        bindGuiLanguage();
    }

    private void bindGuiLanguage() {
        resourceFactory.setResources(ResourceBundle.getBundle(App.BUNDLE, localeMap.get(language_box.getSelectionModel().getSelectedItem())));
        id_column.textProperty().bind(resourceFactory.getStringBinding("IdColumn"));
        name_column.textProperty().bind(resourceFactory.getStringBinding("name_column"));
       coordinateX_column.textProperty().bind(resourceFactory.getStringBinding("coordinateX_column"));
       coordinateY_column.textProperty().bind(resourceFactory.getStringBinding("coordinateY_column"));
       price_column.textProperty().bind(resourceFactory.getStringBinding("price_column"));
    }

    public void initFilter() {
        tableFilter = new TableFilter<Product>(collection_table, client.getProductManager().getCollection(), resourceFactory);
    }

    public TableFilter<Product> getFilter() {
        return tableFilter;
    }

    public TableColumn<Product, ?> getNameColumn() {
        return name_column;
    }

    public void refreshTable() {
        collection_table.refresh();
        tableFilter.updateFilters();
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
        if (product != null)
            client.getCommandManager().runCommand(new CommandMsg("remove_by_id").setArgument(Long.toString(product.getId())));
        collection_table.refresh();
    }

    @FXML
    public void printUniqueOwnerOnAction() {
        Label start = new Label();
        Stage stage = new Stage();
        Label nameLabel = new Label();
        TextField textField = new TextField();
        Button button = new Button();
        button.textProperty().bind(resourceFactory.getStringBinding("Enter"));
        button.setOnAction(event -> {
            String arg = textField.getText();
            if (arg!=null && !arg.equals("")) {
                proccessAction(new CommandMsg("print_unique_owner"));
            }
        });


    }

    @FXML
    public void executeScriptOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void updateIdOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void addIfMinOnAction(ActionEvent actionEvent) {

            Product product = readProduct();
            if (product!= null) {
                proccessAction(new CommandMsg("add_if_min").setProduct(product));
            }


    }

    @FXML
    public void removeLowerOnAction(ActionEvent actionEvent) {

        //proccessAction(new CommandMsg("remove_lower"));
    }

    @FXML
    public void removeGreaterOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void exitOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void infoOnAction() {
//        Label start = new Label();
//        Stage stage = new Stage();
//        proccessAction(new CommandMsg("info"));
    }

    @FXML
    public void clearOnAction() {
        client.getCommandManager().runCommand(new CommandMsg("clear"));
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        proccessAction(new CommandMsg("add").setProduct(readProduct()));
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
            throw new EmptyStringException("name_empty_exception");
        }
        if (matcher.find()) throw new StringException();
        return s;
    }

    public long readX() throws InvalidNumberException {
        Long x;
        try {
            x = Long.parseLong(coordinateX_field.getText());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException("x_coordinate_format_exception");
        }
        if (x > 658) throw new InvalidNumberException("x_must_be_less_than_658");
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
            app.getOutputManager().error(e.getMessage());
        } catch (InvalidDataException e) {
            app.getOutputManager().error(e.getMessage());
        }
        return product;
    }

    public void fillMap(){
        int k = 8;
        for(Product pr: ProductObservableManager.produsts){
            System.out.println(ProductObservableManager.produsts.size());
            if(pr.getPrice()<100){
                k=8;
            }
            if(pr.getPrice()>100&&pr.getPrice()<300)
            {
                k=15;
            }
            if(pr.getPrice()>300)
            {
                k=20;
            }
            Circle circle = new Circle(pr.getCoordinates().getY(),(int)(pr.getCoordinates().getX()),k,Color.rgb((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)));
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
    }
}


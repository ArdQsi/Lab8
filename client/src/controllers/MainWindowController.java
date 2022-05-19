package controllers;

public class MainWindowController {

    private Client client;
    private Stage primaryStage;
    private static ObservableResourceFactory resourceFactory;
    


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
    private TableColumn<Product, String> table_p_passport_ip;

    @FXML
    private TableColumn<?, ?> table_user_login;

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

    @FXML
    void initialize() {
        //initializeTable();
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
        table_p_passport_ip.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getPassportID()));
    }

    /*private void bindGuiLanguage() {
        resourceFactory.setResources(ResourceBundle.getBundle());
    }*/


    public void setClient(Client client) {
        this.client = client;
        productTableView.setItems(client.getProductManager().getCollection());
        client.getProductManager().setController(this);
    }

    public void setUserName(String userName) {
        loginuser_field.setText(userName);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



}


package controllers;

public class MainWindowController {

    private App app;
    private Tooltip shapeTooltip;
    private TableFilter<Product> tableFilter;
    private Client client;
    private Stage askStage;
    private Stage primaryStage;;
    private Map<Integer, Text> textMap;
    private ObservableResourceFactory resourceFactory;
    private Map<String, Locale> localeMap;


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

    public void initFilter(){
        tableFilter = new TableFilter<Product>(collection_table,client.getProductManager().getCollection(),resourceFactory);
    }

    public TableFilter<Product> getFilter(){
        return tableFilter;
    }
    public TableColumn<Product,?> getNameColumn(){
        return name_column;
    }

    public void refreshTable(){
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

    public void setApp(App a){
        app = a;
    }
}


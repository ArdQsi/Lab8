package database;

import auth.User;
import auth.UserManager;
import collection.ProductDequeManager;
import data.Coordinates;
import data.Person;
import data.Product;
import data.UnitOfMeasure;
import exceptions.CollectionException;
import exceptions.DatabaseException;
import exceptions.InvalidDataException;
import exceptions.InvalidEnumException;
import log.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDatabaseManager extends ProductDequeManager {
    private final static String INSERT_PRODUCT_QUERY = "INSERT INTO PRODUCTS (name, coordinates_x, coordinates_y," +
            "creation_date, price, manufacture_cost, unit_of_measure, person_name, person_birthday, person_weight, " +
            "person_passport_id, user_login) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id;";
    private final DatabaseHandler databaseHandler;
    private final UserManager userManager;

    public ProductDatabaseManager(DatabaseHandler databaseHandler, UserManager userManager) {
        super();
        this.databaseHandler = databaseHandler;
        this.userManager = userManager;
        create();
    }


    private void create() throws DatabaseException {
        String create = "CREATE TABLE IF NOT EXISTS PRODUCTS (" +
                "id SERIAL PRIMARY KEY CHECK (id>0)," +
                "name TEXT NOT NULL CHECK (name <> '')," +
                "coordinates_x BIGINT NOT NULL CHECK (coordinates_x <= 658)," +
                "coordinates_y INT NOT NULL CHECK (coordinates_y <= 211)," +
                "creation_date TEXT NOT NULL," +
                "price INT NOT NULL CHECK (price>0)," +
                "manufacture_cost FLOAT," +
                "unit_of_measure TEXT NOT NULL," +
                "person_name TEXT NOT NULL CHECK (person_name <> '')," +
                "person_birthday TEXT," +
                "person_weight FLOAT CHECK (person_weight > 0)," +
                "person_passport_id TEXT NOT NULL CHECK (LENGTH(person_passport_id)>=7)," +
                "user_login TEXT NOT NULL REFERENCES USERS(LOGIN));";
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(create)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("cannot create product database");
        }
    }


    @Override
    public long generateNextId() {
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement("SELECT nextval('id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            return 1;
        }
    }

    private void setProduct(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setLong(2, product.getCoordinates().getX());
        preparedStatement.setInt(3, product.getCoordinates().getY());
        preparedStatement.setString(4, product.getCreationDate().toString());
        preparedStatement.setInt(5, product.getPrice());
        preparedStatement.setFloat(6, product.getManufactureCost());
        preparedStatement.setString(7, product.getUnitOfMeasure().toString());
        preparedStatement.setString(8, product.getOwner().getPersonName());
        preparedStatement.setString(9, product.getOwner().getBirthday().toString());
        preparedStatement.setFloat(10, product.getOwner().getWeight());
        preparedStatement.setString(11, product.getOwner().getPassportID());

        preparedStatement.setString(12, product.getUserLogin());
    }

    private Product getProduct(ResultSet resultSet) throws SQLException, InvalidDataException {
        Coordinates coordinates = new Coordinates(resultSet.getLong("coordinates_x"), resultSet.getInt("coordinates_y"));
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        LocalDate creationDate = LocalDate.parse(resultSet.getString("creation_date"));

        Integer price = resultSet.getInt("price");
        float manufactureCost = resultSet.getFloat("manufacture_cost");
        UnitOfMeasure unitOfMeasure;
        try {
            unitOfMeasure = UnitOfMeasure.valueOf(resultSet.getString("unit_of_measure"));
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumException();
        }
        String personName = resultSet.getString("person_name");
        LocalDateTime birthday = LocalDateTime.parse(resultSet.getString("person_birthday"));
        float weight = resultSet.getFloat("person_weight");
        String passportID = resultSet.getString("person_passport_id");
        Person owner = new Person(personName, birthday, weight, passportID);
        Product product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner);
        product.setCreationDate(creationDate);
        product.setId(id);
        product.setUserLogin(resultSet.getString("user_login"));
        if (!userManager.isPresent(product.getUserLogin())) throw new DatabaseException("user not found");
        return product;
    }

    @Override
    public void add(Product product) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(INSERT_PRODUCT_QUERY, true)) {
            setProduct(preparedStatement, product);
            if (preparedStatement.executeUpdate() == 0) throw new DatabaseException();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) throw new DatabaseException();
            product.setId(resultSet.getLong(resultSet.findColumn("id")));
            databaseHandler.commit();
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
            databaseHandler.rollback();
            throw new DatabaseException("cannot add to database");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.addWithoutIdGeneration(product);
    }

    @Override
    public void deserializeCollection() {
        if (!getCollection().isEmpty()) super.clear();
        String query = "SELECT * FROM PRODUCTS";
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int damageElements = 0;
            while (resultSet.next()) {
                try {
                    Product product = getProduct(resultSet);
                    if (!product.validate()) throw new InvalidDataException("element is damaged");
                    super.addWithoutIdGeneration(product);
                } catch (SQLException e) {
                    damageElements += 1;
                    e.printStackTrace();
                } catch (InvalidDataException e) {
                    e.printStackTrace();
                }

            }
            if (super.getCollection().isEmpty()) throw new DatabaseException("nothing to load");
            if (damageElements == 0) Log.logger.info("collection successfully loaded");
            else Log.logger.error(damageElements + " elements are damaged");
        } catch (SQLException e) {
            throw new DatabaseException("cannot load");
        }
    }

    @Override
    public void removeById(long id) {
        String query = "DELETE FROM PRODUCTS WHERE id = ?;";
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("cannot remove from database");
        }
        super.removeById(id);
    }


    @Override
    public void updateById(long id, Product product) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        String sql = "UPDATE PRODUCTS SET " +
                "name =?," +
                "coordinates_x=?," +
                "coordinates_y=?," +
                "creation_date=?," +
                "price=?," +
                "manufacture_cost=?," +
                "unit_of_measure=?," +
                "person_name=?," +
                "person_birthday=?," +
                "person_weight=?," +
                "person_passport_id=?," +
                "user_login=? " +
                "WHERE id=?";
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(sql)) {
            setProduct(preparedStatement, product);
            preparedStatement.setLong(13, id);
            preparedStatement.execute();
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot update product " + product.getId() + " in database");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.updateById(id, product);
    }

    public List<Product> removeGreater(long id, User user) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        Set<Long> ids = new HashSet<>();
        String query = "DELETE FROM PRODUCTS WHERE id>? and user_login = ? RETURNING id;";
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idp = resultSet.getLong(resultSet.findColumn("id"));
                ids.add(idp);
            }
        } catch (SQLException | CollectionException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot clear database");
        } finally {
            databaseHandler.setNormalMode();
        }
        return removeGreater(ids);
    }

    public List<Product> removeLower(long id, User user) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        Set<Long> ids = new HashSet<>();
        String query = "DELETE FROM PRODUCTS WHERE id<? and user_login = ? RETURNING id;";
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idp = resultSet.getLong(resultSet.findColumn("id"));
                ids.add(idp);
            }
        } catch (SQLException | CollectionException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot clear database");
        } finally {
            databaseHandler.setNormalMode();
        }
        return removeLower(ids);
    }

    public Collection<Product> clear(User user) {
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        //Set<Long> ids = new HashSet<>();
        Set<Product> removed = new HashSet<>();

        String query = "DELETE FROM PRODUCTS WHERE user_login=? RETURNING id;";
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(query)) {
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                Long id = resultSet.getLong(resultSet.findColumn("id"));
//                ids.add(id);
                Long id = resultSet.getLong(1);
                removed.add(getById(id));
                removeById(id);
            }
        } catch (SQLException | CollectionException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot clear database");
        } finally {
            databaseHandler.setNormalMode();
        }
//        removeAll(ids);
        return removed;
    }

    @Override
    public void addIfMin(Product product) {
        String query = "SELECT MIN(price) FROM PRODUCTS;";
        if (getCollection().isEmpty()) {
            add(product);
            return;
        }
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (Statement getStatement = databaseHandler.getStatement();
             PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(INSERT_PRODUCT_QUERY, true)) {
            ResultSet resultSet = getStatement.executeQuery(query);
            if (!resultSet.next()) {
                throw new DatabaseException("unable to add");
            } else {

                Integer minPrice = resultSet.getInt(1);
                if (product.getPrice() > minPrice) throw new DatabaseException("unable to add, min price is " +
                        minPrice + " current price is " + product.getPrice());

                setProduct(preparedStatement, product);
                if (preparedStatement.executeUpdate() == 0) throw new DatabaseException();
                resultSet = preparedStatement.getGeneratedKeys();

                if (!resultSet.next()) throw new DatabaseException();
                product.setId(resultSet.getLong(resultSet.findColumn("id")));
                databaseHandler.commit();

            }
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot add(maybe you entered an unnecessary argument)");
        } finally {
            databaseHandler.setNormalMode();
        }
        super.addWithoutIdGeneration(product);
    }
}

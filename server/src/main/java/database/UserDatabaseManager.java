package database;

import auth.User;
import auth.UserManager;
import exceptions.DatabaseException;
import log.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDatabaseManager implements UserManager {
    private final DatabaseHandler databaseHandler;

    public UserDatabaseManager(DatabaseHandler handler) {
        databaseHandler = handler;
        create();
    }

    private void create() throws DatabaseException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS USERS" +
                "(login TEXT PRIMARY KEY, password TEXT NOT NULL);";
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (PreparedStatement statement = databaseHandler.getPreparedStatement(createTableSQL)) {
            statement.execute();
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("cannot create user database");
        } finally {
            databaseHandler.setNormalMode();
        }
    }

    public void add(User user) throws DatabaseException {
        String sql = "INSERT INTO USERS (login, password) VALUES (?, ?)";
        databaseHandler.setCommitMode();
        databaseHandler.setSavepoint();
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            databaseHandler.commit();
        } catch (SQLException e) {
            databaseHandler.rollback();
            throw new DatabaseException("something went wrong during adding new user");
        } finally {
            databaseHandler.setNormalMode();
        }
    }

    public boolean isValid(User user) {
        try {
            String password = user.getPassword();
            ResultSet resultSet = databaseHandler.getStatement().executeQuery("SELECT * FROM USERS WHERE login = '" + user.getLogin() + "'");
            while (resultSet.next())
                if (password.equals(resultSet.getString(2))) return true;
            return false;
        } catch (SQLException e) {
            Log.logger.error("Can't get user from database");
            return false;
        }
    }

    public boolean isPresent(String userName) {
        try {
            ResultSet rs = databaseHandler.getStatement().executeQuery("SELECT * FROM USERS WHERE login = '" + userName + "'");
            return rs.next();
        } catch (SQLException e) {
            Log.logger.error("cannot get user from database");
            return false;
        }
    }

    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        try (PreparedStatement preparedStatement = databaseHandler.getPreparedStatement("SELECT * FROM USERS")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                while (resultSet.next()) {
                    User user = new User(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    users.add(user);
                }
            } catch (SQLException e) {
            }
        } catch (SQLException | DatabaseException e) {
            if (users.isEmpty()) throw new DatabaseException("registered user not found");
        }
        return users;
    }

}

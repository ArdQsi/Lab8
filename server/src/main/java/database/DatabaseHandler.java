package database;

import exceptions.DatabaseException;
import log.Log;

import java.sql.*;

public class DatabaseHandler {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private final String user;
    private final String password;
    private final String url;
    private Connection connection;

    public DatabaseHandler(String url, String user, String password) {
        this.user = user;
        this.url = url;
        this.password = password;
        connectToDataBase();
    }

    private void connectToDataBase() throws DatabaseException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseException("error during connection to database");
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("data driver not found");
        }
    }

    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) throws DatabaseException {
        PreparedStatement preparedStatement;
        try {
            if (connection == null) throw new SQLException();
            int autoGenerateKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGenerateKeys);
            return preparedStatement;
        } catch (SQLException e) {
            throw new DatabaseException("error during preparation SQL statement");
        }
    }

    public Statement getStatement() throws DatabaseException {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new DatabaseException("cannot get SQL statement");
        }
    }

    public PreparedStatement getPreparedStatement(String sql) throws DatabaseException {
        return getPreparedStatement(sql, false);
    }

    public void closePreparedStatement(PreparedStatement sqlStatement) {
        if (sqlStatement == null) return;
        try {
            sqlStatement.close();
            Log.logger.info("Закрыт SQL запрос " + sqlStatement);
        } catch (SQLException e) {
            Log.logger.error("Произошла ошибка при закрытии SQL запроса " + sqlStatement);
        }
    }

    public void closeConnection() {
        if (connection == null) return;
        try {
            connection.close();
            Log.logger.info("Закрыто подключение к базе данных");
        } catch (SQLException e) {
            Log.logger.error("Произошла ошибка при прерывании соединения с базой данных");
        }
    }

    public void setCommitMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            Log.logger.error("Произошла ошибка при установлении транзакции базы данных");
        }
    }

    public void setNormalMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            Log.logger.error("Произошла ошибка при установлении нормального режима базы данных");
        }
    }

    public void commit() {
        try {
            if (connection == null) throw new SQLException();
            connection.commit();
        } catch (SQLException e) {
            Log.logger.error("Произошла ошибка при подтверждении нового состояния базы данных");
        }
    }

    public void rollback() {
        try {
            if (connection == null) throw new SQLException();
            connection.rollback();
        } catch (SQLException e) {
            Log.logger.error("Произошла ошибка при возврате исходного состояния базы данных");
        }
    }

    public void setSavepoint() {
        try {
            if (connection == null) throw new SQLException();
            connection.setSavepoint();
        } catch (SQLException e) {
            Log.logger.error("Произошла ошибка при сохранении состояния базы данных");
        }
    }
}

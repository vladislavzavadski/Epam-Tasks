package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.connection_pool.ConnectionPool;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vladislav on 15.06.16.
 */
abstract class DatabaseDAO <T> {
    private ConnectionPool connectionPool;

    protected DatabaseDAO() throws DaoException {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    protected List<T> readFromDatabase(String query, Object ... params) throws ConnectionPoolException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> result = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = initPreparedStatement(connection, query, params);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            while (resultSet.next()){
                result.add(resultSetToDomain(resultSet));

            }
            return result;
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    protected int writeToDatabase(String query, Object... params) throws ConnectionPoolException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = initPreparedStatement(connection, query, params);
            return preparedStatement.executeUpdate();
        }
        finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    abstract T resultSetToDomain(ResultSet resultSet);

    private PreparedStatement initPreparedStatement(Connection connection, String query, Object[] params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for(int i=0; i<params.length; i++){
            preparedStatement.setObject(i+1, params[i]);
        }

        return preparedStatement;
    }
}

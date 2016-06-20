package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.OrderDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.order.Order;
import by.training.pharmacy.domain.order.OrderStatus;
import by.training.pharmacy.domain.Period;
import by.training.pharmacy.domain.drug.Drug;
import by.training.pharmacy.domain.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by vladislav on 19.06.16.
 */
public class DatabaseOrderDAO extends DatabaseDAO<Order> implements OrderDAO {
    private static final String GET_ORDER_BY_ID_QUERY = "SELECT us_login, us_first_name, us_second_name, dr_id, dr_name, or_id, or_drug_count, or_drug_dosage, or_status, or_date from orders inner join users on us_login = or_client_login inner join drugs on dr_id = or_drug_id where or_id=? LIMIT 1;";
    private static final String GET_USER_ORDERS_QUERY = "SELECT us_login, us_first_name, us_second_name, dr_id, dr_name, or_id, or_drug_count, or_drug_dosage, or_status, or_date from orders inner join users on us_login = or_client_login inner join drugs on dr_id = or_drug_id where or_client_login=? LIMIT ?, ?;";
    private static final String GET_ORDERS_BY_STATUS_QUERY = "SELECT us_login, us_first_name, us_second_name, dr_id, dr_name, or_id, or_drug_count, or_drug_dosage, or_status, or_date from orders inner join users on us_login = or_client_login inner join drugs on dr_id = or_drug_id where or_status=? LIMIT ?, ?;";
    private static final String GET_ORDERS_BY_DRUG_ID_QUERY = "SELECT us_login, us_first_name, us_second_name, dr_id, dr_name, or_id, or_drug_count, or_drug_dosage, or_status, or_date from orders inner join users on us_login = or_client_login inner join drugs on dr_id = or_drug_id where or_drug_id=? LIMIT ?, ?;";
    private static final String GET_ORDERS_BY_DATE_BEFORE_QUERY = "SELECT us_login, us_first_name, us_second_name, dr_id, dr_name, or_id, or_drug_count, or_drug_dosage, or_status, or_date from orders inner join users on us_login = or_client_login inner join drugs on dr_id = or_drug_id where or_date<? LIMIT ?, ?;";
    private static final String GET_ORDERS_BY_DATE_AFTER_QUERY = "SELECT us_login, us_first_name, us_second_name, dr_id, dr_name, or_id, or_drug_count, or_drug_dosage, or_status, or_date from orders inner join users on us_login = or_client_login inner join drugs on dr_id = or_drug_id where or_date>? LIMIT ?, ?;";
    private static final String GET_ORDERS_BY_DATE_CURRENT_QUERY = "SELECT us_login, us_first_name, us_second_name, dr_id, dr_name, or_id, or_drug_count, or_drug_dosage, or_status, or_date from orders inner join users on us_login = or_client_login inner join drugs on dr_id = or_drug_id where or_date=? LIMIT ?, ?;";
    private static final String INSERT_ORDER_QUERY = "INSERT INTO orders (or_id, or_client_login, or_drug_id, or_drug_count, or_drug_dosage, or_status, or_date) VALUES(?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_ORDER_QUERY = "delete from orders where or_id=?;";
    private static final String UPDATE_ORDER_QUERY = "UPDATE orders SET or_drug_id=?, or_drug_count=?, or_drug_dosage=?, or_status=? WHERE or_id=?";

    public DatabaseOrderDAO() throws DaoException {
        super();
    }

    @Override
    public List<Order> getUserOrders(String userLogin, int limit, int startFrom) throws DaoException {
        List<Order> orders = null;
        try {
            orders = readFromDatabase(GET_USER_ORDERS_QUERY, userLogin, limit, startFrom);
            return orders;
        } catch (ConnectionPoolException|SQLException e) {

            DaoException daoException = new DaoException("Cannot load orders with or_client_login = \'"+userLogin+"\' from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.getUserOrders", daoException);
            throw daoException;
        }

    }

    @Override
    public Order getOrderById(int orderId) throws DaoException {
        try {
            List<Order> result = readFromDatabase(GET_ORDER_BY_ID_QUERY, orderId);
            if(!result.isEmpty()){
                return result.get(0);
            }
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not read order with id = \'"+orderId+"\' from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.getOrderById", daoException);
            throw daoException;
        }
        return null;
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus orderStatus, int limit, int startFrom) throws DaoException {
        List<Order> orders = null;
        try {
            orders = readFromDatabase(GET_ORDERS_BY_STATUS_QUERY, orderStatus.toString().toLowerCase(), limit, startFrom);
            return orders;
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not load orders with status = \'"+orderStatus+"\' from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.getOrdersByStatus", daoException);
            throw daoException;
        }
    }

    @Override
    public List<Order> getOrdersByDrugId(int drugId, int limit, int startFrom) throws DaoException {
        List<Order> orders = null;
        try {
            orders = readFromDatabase(GET_ORDERS_BY_DRUG_ID_QUERY, drugId, limit, startFrom);
            return orders;
        } catch (ConnectionPoolException|SQLException e) {

            DaoException daoException = new DaoException("Can not load orders with drugId = \'"+drugId+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.getOrdersByDrugId", daoException);
            throw daoException;
        }
    }

    @Override
    public List<Order> getOrdersByDate(Date date, Period period, int limit, int startFrom) throws DaoException {
        List<Order> orders = null;
        try {
            switch (period) {
                case AFTER_DATE: {
                    orders = readFromDatabase(GET_ORDERS_BY_DATE_AFTER_QUERY, date, limit, startFrom);
                    break;
                }
                case BEFORE_DATE: {
                    orders = readFromDatabase(GET_ORDERS_BY_DATE_BEFORE_QUERY, date, limit, startFrom);
                    break;
                }
                case CURRENT_DATE: {
                    orders = readFromDatabase(GET_ORDERS_BY_DATE_CURRENT_QUERY, date, limit, startFrom);
                    break;
                }
            }
        }catch (ConnectionPoolException|SQLException e){

            DaoException daoException = new DaoException("Can not load orders from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.getOrdersByDate", daoException);
            throw daoException;
        }

        return orders;
    }

    @Override
    public void updateOrder(Order order) throws DaoException {
        try {
            writeToDatabase(UPDATE_ORDER_QUERY, order.getDrug().getId(), order.getDrugCount(), order.getDrugDosage(), order.getOrderStatus().toString().toLowerCase(), order.getId());
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not update order "+order, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.updateOrder", daoException);
            throw daoException;
        }
    }



    @Override
    public void insertOrder(Order order) throws DaoException {
        try {
            writeToDatabase(INSERT_ORDER_QUERY, order.getId(), order.getClient().getLogin(), order.getDrug().getId(), order.getDrugCount(), order.getDrugDosage(), order.getOrderStatus().toString().toLowerCase(), order.getOrderDate());
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not insert new order "+ order +" to database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.insertOrder", daoException);
            throw daoException;
        }
    }

    @Override
    public void deleteOrder(int orderId) throws DaoException {
        try {
            writeToDatabase(DELETE_ORDER_QUERY, orderId);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not delete order with id = \'"+orderId+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseOrderDAO.deleteOrder", daoException);
            throw daoException;
        }
    }

    @Override
    protected Order resultSetToDomain(ResultSet resultSet) {
        Order order = new Order();

        try {
            order.setId(resultSet.getInt("or_id"));
        } catch (SQLException e) {
            order.setId(0);
        }

        try {
            order.setDrugCount(resultSet.getDouble("or_drug_count"));
        } catch (SQLException e) {
            order.setDrugCount(0.0);
        }

        try {
            order.setDrugDosage(resultSet.getShort("or_drug_dosage"));
        } catch (SQLException e) {
            order.setDrugDosage((short) 0);
        }

        try {
            order.setOrderDate(resultSet.getDate("or_date"));
        } catch (SQLException e) {
            order.setOrderDate(null);
        }

        try {
            order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("or_status").toUpperCase()));
        } catch (SQLException e) {
            order.setOrderStatus(null);
        }
        try {
            DatabaseDAO<User> userDAO = new DatabaseUserDAO();
            order.setClient(userDAO.resultSetToDomain(resultSet));
        } catch (DaoException e) {
            order.setClient(null);
        }

        try {
            DatabaseDAO<Drug> drugDAO = new DatabaseDrugDAO();
            order.setDrug(drugDAO.resultSetToDomain(resultSet));
        } catch (DaoException e) {
            order.setDrug(null);
        }
        return order;
    }
}

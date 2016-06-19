package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.UserDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.user.User;
import by.training.pharmacy.domain.user.UserDescription;
import by.training.pharmacy.domain.user.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vladislav on 14.06.16.
 */
public class DatabaseUserDAO extends DatabaseDAO<User> implements UserDAO {

    private static final String GET_USER_QUERY = "select us_first_name, us_second_name, us_image, us_mail, us_phone, us_group from users WHERE  us_login=? and us_password=md5(?);";
    private static  final String GET_USER_BY_LOGIN_QUERY = "SELECT us_first_name, us_second_name, us_group, us_mail, us_phone, us_image, sd_specialization, sd_description FROM users LEFT JOIN staff_descriptions ON users.us_login = staff_descriptions.sd_user_login WHERE us_login=?;";
    private static final String SEARCH_USER_BY_ROLE_QUERY = "SELECT us_first_name, us_second_name, us_mail, us_phone, us_image, sd_specialization, sd_description FROM users LEFT JOIN staff_descriptions ON us_login=sd_user_login WHERE us_group=? LIMIT ?, ?;";
    private static final String SEARCH_USERS_QUERY = "select us_first_name, us_second_name, us_image, us_mail, us_phone, us_group, sd_specialization, sd_description from users LEFT JOIN staff_descriptions on sd_user_login=us_login where us_first_name LIKE ? and us_second_name LIKE ? LIMIT ?, ?;";
    private static final String INSERT_USER_QUERY = "INSERT INTO users (us_login, us_password, us_first_name, us_second_name, us_group, us_image, us_mail, us_phone) VALUES (?,md5(?),?,?,?,?,?,?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET us_password=md5(?), us_first_name=?, us_second_name=?, us_image=?, us_mail=?, us_phone=? WHERE us_login=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE us_login=?";
    private static final String SEARCH_USER_BY_SPECIALIZATION_QUERY = "SELECT us_first_name, us_second_name, us_image, us_group, us_mail, us_phone sd_specialization, sd_description FROM users INNER JOIN staff_descriptions ON users.us_login = staff_descriptions.sd_user_login WHERE sd_specialization=? LIMIT ?, ?;";

    public DatabaseUserDAO() throws DaoException{
        super();
    }
    @Override
    public User userAuthentication(String login, String password) throws DaoException {

        User user = null;
        try {
            List<User> result = readFromDatabase(GET_USER_QUERY, login, password);
            if(!result.isEmpty()){
                user = result.get(0);
            }
            return user;
        } catch (SQLException|ConnectionPoolException e) {

            DaoException daoException = new DaoException("Cannot load user from database with login = \'"+login+"\' and password = \'"+password+"\'",e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.userAuthentication", daoException);
            throw daoException;
        }

    }

    @Override
    public User getUserByLogin(String login) throws DaoException {

        User user = null;
        try {
            List<User> result = readFromDatabase(GET_USER_BY_LOGIN_QUERY);
            if(!result.isEmpty()){
                user = result.get(0);
            }
            return user;
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not get user with login = \'"+login+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.getUserByLogin", daoException);
            throw daoException;
        }

    }

    @Override
    public List<User> searchUsersByRole(UserRole userRole, int limit, int startFrom) throws DaoException {

        List<User> users;
        try {
            users = readFromDatabase(SEARCH_USER_BY_ROLE_QUERY, userRole.toString().toLowerCase(), limit, startFrom);
            return users;
        } catch (ConnectionPoolException|SQLException e) {
            DaoException daoException = new DaoException("Can not search user with role \'"+userRole+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.searchUsersByRole", daoException);
            throw daoException;
        }

    }

    @Override
    public List<User> searchUsersByName(String firstName, String secondName, int limit, int startFrom) throws DaoException {

        List<User> users;
        try {
            users = readFromDatabase(SEARCH_USERS_QUERY, '%'+firstName+'%', '%'+secondName+'%', limit, startFrom);
            return users;
        } catch (ConnectionPoolException | SQLException ex) {
            DaoException daoException = new DaoException("Can not search users in database", ex);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.searchUsersByName", daoException);
            throw daoException;
        }
    }

    @Override
    public void insertUser(User user) throws DaoException {

        try {
            writeToDatabase(INSERT_USER_QUERY, user.getLogin(), user.getPassword(),
            user.getFirstName(), user.getSecondName(), user.getUserRole().toString().toLowerCase(),
                    user.getUserImage(), user.getMail(), user.getPhone());
        } catch (ConnectionPoolException|SQLException e) {
            DaoException daoException = new DaoException("Can not insert user to database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.insertUser", daoException);
            throw daoException;
        }
    }

    @Override
    public void updateUser(User user) throws DaoException {


        try {
            writeToDatabase(UPDATE_USER_QUERY, user.getLogin(), user.getPassword(),
                    user.getFirstName(), user.getSecondName(), user.getUserRole().toString().toLowerCase(),
                    user.getUserImage(), user.getMail(), user.getPhone());
        } catch (ConnectionPoolException|SQLException e) {
            DaoException daoException = new DaoException("Cannot update user", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.updateUser", daoException);
            throw daoException;
        }
    }

    @Override
    public void deleteUser(String login) throws DaoException {

        try {
            writeToDatabase(DELETE_USER_QUERY, login);
        } catch (ConnectionPoolException|SQLException e) {
            DaoException daoException = new DaoException("Can not delete user with login \'"+login+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.deleteUser", daoException);
            throw daoException;
        }
    }

    @Override
    public List<User> getUsersBySpecialization(String specialization, int limit, int startFrom) throws DaoException {

        List<User> users;
        try {
            users = readFromDatabase(SEARCH_USER_BY_SPECIALIZATION_QUERY, specialization, limit, startFrom);
            return users;
        } catch (ConnectionPoolException |SQLException e) {
            DaoException daoException = new DaoException("Can not search users with specialization = \'"+specialization+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDAO.getUsersBySpecialization", daoException);
            throw daoException;
        }
    }
    @Override
    protected User resultSetToDomain(ResultSet resultSet){
        User user = new User();
        UserDescription userDescription = new UserDescription();
        user.setUserDescription(userDescription);

        try {
            user.setLogin(resultSet.getString("us_login"));
        } catch (SQLException e) {
            user.setLogin(null);
        }
        try {
            user.setPassword(resultSet.getString("us_password"));
        } catch (SQLException e) {
            user.setPassword(null);
        }
        try {
            user.setUserRole(UserRole.valueOf(resultSet.getString("us_group").toUpperCase()));
        } catch (SQLException e) {
            user.setUserRole(null);
        }
        try {
            user.setFirstName(resultSet.getString("us_first_name"));
        } catch (SQLException e) {
            user.setFirstName(null);
        }
        try {
            user.setSecondName(resultSet.getString("us_second_name"));
        } catch (SQLException e) {
            user.setSecondName(null);
        }
        try {
            user.setMail(resultSet.getString("us_mail"));
        } catch (SQLException e) {
            user.setMail(null);
        }
        try {
            user.setPhone(resultSet.getString("us_phone"));
        } catch (SQLException e) {
            user.setPhone(null);
        }
        try {
            user.setUserImage(resultSet.getBytes("us_image"));
        } catch (SQLException e) {
            user.setUserImage(null);
        }
        try {
            userDescription.setDescription(resultSet.getString("sd_description"));
        } catch (SQLException e) {
            userDescription.setDescription(null);
        }
        try {
            userDescription.setSpecialization(resultSet.getString("sd_specialization"));
        } catch (SQLException e) {
            userDescription.setSpecialization(null);
        }
        return user;
    }
}

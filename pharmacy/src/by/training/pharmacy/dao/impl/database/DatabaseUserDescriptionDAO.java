package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.UserDescriptionDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.user.UserDescription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vladislav on 18.06.16.
 */
public class DatabaseUserDescriptionDAO extends DatabaseDAO<UserDescription> implements UserDescriptionDAO {
    private static final String INSERT_DESCRIPTION_QUERY = "INSERT INTO staff_descriptions (sd_user_login, sd_specialization, sd_description) VALUES (?, ?, ?);";
    private static final String GET_DESCRIPTION_QUERY = "SELECT sd_user_login, sd_specialization, sd_description FROM staff_descriptions WHERE sd_user_login=?; limit 1";
    private static final String UPDATE_DESCRIPTION_QUERY = "UPDATE staff_descriptions SET sd_specialization=?, sd_description=? WHERE sd_user_login=?;";
    private static final String DELETE_DESCRIPTION_QWERY = "DELETE FROM staff_descriptions WHERE sd_user_login=?;";

    public DatabaseUserDescriptionDAO() throws DaoException {
        super();
    }

    @Override
    public void insertUserDescription(UserDescription userDescription) throws DaoException {
        try {
            writeToDatabase(INSERT_DESCRIPTION_QUERY, userDescription.getUserLogin(), userDescription.getSpecialization(), userDescription.getDescription());
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not insert new description "+userDescription, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDescriptionDAO.insertUserDescription", daoException);
            throw daoException;
        }
    }

    @Override
    public UserDescription getUserDescriptionByLogin(String userLogin) throws DaoException {
        try {
            List<UserDescription> result = readFromDatabase(GET_DESCRIPTION_QUERY, userLogin);
            if(!result.isEmpty()){
                return result.get(0);
            }
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can load user description with login = \'"+userLogin+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDescriptionDAO.getUserDescriptionByLogin", daoException);
            throw daoException;
        }
        return null;
    }

    @Override
    public void updateUserDescription(UserDescription userDescription) throws DaoException {
        try {
            writeToDatabase(UPDATE_DESCRIPTION_QUERY, userDescription.getSpecialization(), userDescription.getDescription(), userDescription.getUserLogin());
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Cannot update user's description with login = \'"+userDescription.getUserLogin()+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDescriptionDAO.updateUserDescription", daoException);
            throw daoException;
        }
    }

    @Override
    public void deleteUserDescription(String userLogin) throws DaoException {
        try {
            writeToDatabase(DELETE_DESCRIPTION_QWERY, userLogin);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not delete user description with login = \'"+userLogin+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseUserDescriptionDAO.deleteUserDescription", daoException);
            throw daoException;
        }
    }

    @Override
    protected UserDescription resultSetToDomain(ResultSet resultSet) {
        UserDescription userDescription = new UserDescription();
        try {
            userDescription.setUserLogin(resultSet.getString("sd_user_login"));
        } catch (SQLException e) {
            try {
                userDescription.setUserLogin(resultSet.getString("us_login"));
            } catch (SQLException ex){
                userDescription.setUserLogin(null);
            }
        }

        try {
            userDescription.setSpecialization(resultSet.getString("sd_specialization"));
        } catch (SQLException e) {
            userDescription.setSpecialization(null);
        }

        try {
            userDescription.setDescription(resultSet.getString("sd_description"));
        } catch (SQLException e) {
            userDescription.setDescription(null);
        }

        return userDescription;
    }
}

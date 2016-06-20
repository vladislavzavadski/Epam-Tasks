package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.DrugManufacturerDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.drug.DrugManufacturer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vladislav on 19.06.16.
 */
public class DatabaseDrugManufacturerDao extends DatabaseDAO<DrugManufacturer> implements DrugManufacturerDAO {
    private static final String GET_MANUFACTURER_BY_COUNTRY_QUERY = "select dm_id, dm_name, dm_country, dm_description from drugs_manufactures where dm_country=? LIMIT ?, ?";
    private static final String GET_MANUFACTURER_BY_NAME_QUERY = "select dm_id, dm_name, dm_country, dm_description from drugs_manufactures where dm_name LIKE ? LIMIT ?, ?";
    private static final String GET_MANUFACTURER_BY_ID_QUERY = "select dm_id, dm_name, dm_country, dm_description from drugs_manufactures where dm_id=? LIMIT 1;";
    private static final String INSERT_MANUFACTURER_QUERY = "INSERT INTO drugs_manufactures (dm_name, dm_country, dm_description) VALUES (?, ?, ?);";
    private static final String UPDATE_MANUFACTURE_QUERY = "UPDATE drugs_manufactures SET dm_name=?, dm_country=?, dm_description=? where dm_id=?";
    private static final String DELETE_MANUFACTURER_QUERY = "DELETE FROM drugs_manufactures where dm_id=?;";
    public DatabaseDrugManufacturerDao() throws DaoException {
        super();
    }

    @Override
    public List<DrugManufacturer> getManufacturesByCountry(String country, int limit, int startFrom) throws DaoException {
        List<DrugManufacturer> drugManufacturers = null;
        try {
            drugManufacturers = readFromDatabase(GET_MANUFACTURER_BY_COUNTRY_QUERY, country, limit, startFrom);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not load manufacturer with country = \'"+country+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugManufacturerDao.getManufacturesByCountry", daoException);
            throw daoException;
        }
        return drugManufacturers;
    }

    @Override
    public List<DrugManufacturer> getManufacturesByName(String name, int limit, int startFrom) throws DaoException {
        List<DrugManufacturer> drugManufacturers = null;
        try {
            drugManufacturers = readFromDatabase(GET_MANUFACTURER_BY_NAME_QUERY, '%'+name+'%', limit, startFrom);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not load manufacturer with name LIKE \'"+name+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugManufacturerDao.getManufacturesByName", daoException);
            throw daoException;
        }
        return drugManufacturers;
    }

    @Override
    public DrugManufacturer getManufacturerById(int manufactureId) throws DaoException {
        try {
            List<DrugManufacturer> result = readFromDatabase(GET_MANUFACTURER_BY_ID_QUERY, manufactureId);
            if(!result.isEmpty()){
                return result.get(0);
            }
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not load manufacturer with id = \'"+ manufactureId+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugManufacturerDao.getManufacturerById", daoException);
            throw daoException;
        }
        return null;
    }

    @Override
    public void insertDrugManufacturer(DrugManufacturer drugManufacturer) throws DaoException {
        try {
            writeToDatabase(INSERT_MANUFACTURER_QUERY, drugManufacturer.getName(), drugManufacturer.getCountry(), drugManufacturer.getDescription());
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not insert manufacturer "+drugManufacturer, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugManufacturerDao.insertDrugManufacturer", daoException);
            throw daoException;
        }
    }

    @Override
    public void updateManufacturer(DrugManufacturer drugManufacturer) throws DaoException {
        try {
            writeToDatabase(UPDATE_MANUFACTURE_QUERY, drugManufacturer.getName(), drugManufacturer.getCountry(), drugManufacturer.getDescription(), drugManufacturer.getId());
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not update drugManufacturer "+drugManufacturer);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugManufacturerDao.updateManufacturer", daoException);
            throw daoException;
        }
    }

    @Override
    public void deleteManufacturer(int manufacturerId) throws DaoException {
        try {
            writeToDatabase(DELETE_MANUFACTURER_QUERY, manufacturerId);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not delete manufacturer with id = "+manufacturerId, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugManufacturerDao.deleteManufacturer", daoException);
            throw daoException;
        }
    }

    @Override
    DrugManufacturer resultSetToDomain(ResultSet resultSet) {
        DrugManufacturer drugManufacturer = new DrugManufacturer();

        try {
            drugManufacturer.setId(resultSet.getInt("dm_id"));
        } catch (SQLException e) {
            drugManufacturer.setId(0);
        }
        try {
            drugManufacturer.setName(resultSet.getString("dm_name"));
        } catch (SQLException e) {
            drugManufacturer.setName(null);
        }
        try {
            drugManufacturer.setDescription(resultSet.getString("dm_description"));
        } catch (SQLException e) {
            drugManufacturer.setDescription(null);
        }
        try {
            drugManufacturer.setCountry(resultSet.getString("dm_country"));
        } catch (SQLException e) {
            drugManufacturer.setCountry(null);
        }
        return drugManufacturer;
    }


}

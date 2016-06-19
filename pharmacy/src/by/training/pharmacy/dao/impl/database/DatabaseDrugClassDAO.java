package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.DrugClassDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.drug.DrugClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by vladislav on 19.06.16.
 */
public class DatabaseDrugClassDAO extends DatabaseDAO<DrugClass> implements DrugClassDAO {
    private static final String GET_CLASS_BY_NAME_QUERY = "select dr_class_name, dr_class_description from drug_classes where dr_class_name=? LIMIT 1;";
    private static final String INSERT_DRUG_CLASS_QUERY = "inserts into drug_classes (dr_class_name, dr_class_description) VALUES(?,?);";
    private static final String UPDATE_DRUG_CLASS_QUERY = "update drug_classes set dr_class_name=?, dr_class_description=? WHERE dr_class_name=?;";
    private static final String DELETE_DRUG_CLASS_QUERY = "delete from drug_classes where dr_class_name=?;";
    public DatabaseDrugClassDAO() throws DaoException {
        super();
    }

    @Override
    public DrugClass getDrugClassByName(String name) throws DaoException {
        try {
            List<DrugClass> result = readFromDatabase(GET_CLASS_BY_NAME_QUERY, name);
            if(!result.isEmpty()){
                return result.get(0);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            Logger daoLogger = LogManager.getLogger(this.getClass());
            DaoException daoException = new DaoException("Can not load drug class from database with name = \'"+name+"\'", ex);
            daoLogger.error("Method: DatabaseDrugClassDAO.getDrugClassByName", daoException);
            throw daoException;
        }
        return null;
    }

    @Override
    public void insertDrugClass(DrugClass drugClass) throws DaoException {
        try {
            writeToDatabase(INSERT_DRUG_CLASS_QUERY, drugClass.getName(), drugClass.getDescription());
        } catch (ConnectionPoolException |SQLException e) {
            Logger logger = LogManager.getLogger(this.getClass());
            DaoException daoException = new DaoException("Can not insert drug class "+drugClass, e);
            logger.error("Method: DatabaseDrugClassDAO.insertDrugClass", daoException);
            throw daoException;
        }
    }

    @Override
    public void updateDrugClass(DrugClass drugClass) throws DaoException {
        try {
            writeToDatabase(UPDATE_DRUG_CLASS_QUERY, drugClass.getName(), drugClass.getDescription());
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not update drug class " +drugClass, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugClassDAO.updateDrugClass", daoException);
            throw daoException;
        }
    }

    @Override
    public void deleteDrugClass(String name) throws DaoException {
        try {
            writeToDatabase(DELETE_DRUG_CLASS_QUERY, name);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not delete drug class with name = \'"+name+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseDrugClassDAO.deleteDrugClass", daoException);
            throw daoException;
        }
    }

    @Override
    protected DrugClass resultSetToDomain(ResultSet resultSet) {
        DrugClass drugClass = new DrugClass();
        try {
            drugClass.setName(resultSet.getString("dr_class_name"));
        } catch (SQLException e) {
            drugClass.setName(null);
        }

        try {
            drugClass.setDescription(resultSet.getString("dr_class_description"));
        } catch (SQLException e) {
            drugClass.setDescription(null);
        }
        return drugClass;
    }
}

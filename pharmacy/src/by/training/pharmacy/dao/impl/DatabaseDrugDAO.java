package by.training.pharmacy.dao.impl;

import by.training.pharmacy.dao.DrugDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.drug.Drug;
import by.training.pharmacy.domain.drug.DrugType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vladislav on 15.06.16.
 */
public class DatabaseDrugDAO extends DatabaseDAO<Drug> implements DrugDAO {

    private static final String GET_DRUGS_BY_ID_QUERY = "SELECT dr_class, dr_description, dr_image, dr_in_stock, dr_manufacturer, dr_name, dr_prescription_enable, dr_price, dr_type,  dr_dosage, dr_active_substance FROM drugs WHERE dr_id=?;";
    private static final String GET_DRUGS_BY_NAME_QUERY = "SELECT dr_id, dr_class, dr_description, dr_image, dr_in_stock, dr_manufacturer, dr_name, dr_prescription_enable, dr_price, dr_type,  dr_dosage, dr_active_substance FROM drugs WHERE dr_name LIKE ? LIMIT ?, ?;";
    private static final String GET_DRUGS_BY_CLASS_QUERY = "SELECT dr_id, dr_class, dr_description, dr_image, dr_in_stock, dr_manufacturer, dr_name, dr_prescription_enable, dr_price, dr_type,  dr_dosage, dr_active_substance FROM drugs WHERE dr_class=? LIMIT ?, ?;";
    private static final String GET_DRUGS_BY_ACTIVE_SUBSTANCE_QUERY = "SELECT dr_id, dr_class, dr_description, dr_image, dr_in_stock, dr_manufacturer, dr_name, dr_prescription_enable, dr_price, dr_type,  dr_dosage, dr_active_substance FROM drugs WHERE dr_active_substance=? LIMIT ?, ?;";
    private static final String INSERT_DRUG_QUERY = "insert into drugs (dr_class, dr_description, dr_image, dr_in_stock, dr_manufacturer, dr_name, dr_prescription_enable, dr_price, dr_type,  dr_dosage, dr_active_substance) values (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_DRUG_QUERY = "update drugs set dr_description = ?, dr_image = ?, dr_in_stock = ?, dr_prescription_enable = ?, dr_dosage = ? where dr_id=?;";
    private static final String DELETE_DRUG_QUERY = "delete from drugs where dr_id=?;";
    public DatabaseDrugDAO() throws DaoException{
        super();
    }
    @Override
    public Drug getDrugById(int drugId) throws DaoException {

        Drug drug = null;
        try {
            List<Drug> result = readFromDatabase(GET_DRUGS_BY_ID_QUERY, drugId);
            if(!result.isEmpty()){
                drug = result.get(0);
            }
            return drug;
        } catch (ConnectionPoolException|SQLException e) {
            throw new DaoException("Can not load drug with id = \'"+drugId+"\' from database", e);
        }
    }

    @Override
    public List<Drug> getDrugsByName(String name, int limit, int startFrom) throws DaoException {

        List<Drug> drugs;
        try {
            drugs = readFromDatabase(GET_DRUGS_BY_NAME_QUERY, "%"+name+"%", limit, startFrom);
            return drugs;
        } catch (SQLException|ConnectionPoolException e) {
            throw new DaoException("Can not load drugs with name like \'"+name+"\' from database", e);
        }
    }

    @Override
    public List<Drug> getDrugsByClass(String drugClass, int limit, int startFrom) throws DaoException {
        List<Drug> drugs;
        try {
            drugs = readFromDatabase(GET_DRUGS_BY_CLASS_QUERY, drugClass, limit, startFrom);
            return drugs;
        } catch (SQLException|ConnectionPoolException e) {
            throw new DaoException("Can not load drugs with class = \'"+drugClass+"\' from database", e);
        }
    }

    @Override
    public List<Drug> getDrugsByActiveSubstance(String activeSubstance, int limit, int startFrom) throws DaoException {
        List<Drug> drugs;
        try {
            drugs = readFromDatabase(GET_DRUGS_BY_ACTIVE_SUBSTANCE_QUERY, activeSubstance, limit, startFrom);
            return drugs;
        } catch (SQLException|ConnectionPoolException e) {
            throw new DaoException("Can not load drugs with activeSubstance = \'"+activeSubstance+"\' from database", e);
        }
    }


    @Override
    public void insertDrug(Drug drug) throws DaoException {
        try {
            String dosages = "";
            for(int i:drug.getDosages()){
                dosages+=','+i;
            }
            writeToDatabase(INSERT_DRUG_QUERY, drug.getClass(), drug.getDescription(), drug.getDrugImage(), drug.isInStock()
            , drug.getManufacturer(), drug.getName(), drug.isPrescriptionEnable(), drug.getPrice(), drug.getType().toString().toLowerCase(), dosages.substring(1), drug.getActiveSubstance());

        } catch (ConnectionPoolException|SQLException e) {
            throw new DaoException("Can not insert drug into database", e);
        }
    }

    @Override
    public void updateDrug(Drug drug) throws DaoException {
        try {
            String dosages = "";
            for (int i : drug.getDosages()) {
                dosages += ',' + i;
            }
            writeToDatabase(UPDATE_DRUG_QUERY, drug.getDescription(), drug.getDrugImage(), drug.isInStock(), drug.isPrescriptionEnable(), dosages.substring(1), drug.getId());
        } catch (ConnectionPoolException|SQLException e) {
            throw new DaoException("Can not update drug into database", e);
        }
    }

    @Override
    public void deleteDrug(int drugId) throws DaoException {
        try {
            writeToDatabase(DELETE_DRUG_QUERY, drugId);
        } catch (ConnectionPoolException|SQLException e) {
            throw new DaoException("Can not delete drug with id = \'"+drugId+"\' from database", e);
        }
    }

    @Override
    protected Drug resultSetToDomain(ResultSet resultSet){
        Drug drug = new Drug();

        try {
            drug.setName(resultSet.getString("dr_name"));
        } catch (SQLException e) {
            drug.setName(null);
        }

        try {
            drug.setId(resultSet.getInt("dr_id"));
        } catch (SQLException e) {
            drug.setId(0);
        }

        try {
            drug.setPrescriptionEnable(resultSet.getBoolean("dr_prescription_enable"));
        } catch (SQLException e) {
            drug.setPrescriptionEnable(false);
        }

        try {
            drug.setDescription(resultSet.getString("dr_description0"));
        } catch (SQLException e) {
            drug.setDescription(null);
        }

        try {
            drug.setActiveSubstance(resultSet.getString("dr_active_substance"));
        } catch (SQLException e) {
            drug.setActiveSubstance(null);
        }

        try {
            drug.setDrugClass(resultSet.getString("dr_class"));
        } catch (SQLException e) {
            drug.setDrugClass(null);
        }

        try {
            drug.setType(DrugType.valueOf(resultSet.getString("dr_type").toUpperCase()));
        } catch (SQLException e) {
            drug.setType(null);
        }

        try {
            String[] dosages = resultSet.getString("dr_dosage").split(",");
            for(String dosage:dosages){
                drug.getDosages().add(Integer.parseInt(dosage));
            }
        } catch (SQLException e) {
            drug.setDosages(null);
        }

        try {
            drug.setPrice(resultSet.getFloat("dr_price"));
        } catch (SQLException e) {
            drug.setPrice(0);
        }

        try {
            drug.setDrugImage(resultSet.getBytes("dr_image"));
        } catch (SQLException e) {
            drug.setDrugImage(null);
        }

        try {
            drug.setManufacturer(resultSet.getString("dr_manufacturer"));
        } catch (SQLException e) {
            drug.setManufacturer(null);
        }

        try {
            drug.setInStock(resultSet.getBoolean("dr_in_stock"));
        } catch (SQLException e) {
            drug.setInStock(false);
        }

        return drug;

    }
}

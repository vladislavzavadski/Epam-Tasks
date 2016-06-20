package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.PrescriptionDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.Period;
import by.training.pharmacy.domain.prescription.Prescription;
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
public class DatabasePrescriptionDAO extends DatabaseDAO<Prescription> implements PrescriptionDAO {
    private static final String GET_USERS_PRESCRIPTIONS_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_client_login=? limit ?, ?;";
    private static final String GET_PRESCRIPTIONS_BY_ID_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_drug_id=? limit ?, ?;";
    private static final String GET_DOCTORS_PRESCRIPTIONS_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_doctor=? limit ?, ?;";
    private static final String GET_PRESCRIPTIONS_BY_PRIMARY_KEY_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_client_login=? and pr_drug_id=? limit 1;";
    private static final String GET_PRESCRIPTIONS_BY_APPOINTMENT_DATE_BEFORE_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_appointment_date<? limit ?, ?;";
    private static final String GET_PRESCRIPTIONS_BY_APPOINTMENT_DATE_AFTER_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_appointment_date>? limit ?, ?;";
    private static final String GET_PRESCRIPTIONS_BY_APPOINTMENT_DATE_CURRENT_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_appointment_date=? limit ?, ?;";
    private static final String GET_PRESCRIPTIONS_BY_EXPIRATION_DATE_BEFORE_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_expiration_date<? limit ?, ?;";
    private static final String GET_PRESCRIPTIONS_BY_EXPIRATION_DATE_AFTER_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_expiration_date>? limit ?, ?;";
    private static final String GET_PRESCRIPTIONS_BY_EXPIRATION_DATE_CURRENT_QUERY = "select cl.us_login, dr_id, pr_doctor, pr_drug_count, pr_drug_dosage, pr_appointment_date, pr_expiration_date, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from prescriptions inner join drugs on dr_id = pr_drug_id inner join users as cl on pr_client_login = cl.us_login inner join users as doc on pr_doctor = doc.us_login where pr_expiration_date=? limit ?, ?;";
    private static final String INSERT_PRESCRIPTION_QUERY = "INSERT INTO prescriptions (pr_client_login, pr_drug_id, pr_doctor, pr_appointment_date, pr_expiration_date, pr_drug_count, pr_drug_dosage) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_PRESCRIPTION_QUERY = "UPDATE prescriptions SET pr_doctor=?, pr_appointment_date=?, pr_expiration_date=?, pr_drug_count=?, pr_drug_dosage=? WHERE pr_client_login=? and pr_drug_id=?";
    private static final String DELETE_PRESCRIPTION_QUERY = "DELETE FROM prescriptions WHERE pr_client_login=? and pr_drug_id=?;";

    public DatabasePrescriptionDAO() throws DaoException {
        super();
    }

    @Override
    public List<Prescription> getUsersPrescriptions(String clientLogin, int limit, int startFrom) throws DaoException {
        List<Prescription> prescriptions = null;
        try {
            prescriptions = readFromDatabase(GET_USERS_PRESCRIPTIONS_QUERY, clientLogin, limit, startFrom);
        } catch (ConnectionPoolException |SQLException e) {
            DaoException daoException = new DaoException("Can not load users prescriptions with login = \'"+clientLogin+"\' from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.getUsersPrescriptions", daoException);
            throw daoException;
        }
        return prescriptions;
    }

    @Override
    public List<Prescription> getPrescriptionsByDrugId(int drugId, int limit, int startFrom) throws DaoException {
        List<Prescription> prescriptions = null;
        try {
            prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_ID_QUERY, drugId, limit, startFrom);
        } catch (ConnectionPoolException |SQLException e) {

            DaoException daoException = new DaoException("Can not load prescriptions with drugId = \'"+drugId+"\' from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.getPrescriptionsByDrugId", daoException);
            throw daoException;
        }
        return prescriptions;
    }

    @Override
    public List<Prescription> getDoctorsPrescriptions(String doctorLogin, int limit, int startFrom) throws DaoException {
        List<Prescription> prescriptions = null;
        try {
            prescriptions = readFromDatabase(GET_DOCTORS_PRESCRIPTIONS_QUERY, doctorLogin, limit, startFrom);
        } catch (ConnectionPoolException |SQLException e) {
            DaoException daoException = new DaoException("Can not load prescriptions with doctor login = \'"+doctorLogin+"\' from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.getDoctorsPrescriptions", daoException);
            throw daoException;
        }
        return prescriptions;
    }

    @Override
    public Prescription getPrescriptionByPrimaryKey(String userLogin, int drugId) throws DaoException {
        List<Prescription> prescriptions = null;
        try {
            prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_PRIMARY_KEY_QUERY, userLogin, drugId);
            if(!prescriptions.isEmpty()){
                return prescriptions.get(0);
            }
        } catch (ConnectionPoolException |SQLException e) {

            DaoException daoException = new DaoException("Can not load prescriptions with login = \'"+userLogin+"\' and drugId = \'"+drugId+"\' from database", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.getPrescriptionByPrimaryKey", daoException);
            throw daoException;
        }
        return null;
    }

    @Override
    public List<Prescription> getPrescriptionsByAppointmentDate(Date date, Period period, int limit, int startFrom) throws DaoException {
        List<Prescription> prescriptions = null;
        try {
            switch (period) {
                case AFTER_DATE: {
                    prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_APPOINTMENT_DATE_AFTER_QUERY, date, limit, startFrom);
                    break;
                }

                case BEFORE_DATE: {
                    prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_APPOINTMENT_DATE_BEFORE_QUERY, date, limit, startFrom);
                    break;
                }

                case CURRENT_DATE: {
                    prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_APPOINTMENT_DATE_CURRENT_QUERY, date, limit, startFrom);
                    break;
                }
            }
        }catch (ConnectionPoolException|SQLException e){
            DaoException daoException = new DaoException("Can not load prescriptions by appointment date = \'"+date+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.getPrescriptionsByAppointmentDate", daoException);
            throw daoException;
        }
        return prescriptions;
    }

    @Override
    public List<Prescription> getPrescriptionsByExpirationDate(Date date, Period period, int limit, int startFrom) throws DaoException {
        List<Prescription> prescriptions = null;
        try {
            switch (period) {
                case AFTER_DATE: {
                    prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_EXPIRATION_DATE_AFTER_QUERY, date, limit, startFrom);
                    break;
                }

                case BEFORE_DATE: {
                    prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_EXPIRATION_DATE_BEFORE_QUERY, date, limit, startFrom);
                    break;
                }

                case CURRENT_DATE: {
                    prescriptions = readFromDatabase(GET_PRESCRIPTIONS_BY_EXPIRATION_DATE_CURRENT_QUERY, date, limit, startFrom);
                    break;
                }
            }
        }catch (ConnectionPoolException|SQLException e){

            DaoException daoException = new DaoException("Can not load prescriptions by appointment date = \'"+date+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.getPrescriptionsByExpirationDate", daoException);
            throw daoException;
        }
        return prescriptions;
    }

    @Override
    public void insertPrescription(Prescription prescription) throws DaoException {
        try {
            writeToDatabase(INSERT_PRESCRIPTION_QUERY, prescription.getClient().getLogin(), prescription.getDrug().getId(), prescription.getDoctor().getLogin(), prescription.getAppointmentDate(), prescription.getExpirationDate(), prescription.getDrugCount(), prescription.getDrugDosage());
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not insert prescription "+prescription, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.insertPrescription", daoException);
            throw daoException;
        }
    }

    @Override
    public void updatePrescription(Prescription prescription) throws DaoException {
        try {
            writeToDatabase(UPDATE_PRESCRIPTION_QUERY, prescription.getDoctor().getLogin(), prescription.getAppointmentDate(), prescription.getExpirationDate(), prescription.getDrugCount(), prescription.getDrugDosage(), prescription.getClient().getLogin(), prescription.getDrug().getId());
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not update prescription "+prescription, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.updatePrescription", daoException);
            throw daoException;
        }
    }

    @Override
    public void deletePrescription(String clientLogin, int drugId) throws DaoException {
        try {
            writeToDatabase(DELETE_PRESCRIPTION_QUERY, clientLogin, drugId);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not delete prescription with clientLogin = \'"+clientLogin+"\' and drugId = \'"+drugId, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabasePrescriptionDAO.deletePrescription", daoException);
            throw daoException;
        }
    }

    @Override
    protected Prescription resultSetToDomain(ResultSet resultSet) {
        Prescription prescription = new Prescription();
        User doctor = new User();
        prescription.setDoctor(doctor);
        try {
            prescription.setAppointmentDate(resultSet.getDate("pr_appointment_date"));
        } catch (SQLException e) {
            prescription.setAppointmentDate(null);
        }

        try {
            prescription.setExpirationDate(resultSet.getDate("pr_expiration_date"));
        } catch (SQLException e) {
            prescription.setExpirationDate(null);
        }

        try {
            prescription.setDrugCount(resultSet.getShort("pr_drug_count"));
        } catch (SQLException e) {
            prescription.setDrugCount((short) 0);
        }

        try {
            prescription.setDrugDosage(resultSet.getShort("pr_drug_dosage"));
        } catch (SQLException e) {
            prescription.setDrugDosage((short) 0);
        }

        try {
            DatabaseDAO<User> databaseDAO = new DatabaseUserDAO();
            prescription.setClient(databaseDAO.resultSetToDomain(resultSet));
        } catch (DaoException e) {
            prescription.setClient(null);
        }

        try {
            DatabaseDAO<Drug> databaseDAO = new DatabaseDrugDAO();
            prescription.setDrug(databaseDAO.resultSetToDomain(resultSet));
        } catch (DaoException e) {
            prescription.setDrug(null);
        }

        try {
            doctor.setFirstName(resultSet.getString("doc_first_name"));
        } catch (SQLException e) {
            doctor.setFirstName(null);
        }

        try {
            doctor.setSecondName(resultSet.getString("doc_second_name"));
        } catch (SQLException e) {
            doctor.setSecondName(null);
        }
        try {
            doctor.setLogin(resultSet.getString("doc_login"));
        } catch (SQLException e) {
            doctor.setLogin(null);
        }

        return prescription;
    }
}

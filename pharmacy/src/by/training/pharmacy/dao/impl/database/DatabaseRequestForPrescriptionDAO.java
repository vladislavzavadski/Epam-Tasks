package by.training.pharmacy.dao.impl.database;

import by.training.pharmacy.dao.RequestForPrescriptionDAO;
import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.Period;
import by.training.pharmacy.domain.prescription.RequestForPrescription;
import by.training.pharmacy.domain.prescription.RequestStatus;
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
public class DatabaseRequestForPrescriptionDAO extends DatabaseDAO<RequestForPrescription> implements RequestForPrescriptionDAO {
    private static final String GET_REQUESTS_BY_CLIENT_QUERY =  "select cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment, re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_client_login=? LIMIT ?, ?;";
    private static final String GET_REQUESTS_BY_DRUG_ID_QUERY = "select cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment,  re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_drug_id=? LIMIT ?, ?;";
    private static final String GET_REQUESTS_BY_DOCTOR_QUERY = "select cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment, re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_doctor=? LIMIT ?, ?;";
    private static final String GET_REQUESTS_BY_STATUS_QUERY = "select cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment, re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_status=? LIMIT ?, ?;";
    private static final String GET_REQUESTS_BY_DATE_BEFORE_QUERY = "select cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment, re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_request_date<? LIMIT ?, ?;";
    private static final String GET_REQUESTS_BY_DATE_AFTER_QUERY = "select cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment, re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_request_date>? LIMIT ?, ?;";
    private static final String GET_REQUESTS_BY_DATE_CURRENT_QUERY = "select cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment, re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_request_date=? LIMIT ?, ?;";
    private static final String GET_REQUESTS_BY_ID = "select re_status, cl.us_login, dr_id, re_doctor, re_id, re_prolong_to, re_request_date, re_clients_comment, re_doctors_comment, dr_name, cl.us_first_name, cl.us_second_name, doc.us_first_name as doc_first_name, doc.us_second_name as doc_second_name, doc.us_login as doc_login  from requests_for_prescriptions inner join drugs on dr_id = re_drug_id inner join users as cl on re_client_login = cl.us_login inner join users as doc on re_doctor = doc.us_login WHERE re_id=? LIMIT 1;";
    private static final String INSERT_REQUEST_QUERY = "INSERT INTO requests_for_prescriptions (re_id, re_client_login, re_drug_id, re_doctor, re_prolong_to, re_status, re_clients_comment, re_doctors_comment, re_request_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_REQUEST_QUERY = "UPDATE requests_for_prescriptions SET re_drug_id=?, re_doctor=?, re_prolong_to=?, re_status=?, re_clients_comment=?, re_doctors_comment=? WHERE re_id=?;";
    private static final String DELETE_REQUEST_QUERY = "DELETE FROM requests_for_prescriptions WHERE re_id=?;";
    public DatabaseRequestForPrescriptionDAO() throws DaoException {
        super();
    }

    @Override
    public List<RequestForPrescription> getRequestsByClient(String clientLogin, int limit, int startFrom) throws DaoException {
        List<RequestForPrescription> requestForPrescriptions = null;
        try {
            requestForPrescriptions = readFromDatabase(GET_REQUESTS_BY_CLIENT_QUERY, clientLogin, limit, startFrom);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not load requests for user with login = \'"+clientLogin+"\'"+clientLogin, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.getRequestsByClient", daoException);
            throw daoException;
        }
        return requestForPrescriptions;
    }

    @Override
    public List<RequestForPrescription> getRequestsByDrugId(int drugId, int limit, int startFrom) throws DaoException {
        List<RequestForPrescription> requestForPrescriptions = null;
        try {
            requestForPrescriptions = readFromDatabase(GET_REQUESTS_BY_DRUG_ID_QUERY, drugId, limit, startFrom);
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not load requests for drugId = \'"+drugId+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.getRequestsByDrugId", daoException);
            throw daoException;
        }
        return requestForPrescriptions;
    }

    @Override
    public List<RequestForPrescription> getRequestsByDoctor(String doctorLogin, int limit, int startFrom) throws DaoException {
        List<RequestForPrescription> requestForPrescriptions = null;
        try {
            requestForPrescriptions = readFromDatabase(GET_REQUESTS_BY_DOCTOR_QUERY, doctorLogin, limit, startFrom);
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not load requests with doctorLogin = \'"+doctorLogin+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.getRequestsByDoctor", daoException);
            throw daoException;
        }
        return requestForPrescriptions;
    }

    @Override
    public List<RequestForPrescription> getRequestsByStatus(RequestStatus requestStatus, int limit, int startFrom) throws DaoException {
        List<RequestForPrescription> requestForPrescriptions = null;
        try {
            requestForPrescriptions = readFromDatabase(GET_REQUESTS_BY_STATUS_QUERY, requestStatus.toString().toLowerCase(), limit, startFrom);
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not load requests with requestStatus \'"+requestStatus+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.getRequestsByStatus", daoException);
            throw daoException;
        }
        return requestForPrescriptions;
    }

    @Override
    public List<RequestForPrescription> getRequestsByDate(Date requestDate, Period period, int limit, int startFrom) throws DaoException {
        List<RequestForPrescription> requestForPrescriptions = null;
        try {

            switch (period) {
                case AFTER_DATE: {
                    requestForPrescriptions = readFromDatabase(GET_REQUESTS_BY_DATE_AFTER_QUERY, requestDate, limit, startFrom);
                    break;
                }

                case BEFORE_DATE: {
                    requestForPrescriptions = readFromDatabase(GET_REQUESTS_BY_DATE_BEFORE_QUERY, requestDate, limit, startFrom);
                    break;
                }

                case CURRENT_DATE: {
                    requestForPrescriptions = readFromDatabase(GET_REQUESTS_BY_DATE_CURRENT_QUERY, requestDate, limit, startFrom);
                    break;
                }
            }
        }catch (ConnectionPoolException | SQLException e){
            DaoException daoException = new DaoException("Can not load requests from database with requestDate = \'"+requestDate+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.getRequestsByDate", daoException);
            throw daoException;
        }
        return requestForPrescriptions;
    }

    @Override
    public RequestForPrescription getRequestById(int requestId) throws DaoException {
        try {
            List<RequestForPrescription> results = readFromDatabase(GET_REQUESTS_BY_ID, requestId);
            if(!results.isEmpty()){
                return results.get(0);
            }
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not load request with id = \'"+requestId+"\'", e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.getRequestById", daoException);
            throw daoException;
        }
        return null;
    }

    @Override
    public void insertRequest(RequestForPrescription requestForPrescription) throws DaoException {
        try {
            writeToDatabase(INSERT_REQUEST_QUERY, requestForPrescription.getId(), requestForPrescription.getClient().getLogin(), requestForPrescription.getDrug().getId(), requestForPrescription.getDoctor().getLogin(), requestForPrescription.getProlongDate(), requestForPrescription.getRequestStatus().toString().toLowerCase(), requestForPrescription.getClientComment(), requestForPrescription.getDoctorComment(), requestForPrescription.getRequestDate());
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not insert new request "+ requestForPrescription, e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.insertRequest", daoException);
            throw daoException;
        }
    }

    @Override
    public void updateRequest(RequestForPrescription requestForPrescription) throws DaoException {
        try {
//"UPDATE requests_for_prescriptions re_drug_id=?, re_doctor=?, re_prolong_to=?, re_status=?, re_clients_comment=?, re_doctors_comment=? WHERE re_id=?;";
            writeToDatabase(UPDATE_REQUEST_QUERY, requestForPrescription.getDrug().getId(), requestForPrescription.getDoctor().getLogin(), requestForPrescription.getProlongDate(), requestForPrescription.getRequestStatus().toString().toLowerCase(), requestForPrescription.getClientComment(), requestForPrescription.getDoctorComment(), requestForPrescription.getId());
        } catch (ConnectionPoolException | SQLException e) {
            DaoException daoException = new DaoException("Can not update request "+requestForPrescription,e);
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.updateRequest", daoException);
            throw daoException;
        }
    }

    @Override
    public void deleteRequest(int requestId) throws DaoException {
        try {
            writeToDatabase(DELETE_REQUEST_QUERY, requestId);
        } catch (ConnectionPoolException | SQLException e) {

            DaoException daoException = new DaoException("Can not delete request with requestId = \'"+requestId+"\'");
            Logger logger = LogManager.getLogger(this.getClass());
            logger.error("Method: DatabaseRequestForPrescriptionDAO.deleteRequest", daoException);
            throw daoException;
        }
    }

    @Override
    protected RequestForPrescription resultSetToDomain(ResultSet resultSet) {
        RequestForPrescription requestForPrescription = new RequestForPrescription();
        User doctor = new User();
        requestForPrescription.setDoctor(doctor);
        try {
            requestForPrescription.setId(resultSet.getInt("re_id"));
        } catch (SQLException e) {
            requestForPrescription.setId(0);
        }

        try {
            requestForPrescription.setRequestStatus(RequestStatus.valueOf(resultSet.getString("re_status").toUpperCase()));
        } catch (SQLException e) {
            requestForPrescription.setRequestStatus(null);
        }

        try {
            requestForPrescription.setProlongDate(resultSet.getDate("re_prolong_to"));
        } catch (SQLException e) {
            requestForPrescription.setProlongDate(null);
        }

        try {
            requestForPrescription.setRequestDate(resultSet.getDate("re_request_date"));
        } catch (SQLException e) {
            requestForPrescription.setRequestDate(null);
        }

        try {
            requestForPrescription.setRequestStatus(RequestStatus.valueOf(resultSet.getString("re_status").toUpperCase()));
        } catch (SQLException e) {
            requestForPrescription.setRequestStatus(null);
        }

        try {
            requestForPrescription.setClientComment(resultSet.getString("re_clients_comment"));
        } catch (SQLException e) {
            requestForPrescription.setClientComment(null);
        }
        try {
            requestForPrescription.setDoctorComment(resultSet.getString("re_doctors_comment"));
        } catch (SQLException e) {
            requestForPrescription.setDoctorComment(null);
        }
        try {
            DatabaseDAO<User> databaseDAO = new DatabaseUserDAO();
            requestForPrescription.setClient(databaseDAO.resultSetToDomain(resultSet));
        } catch (DaoException e) {
            requestForPrescription.setClient(null);
        }

        try {
            DatabaseDAO<Drug> databaseDAO = new DatabaseDrugDAO();
            requestForPrescription.setDrug(databaseDAO.resultSetToDomain(resultSet));
        } catch (DaoException e) {
            requestForPrescription.setDrug(null);
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
        return requestForPrescription;
    }
}

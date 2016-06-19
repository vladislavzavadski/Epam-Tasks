package by.training.pharmacy.dao;

import by.training.pharmacy.dao.connection_pool.exception.ConnectionPoolException;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.Period;
import by.training.pharmacy.domain.RequestForPrescription;
import by.training.pharmacy.domain.RequestStatus;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by vladislav on 19.06.16.
 */
public interface RequestForPrescriptionDAO {
    List<RequestForPrescription> getRequestsByClient(String clientLogin, int limit, int startFrom) throws DaoException;
    List<RequestForPrescription> getRequestsByDrugId(int drugId, int limit, int startFrom) throws DaoException;
    List<RequestForPrescription> getRequestsByDoctor(String doctorLogin, int limit, int startFrom) throws DaoException;
    List<RequestForPrescription> getRequestsByStatus(RequestStatus requestStatus, int limit, int startFrom) throws DaoException;
    List<RequestForPrescription> getRequestsByDate(Date requestDate, Period period, int limit, int startFrom) throws DaoException;
    RequestForPrescription getRequestById(int requestId) throws DaoException;
    void insertRequest(RequestForPrescription requestForPrescription) throws DaoException;
    void updateRequest(RequestForPrescription requestForPrescription) throws DaoException;
    void deleteRequest(int requestId) throws DaoException;
}

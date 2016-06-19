import by.training.pharmacy.dao.DaoFactory;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.Prescription;
import by.training.pharmacy.domain.RequestForPrescription;

import java.util.List;


/**
 * Created by vladislav on 14.06.16.
 */
public class Main {
    public static void main(String[] args) throws DaoException {
        List<RequestForPrescription> prescriptions = DaoFactory.takeFactory(1).getRequestForPrescriptionDAO().getRequestsByClient("vladikxd4", 0, 10);
     //   DaoFactory.takeFactory(1).getPrescriptionDAO().deletePrescription(prescriptions.get(0).getClient().getLogin(), prescriptions.get(0).getDrug().getId());
    }
}

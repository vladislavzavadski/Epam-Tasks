package by.training.pharmacy.dao;


import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.dao.impl.DatabaseDrugDAO;
import by.training.pharmacy.dao.impl.DatabaseUserDAO;

/**
 * Created by vladislav on 13.06.16.
 */
public class DAOFactory {
    private static DAOFactory daoFactory = new DAOFactory();

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory;
    }

    public UserDAO getUserDAO() throws DaoException {
        return new DatabaseUserDAO();
    }

    public DrugDAO getDrugDAO() throws DaoException{
        return new DatabaseDrugDAO();
    }

}


import by.training.pharmacy.dao.DAOFactory;
import by.training.pharmacy.dao.UserDAO;
import by.training.pharmacy.dao.exception.DaoException;
import by.training.pharmacy.domain.user.User;
import by.training.pharmacy.domain.user.UserRole;

import java.util.List;

/**
 * Created by vladislav on 14.06.16.
 */
public class Main {
    public static void main(String[] args) throws DaoException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
       // User user = userDAO.getUserByLoginAndPassword("vladikxd4", "qwerty");
        List<User> users = userDAO.searchUsersByName("нис", "едн", 0, 2);
       // user.setMail("bsuir@gmail.com");
        new String();
    }
}

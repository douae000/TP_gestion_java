package Model;

import DAO.LoginDAOimpl;

public class LoginModel {

    private LoginDAOimpl loginDAO;

    public LoginModel(LoginDAOimpl loginDAO) {
        this.loginDAO = loginDAO;
    }

    // Authentication 
    public boolean authenticate(String username, String password) {
        return loginDAO.authenticate(username, password); // Delegate to DAO
    }

	
}

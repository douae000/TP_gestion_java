package Main;

import Controller.EmployeController;
import Controller.HolidayController;
import Controller.LoginController;
import DAO.EmployeDAOImpl;
import DAO.HolidayDAOImpl;
import DAO.LoginDAOimpl;
import Model.EmployeModel;
import Model.HolidayModel;
import Model.LoginModel;
import View.MainView;
import View.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginDAOimpl loginDAO = new LoginDAOimpl();
        EmployeDAOImpl employeDAO = new EmployeDAOImpl();
        HolidayDAOImpl holidayDAO = new HolidayDAOImpl();
        LoginModel loginModel = new LoginModel(loginDAO);
        EmployeModel employeModel = new EmployeModel(employeDAO);
        HolidayModel holidayModel = new HolidayModel(holidayDAO);

        LoginView loginView = new LoginView();
        MainView employeHolidayView = new MainView();

        new LoginController(loginView, loginModel);

        loginView.setVisible(true);
        loginView.addLoginListener(e -> {
            if (loginModel.authenticate(loginView.getUsername(), loginView.getPassword())) {
            
                loginView.setVisible(false);
                
                new EmployeController(employeHolidayView, employeModel);
                new HolidayController(employeHolidayView, holidayModel);

                employeHolidayView.setVisible(true);
            } else {
                loginView.showError("Invalid username or password. Please try again.");
            }
        });
    }
}

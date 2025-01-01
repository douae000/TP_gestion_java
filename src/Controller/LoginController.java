package Controller;

import Model.LoginModel;
import View.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private final LoginView loginView;
    private final LoginModel loginModel;

    public LoginController(LoginView loginView, LoginModel loginModel) {
        this.loginView = loginView;
        this.loginModel = loginModel;

        
        this.loginView.addLoginListener(new LoginListener());
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
 
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            
            if (username.isEmpty() || password.isEmpty()) {
                loginView.showError("Username or password cannot be empty.");
                return;
            }

            
            boolean isAuthenticated = loginModel.authenticate(username, password);

            if (isAuthenticated) {
             
                loginView.showError("Login successful!");
                loginView.close(); 
            } else {
                
                loginView.showError("Invalid username or password. Please try again.");
            }
        }
    }
}

package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOimpl {

    
	public boolean authenticate(String username, String password) {
        String sql = "SELECT password FROM login WHERE username = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(password); 
                }
            }
        } catch (SQLException exception) {
            System.err.println("Erreur lors de l'authentication: " + exception.getMessage());
            exception.printStackTrace();
        }
        return false; 
    }
}

package Model;

import DAO.EmployeDAOImpl;
import java.io.*;
import java.util.List;

public class EmployeModel {
    private EmployeDAOImpl dao;

    public EmployeModel(EmployeDAOImpl dao) {
        this.dao = dao;
    }

    // Fonction d'ajout d'un employé :
    public boolean addEmploye(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {
        if (salaire < 0) {
            System.out.println("Erreur : le salaire doit être positif.");
            return false;
        }
        if (id < 0) {
            System.out.println("Erreur : l'id doit être positif.");
            return false;
        }
        if (telephone.length() != 10) {
            System.out.println("Erreur : le téléphone doit contenir 10 numéros.");
            return false;
        }
        if (!email.contains("@")) {
            System.out.println("Erreur : le mail doit contenir le @.");
            return false;
        }

        Employe e = new Employe(id, nom, prenom, email, telephone, salaire, role, poste, solde);

        dao.add(e);

        return true;
    }

    // Fonction de suppression d'un employé :
    public boolean deleteEmploye(int id) {
        dao.delete(id);
        return true;
    }

    // Fonction de mise à jour d'un employé :
    public boolean updateEmploye(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {
        Employe e = new Employe(id, nom, prenom, email, telephone, salaire, role, poste, solde);
        dao.update(e);
        return true;
    }

    // Fonction de mise à jour du solde d'un employé :
    public boolean updateSolde(int id, int solde) {
        dao.updateSolde(id, solde);
        return true;
    }

    // Fonction d'affichage des employés :
    public List<Employe> displayEmploye() {
        return dao.display();
    }

    // Vérification de l'existence du fichier :
    private void checkFileExists(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IOException("Erreur : Le fichier n'existe pas.");
        }
        if (!file.isFile()) {
            throw new IOException("Erreur : Le chemin ne correspond pas à un fichier valide.");
        }
        if (!file.canRead()) {
            throw new IOException("Erreur : Le fichier ne peut pas être lu.");
        }
    }

    // Méthode pour importer des données :
    public void importData(String fileName) throws IOException {
        File file = new File(fileName);
        checkFileExists(file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Supposons que les champs sont séparés par des virgules
                if (parts.length == 9) {
                    int id = Integer.parseInt(parts[0]);
                    String nom = parts[1];
                    String prenom = parts[2];
                    String email = parts[3];
                    String telephone = parts[4];
                    double salaire = Double.parseDouble(parts[5]);
                    Role role = Role.valueOf(parts[6]);
                    Poste poste = Poste.valueOf(parts[7]);
                    int solde = Integer.parseInt(parts[8]);

                    addEmploye(id, nom, prenom, email, telephone, salaire, role, poste, solde);
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new IOException("Erreur lors de l'importation des données : " + e.getMessage(), e);
        }
    }

    // Méthode pour exporter des données :
    public void exportData(String fileName, List<Employe> data) throws IOException {
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Employe employe : data) {
                String line = String.format("%d,%s,%s,%s,%s,%.2f,%s,%s,%d", 
                        employe.getId(), 
                        employe.getNom(), 
                        employe.getPrenom(), 
                        employe.getEmail(), 
                        employe.getTelephone(), 
                        employe.getSalaire(), 
                        employe.getRole(), 
                        employe.getPoste(), 
                        employe.getSolde());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Erreur lors de l'exportation des données : " + e.getMessage(), e);
        }
    }
}

package com.ums.ui;



import com.ums.DBManager;
import com.ums.UMSApplication;
import com.ums.model.Role;
import com.ums.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserUIController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> nomColumn;
    @FXML
    private TableColumn<User, String> prenomColumn;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label telephoneLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Button supprimerButton;
    @FXML
    private TextField searchField;

    private ObservableList<User> userList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    	
    	// Configuration des colonnes de la table
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        // Chargement des utilisateurs depuis la base de données
        loadUsers();

        // Gestion de la sélection d'un utilisateur dans la table
        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
    
        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

    private void loadUsers() {
        try {
            userList = FXCollections.observableArrayList();
            Connection connection = DBManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String telephone = resultSet.getString("telephone");
                String login = resultSet.getString("login");
                String role = resultSet.getString("role");

                User user = new User(id, nom, prenom, email, telephone, login, Role.valueOf(role));
                userList.add(user);
            }


            userTable.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUserDetails(User user) {
        if (user != null) {
            nomLabel.setText(user.getNom());
            prenomLabel.setText(user.getPrenom());
            emailLabel.setText(user.getEmail());
            telephoneLabel.setText(user.getTelephone());
            loginLabel.setText(user.getLogin());
            passwordLabel.setText(user.getPassword());
            roleLabel.setText(user.getRole().toString());
        } else {
            nomLabel.setText("");
            prenomLabel.setText("");
            emailLabel.setText("");
            telephoneLabel.setText("");
            loginLabel.setText("");
            passwordLabel.setText("");
            roleLabel.setText("");
        }
    }

    @FXML
    private void handleClickAdd() {
        User newUser = new User();
        boolean isValiderClicked = UMSApplication.getInstance().showUserEditUI(newUser);

        if (isValiderClicked) {
            try {
                Connection connection = DBManager.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users (nom, prenom, email, telephone, login,password,role) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, newUser.getNom());
                statement.setString(2, newUser.getPrenom());
                statement.setString(3, newUser.getEmail());
                statement.setString(4, newUser.getTelephone());
                statement.setString(5, newUser.getLogin());
                statement.setString(6, newUser.getPassword());
                statement.setString(7, newUser.getRole().toString());
                statement.executeUpdate();

                loadUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleClickEdit() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean isValiderClicked = UMSApplication.getInstance().showUserEditUI(selectedUser);
            if (isValiderClicked) {
                try {
                    Connection connection = DBManager.getConnection();
                    PreparedStatement statement = connection.prepareStatement("UPDATE users SET nom=?, prenom=?, email=?, telephone=?, login=?, password=?, role=? WHERE id=?");
                    statement.setString(1, selectedUser.getNom());
                    statement.setString(2, selectedUser.getPrenom());
                    statement.setString(3, selectedUser.getEmail());
                    statement.setString(4, selectedUser.getTelephone());
                    statement.setString(5, selectedUser.getLogin());
                    statement.setString(6, selectedUser.getPassword());
                    statement.setString(7, selectedUser.getRole().toString());
                    statement.setInt(8, selectedUser.getId());

                    statement.executeUpdate();

                    loadUsers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur dans la table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteSeletedUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                Connection connection = DBManager.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
                statement.setInt(1, selectedUser.getId());
                statement.executeUpdate();

                loadUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur dans la table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        ObservableList<User> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            userTable.setItems(userList);
        } else {
            for (User user : userList) {
                if (user.getNom().toLowerCase().contains(searchText) ||
                        user.getPrenom().toLowerCase().contains(searchText) ||
                        user.getEmail().toLowerCase().contains(searchText) ||
                        user.getTelephone().toLowerCase().contains(searchText) ||
                        user.getRole().toString().toLowerCase().contains(searchText)) {
                    filteredList.add(user);
                }
            }
            userTable.setItems(filteredList);
        }
    }
}

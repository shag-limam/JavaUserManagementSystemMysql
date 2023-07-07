/*package com.ums.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserSearchController {

    @FXML
    private TextField searchTextField;
    
    private boolean searchClicked;

    @FXML
    private void handleSearch() {
        String searchText = searchTextField.getText();
        // Effectuer la recherche en utilisant le texte saisi dans le champ de recherche
        // Mettez ici le code pour traiter les résultats de la recherche
        
        // Marquer la recherche comme effectuée
        searchClicked = true;
        
        // Fermer la fenêtre de recherche
        searchTextField.getScene().getWindow().hide();
    }

    public boolean isSearchClicked() {
        return searchClicked;
    }

    // Autres méthodes de votre contrôleur
}*/
package com.ums.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserSearchController {
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;

    private boolean searchClicked;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleSearch() {
        searchClicked = true;
        closeWindow();
    }

    public boolean isSearchClicked() {
        return searchClicked;
    }

    private void closeWindow() {
        Stage stage = (Stage) searchButton.getScene().getWindow();
        stage.close();
    }
}

package com.ums;

import java.io.IOException;


import com.ums.model.User;
import com.ums.ui.UserEditUIController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UMSApplication extends Application {

	private Stage primaryStage;
    private BorderPane mainUI;
    private AnchorPane userUI;
   
    
    private static UMSApplication instance = null ;

  
	@Override
	public void start(Stage primaryStage) {
	    this.primaryStage = primaryStage;
	    this.primaryStage.setTitle("User Management System");
	    instance = this;

	    try {
	        DBManager.getConnection(); 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    initRootLayout();
	    showUserUI();
	}
    public void initRootLayout() {
        try {
        	
        	mainUI = (BorderPane) FXMLLoader.load(getClass().getResource("ui/MainUI.fxml"));

        	Scene scene = new Scene(mainUI);
        	primaryStage.setScene(scene);
        	primaryStage.show();

        } catch (IOException e) { 
        	e.printStackTrace(); 
        }
    }

	
    public void showUserUI() {
    	try {
       
        	userUI = (AnchorPane) FXMLLoader.load(getClass().getResource("ui/UserUI.fxml"));
        	
        	mainUI.setCenter(userUI);
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    //
    public boolean showUserEditUI(User user) {
    	 try {
             FXMLLoader loader = new FXMLLoader();
             loader.setLocation(UMSApplication.class.getResource("ui/UserEditUI.fxml"));
             AnchorPane page = (AnchorPane) loader.load();
          
            
             Stage dialogStage = new Stage();
             dialogStage.setTitle("Ajouter/Modifier un utilisateur");
             dialogStage.initModality(Modality.WINDOW_MODAL);
             dialogStage.initOwner(primaryStage);
             Scene scene = new Scene(page);
             dialogStage.setScene(scene);

             
             UserEditUIController controller = loader.getController();
             controller.setDialogStage(dialogStage);
             
             controller.setUser(user);

             
             dialogStage.showAndWait();

             return controller.isValiderClicked();
         } catch (IOException e) {
             System.err.println(e.getMessage());
            
             return false;
         }
    }


    public Stage getPrimaryStage() {
    	return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}

	public static UMSApplication getInstance() {
		
		return instance ;
	}
}
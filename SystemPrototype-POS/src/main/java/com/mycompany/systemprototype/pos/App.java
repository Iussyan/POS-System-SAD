package com.mycompany.systemprototype.pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.Modality;

public class App extends Application {
    
//    SET FOREIGN_KEY_CHECKS = 0;
//
//    TRUNCATE TABLE `staff`;
//    TRUNCATE TABLE `staff_action_log`;
//    TRUNCATE TABLE `staffcontact`;
//    TRUNCATE TABLE `stafflogs`;
//    TRUNCATE TABLE `staffpersonalinfo`;
//
//    SET FOREIGN_KEY_CHECKS = 1;
    
    private static Scene scene;
    public static Stage popupStage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"));
        stage.setScene(scene);
        stage.setTitle("POS System for Micro-Businesses");
        stage.getIcons().add(new javafx.scene.image.Image("/res/qcu.png"));
        
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    private static Parent loadPopUpFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/popups/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    // New method to create a pop-up stage
    public static void showPopup(String fxml) throws IOException {
        // Load the FXML file for the pop-up scene
        Parent root = loadPopUpFXML(fxml);

        // Create a new stage for the pop-up
        popupStage = new Stage();
        popupStage.setTitle("Popup");  // Set the title of the pop-up window
        popupStage.setResizable(false);
        popupStage.getIcons().add(new javafx.scene.image.Image("/res/qcu.png"));
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make the pop-up modal

        // Set the scene for the pop-up stage
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);

        // Show the pop-up stage
        popupStage.showAndWait(); // Wait for the pop-up to be closed
    }

    public static void main(String[] args) {
        launch();
    }

}

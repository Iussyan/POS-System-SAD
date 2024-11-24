package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class HomeController implements Initializable {

    @FXML
    private JFXButton analytics;

    @FXML
    private BorderPane basePane;

    @FXML
    private JFXButton items;

    @FXML
    private AnchorPane mainStage;

    @FXML
    private JFXButton prodSales;

    @FXML
    private JFXButton stockIn;

    @FXML
    private JFXButton team;
    
    private String loc = "";
    
    public void goTeam() {
        loc = "team";
        loadScene(loc);
    }
    
    public void goProd() {
        loc = "inventory";
        loadScene(loc);
    }
    
    private void loadScene(String fxml) {
        try {
            mainStage.getChildren().clear();
            mainStage.setPrefSize(-1, -1);
            mainStage.setMinSize(100, 100);
                       
            FXMLLoader load = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
            
            Node innNode = load.load();
            innNode.setManaged(true);
            
            mainStage.getChildren().add(innNode);
            
            AnchorPane.setTopAnchor(innNode, 0.0);
            AnchorPane.setLeftAnchor(innNode, 0.0);
            AnchorPane.setBottomAnchor(innNode, 0.0);
            AnchorPane.setRightAnchor(innNode, 0.0);
            
        } catch (IOException e) {
            System.err.println("Error loading scene: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup homeNav = new ToggleGroup();
    }


}

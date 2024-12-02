package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class HomeController implements Initializable {

    @FXML
    private JFXToggleNode analytics;

    @FXML
    private BorderPane basePane;

    @FXML
    private AnchorPane mainStage;

    @FXML
    private ToggleGroup nav;

    @FXML
    private JFXToggleNode prodSales;

    @FXML
    private JFXToggleNode stockIn;

    @FXML
    private JFXToggleNode team;

    @FXML
    private JFXToggleNode transaction;
    
    @FXML
    private FontAwesomeIcon statusIcon;

    @FXML
    private Label statusTxt;
    
    @FXML
    private Label date;
    
    @FXML
    private Label time;
    
    @FXML
    private Label uName;
    
    @FXML
    private Label position;
    
    private String loc = "";
    
    @FXML
    public void goTeam() {
        team.setSelected(true);
        loc = "team";
        loadScene(loc);
    }
    
    @FXML
    public void goStocks() {
        stockIn.setSelected(true);
        loc = "inventory";
        loadScene(loc);
    }
    
    @FXML
    public void goTrans() {
        transaction.setSelected(true);
        loc = "transactions";
        loadScene(loc);
    }
    
    @FXML
    public void goSales() {
        prodSales.setSelected(true);
        loc = "prodSales";
        loadScene(loc);
    }
    
    @FXML
    public void goAnalytics() {
        analytics.setSelected(true);
        loc = "analytics";
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
        nav = new ToggleGroup();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void updateDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dates = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter times = DateTimeFormatter.ofPattern("hh:mm:ss a");
        
        date.setText(currentDateTime.format(dates));
        time.setText(currentDateTime.format(times));
    }

}

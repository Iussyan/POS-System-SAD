package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class ProdSalesController implements Initializable {
    
    @FXML
    private BorderPane basePane;
    
    @FXML
    private JFXToggleNode amountAsc;

    @FXML
    private JFXToggleNode amountDesc;

    @FXML
    private JFXToggleNode itemidAsc;

    @FXML
    private JFXToggleNode itemidDesc;

    @FXML
    private JFXToggleNode nameAsc;

    @FXML
    private JFXToggleNode nameDesc;

    @FXML
    private JFXButton refresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup sort = new ToggleGroup();
    }

}

package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXToggleNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class HomeController implements Initializable {

   @FXML
    private BorderPane basePane;

    @FXML
    private ToggleButton homeBtn;

    @FXML
    private ToggleGroup homeNav;

    @FXML
    private ToggleButton invBtn;

    @FXML
    private ToggleButton notifBtn;

    @FXML
    private ToggleButton optionBtn;

    @FXML
    private ToggleButton staffsBtn;

    @FXML
    private ToggleButton statsBtn;

    @FXML
    private ToggleButton transBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup homeNav = new ToggleGroup();
    }


}

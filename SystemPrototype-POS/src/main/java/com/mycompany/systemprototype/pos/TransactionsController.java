package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class TransactionsController {

    @FXML
    private BorderPane basePane;

    @FXML
    private JFXButton refresh;

    @FXML
    private ToggleGroup report;

    @FXML
    private JFXToggleNode thisMonth;

    @FXML
    private JFXToggleNode thisWeek;

    @FXML
    private JFXToggleNode today;

    @FXML
    void reportThisMonth(ActionEvent event) {

    }

    @FXML
    void reportThisWeek(ActionEvent event) {

    }

    @FXML
    void reportToday(ActionEvent event) {

    }

}
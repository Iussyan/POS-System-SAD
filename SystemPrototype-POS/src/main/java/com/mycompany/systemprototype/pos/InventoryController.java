package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.SearchableComboBox;

public class InventoryController implements Initializable {

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton addNote;

    @FXML
    private JFXToggleNode amountAsc;

    @FXML
    private JFXToggleNode amountDesc;

    @FXML
    private BorderPane basePane;

    @FXML
    private JFXButton editBtn;

    @FXML
    private SearchableComboBox<?> filter;

    @FXML
    private JFXButton filterBtn;

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

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private ToggleGroup sort;

    @FXML
    private TableView<?> tableView;

    @FXML
    void addNotes(ActionEvent event) {

    }

    @FXML
    void addProduct(ActionEvent event) {

    }

    @FXML
    void doFilter(ActionEvent event) {

    }

    @FXML
    void editProduct(ActionEvent event) {

    }

    @FXML
    void enableButton(KeyEvent event) {

    }

    @FXML
    void removeProduct(ActionEvent event) {

    }

    @FXML
    void searchItem(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup sort = new ToggleGroup();
    }

}


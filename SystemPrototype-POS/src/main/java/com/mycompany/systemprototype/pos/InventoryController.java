package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import dbConstructors.ProductConstructor;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.SearchableComboBox;
import secured.DbConnection;
import tableManipulation.DynamicFields;
import tableManipulation.DynamicTableView;

public class InventoryController implements Initializable {

    private Connection con;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton overview;

    @FXML
    private JFXToggleNode amountAsc;

    @FXML
    private JFXToggleNode amountDesc;

    @FXML
    private BorderPane basePane;

    @FXML
    private JFXButton editBtn;

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
    private TableView<ObservableList<String>> tableView;

    public static ProductConstructor pc = new ProductConstructor();
    public static TableView<ObservableList<String>> tableCopy = new TableView<>();

    @FXML
    void prodOverview(ActionEvent event) throws IOException {
        App.showPopup("overview");
        reflectTable();
    }

    @FXML
    void addProduct(ActionEvent event) throws IOException {
        App.showPopup("addProduct");
        reflectTable();
    }

    @FXML
    void editProduct(ActionEvent event) throws IOException {
        App.showPopup("editProduct");
        reflectTable();
    }

    @FXML
    void removeProduct(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("You are about to delete \"" + pc.getProdName().concat("\" !"));
        alert.setContentText("Do you want to proceed?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getDialogPane().getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            deleteProduct(pc.getProdId());
            reflectTable();
        }
    }

    @FXML
    void searchItem(ActionEvent event) {
        if (searchField.getText().trim().isEmpty()) {
            Alert infoAlert = new Alert(AlertType.INFORMATION);
            infoAlert.setTitle("Empty Search!");
            infoAlert.setHeaderText("No text found in search field.");

            infoAlert.showAndWait();
        } else {
            searchProduct(searchField.getText().trim());
        }
    }

    @FXML
    void sortIdAsc(ActionEvent event) {
        itemidAsc.setSelected(true);
        sortTable(true, false, false, true);
    }

    @FXML
    void sortIdDesc(ActionEvent event) {
        itemidDesc.setSelected(true);
        sortTable(true, false, false, false);
    }

    @FXML
    void sortNameAsc(ActionEvent event) {
        nameAsc.setSelected(true);
        sortTable(false, true, false, true);
    }

    @FXML
    void sortNameDesc(ActionEvent event) {
        nameDesc.setSelected(true);
        sortTable(false, true, false, false);
    }

    @FXML
    void sortStockAsc(ActionEvent event) {
        amountAsc.setSelected(true);
        sortTable(false, false, true, true);
    }

    @FXML
    void sortStockDesc(ActionEvent event) {
        amountDesc.setSelected(true);
        sortTable(false, false, true, false);
    }

    @FXML
    void getSelectedColumn(MouseEvent event) {
        ObservableList<String> selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            pc.setProdId(Integer.parseInt(selectedItem.get(0)));
        }

        if (selectedItem != null) {
            overview.setDisable(false);
            editBtn.setDisable(false);
            removeBtn.setDisable(false);
            reflectCell(pc.getProdId());
        } else {
            overview.setDisable(true);
            editBtn.setDisable(true);
            removeBtn.setDisable(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup sort = new ToggleGroup();
        DynamicFields.applyAlphabeticalWithLimit(searchField, 150);
        try {
            con = DbConnection.getConnection();

            PreparedStatement prep = con.prepareStatement("SELECT * FROM generalitemsview");

            ResultSet rs = prep.executeQuery();

            DynamicTableView.populateTable(tableView, rs, tableCopy);

        } catch (Exception e) {
            // Create an error alert
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred while loading data.");
            errorAlert.setContentText(e.getMessage());

            // Show the alert and wait for user interaction
            errorAlert.showAndWait();
        }
    }

    public void reflectTable() {
        try {
            overview.setDisable(true);
            editBtn.setDisable(true);
            removeBtn.setDisable(true);

            con = DbConnection.getConnection();

            PreparedStatement prep = con.prepareStatement("SELECT * FROM generalitemsview");

            ResultSet rs = prep.executeQuery();

            DynamicTableView.populateTable(tableView, rs, tableCopy);
        } catch (Exception e) {
            // Create an error alert
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred while loading data.");
            errorAlert.setContentText(e.getMessage());

            // Show the alert and wait for user interaction
            errorAlert.showAndWait();
        }
    }

    public void reflectCell(int prodID) {
        try {
            con = DbConnection.getConnection();
            PreparedStatement prep = con.prepareStatement("{call sp_reflect_edit_data(?)}");
            prep.setInt(1, prodID);

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                pc.setProdName(rs.getString(1));
                pc.setProdPrice(rs.getDouble(2));
                pc.setProdDesc(rs.getString(3));
                pc.setCatName(rs.getString(4));
                pc.setCatDesc(rs.getString(5));
                pc.setQuantity(rs.getInt(6));
                pc.setLimit(rs.getInt(7));
                pc.setManufactured(rs.getString(8));
                pc.setExpiration(rs.getString(9));
                pc.setSupplier(rs.getString(10));
            }

        } catch (Exception e) {

        }
    }

    public void sortTable(boolean numerical, boolean alpha, boolean stock, boolean asc) {
        String sql = "";
        overview.setDisable(true);
        editBtn.setDisable(true);
        removeBtn.setDisable(true);
        
        if (numerical) {
            if (asc) {
                sql = "SELECT * FROM generalitemsview";
                try {
                    con = DbConnection.getConnection();

                    PreparedStatement prep = con.prepareStatement(sql);

                    ResultSet rs = prep.executeQuery();

                    DynamicTableView.populateTable(tableView, rs, tableCopy);

                } catch (Exception e) {
                    // Create an error alert
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());

                    // Show the alert and wait for user interaction
                    errorAlert.showAndWait();
                }
            } else {
                sql = "SELECT * FROM generalitemsview_iddesc";
                try {
                    con = DbConnection.getConnection();

                    PreparedStatement prep = con.prepareStatement(sql);

                    ResultSet rs = prep.executeQuery();

                    DynamicTableView.populateTable(tableView, rs, tableCopy);

                } catch (Exception e) {
                    // Create an error alert
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());

                    // Show the alert and wait for user interaction
                    errorAlert.showAndWait();
                }
            }
        } else if (alpha) {
            if (asc) {
                sql = "SELECT * FROM generalitemsview_nameasc";
                try {
                    con = DbConnection.getConnection();

                    PreparedStatement prep = con.prepareStatement(sql);

                    ResultSet rs = prep.executeQuery();

                    DynamicTableView.populateTable(tableView, rs, tableCopy);

                } catch (Exception e) {
                    // Create an error alert
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());

                    // Show the alert and wait for user interaction
                    errorAlert.showAndWait();
                };
            } else {
                sql = "SELECT * FROM generalitemsview_namedesc";
                try {
                    con = DbConnection.getConnection();

                    PreparedStatement prep = con.prepareStatement(sql);

                    ResultSet rs = prep.executeQuery();

                    DynamicTableView.populateTable(tableView, rs, tableCopy);

                } catch (Exception e) {
                    // Create an error alert
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());

                    // Show the alert and wait for user interaction
                    errorAlert.showAndWait();
                }
            }
        } else if (stock) {
            if (asc) {
                sql = "SELECT * FROM generalitemsview_stockasc";
                try {
                    con = DbConnection.getConnection();

                    PreparedStatement prep = con.prepareStatement(sql);

                    ResultSet rs = prep.executeQuery();

                    DynamicTableView.populateTable(tableView, rs, tableCopy);

                } catch (Exception e) {
                    // Create an error alert
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());

                    // Show the alert and wait for user interaction
                    errorAlert.showAndWait();
                }
            } else {
                sql = "SELECT * FROM generalitemsview_stockdesc";
                try {
                    con = DbConnection.getConnection();

                    PreparedStatement prep = con.prepareStatement(sql);

                    ResultSet rs = prep.executeQuery();

                    DynamicTableView.populateTable(tableView, rs, tableCopy);

                } catch (Exception e) {
                    // Create an error alert
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());

                    // Show the alert and wait for user interaction
                    errorAlert.showAndWait();
                }
            }
        } else {
            System.out.println("No valid sorting parameters provided.");
        }
    }

    public void searchProduct(String find) {
        String sql = "{call sp_search_products_fulltext(?)}";
        try {
            con = DbConnection.getConnection();

            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, find);

            ResultSet rs = prep.executeQuery();

            DynamicTableView.populateTable(tableView, rs, tableCopy);

        } catch (Exception e) {
            // Create an error alert
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred while loading data.");
            errorAlert.setContentText(e.getMessage());

            // Show the alert and wait for user interaction
            errorAlert.showAndWait();
        }
    }

    public void deleteProduct(int delete) {
        String sql = "DELETE FROM product WHERE ID = ?;";
        try {
            con = DbConnection.getConnection();

            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1, delete);

            ResultSet rs = prep.executeQuery();

            DynamicTableView.populateTable(tableView, rs, tableCopy);

        } catch (Exception e) {
            // Create an error alert
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred while loading data.");
            errorAlert.setContentText(e.getMessage());

            // Show the alert and wait for user interaction
            errorAlert.showAndWait();
        }
    }
}

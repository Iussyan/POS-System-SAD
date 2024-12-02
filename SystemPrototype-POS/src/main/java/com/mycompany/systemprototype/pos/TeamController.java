package com.mycompany.systemprototype.pos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import static com.mycompany.systemprototype.pos.InventoryController.pc;
import static com.mycompany.systemprototype.pos.InventoryController.tableCopy;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import secured.DbConnection;
import tableManipulation.DynamicTableView;

public class TeamController implements Initializable {

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
    
    private Connection con;
    
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
    void overview(ActionEvent event) {
        
    }
    
    @FXML
    void remove(ActionEvent event) {

    }
    
    @FXML
    void edit(ActionEvent event) {

    }
    
    @FXML
    void add(ActionEvent event) {

    }
    
    @FXML
    void selectColumn(MouseEvent event) {

    }

    @FXML
    void getSelectedColumn(MouseEvent event) {
//        ObservableList<String> selectedItem = tableView.getSelectionModel().getSelectedItem();
//
//        if (selectedItem != null) {
//            pc.setProdId(Integer.parseInt(selectedItem.get(0)));
//        }
//
//        if (selectedItem != null) {
//            overview.setDisable(false);
//            editBtn.setDisable(false);
//            removeBtn.setDisable(false);
//            reflectCell(pc.getProdId());
//        } else {
//            overview.setDisable(true);
//            editBtn.setDisable(true);
//            removeBtn.setDisable(true);
//        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup sort = new ToggleGroup();
    }
    
    public void reflectTable() {
//        try {
//            overview.setDisable(true);
//            editBtn.setDisable(true);
//            removeBtn.setDisable(true);
//
//            con = DbConnection.getConnection();
//
//            PreparedStatement prep = con.prepareStatement("SELECT * FROM generalitemsview");
//
//            ResultSet rs = prep.executeQuery();
//
//            DynamicTableView.populateTable(tableView, rs, tableCopy);
//        } catch (Exception e) {
//            // Create an error alert
//            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//            errorAlert.setTitle("Error");
//            errorAlert.setHeaderText("An error occurred while loading data.");
//            errorAlert.setContentText(e.getMessage());
//
//            // Show the alert and wait for user interaction
//            errorAlert.showAndWait();
//        }
    }

    public void reflectCell(int prodID) {
//        try {
//            con = DbConnection.getConnection();
//            PreparedStatement prep = con.prepareStatement("{call sp_reflect_edit_data(?)}");
//            prep.setInt(1, prodID);
//
//            ResultSet rs = prep.executeQuery();
//
//            while (rs.next()) {
//                pc.setProdName(rs.getString(1));
//                pc.setProdPrice(rs.getDouble(2));
//                pc.setProdDesc(rs.getString(3));
//                pc.setCatName(rs.getString(4));
//                pc.setCatDesc(rs.getString(5));
//                pc.setQuantity(rs.getInt(6));
//                pc.setLimit(rs.getInt(7));
//                pc.setManufactured(rs.getString(8));
//                pc.setExpiration(rs.getString(9));
//                pc.setSupplier(rs.getString(10));
//            }
//
//        } catch (Exception e) {
//
//        }
    }

    public void sortTable(boolean numerical, boolean alpha, boolean stock, boolean asc) {
//        String sql = "";
//        overview.setDisable(true);
//        editBtn.setDisable(true);
//        removeBtn.setDisable(true);
//        
//        if (numerical) {
//            if (asc) {
//                sql = "SELECT * FROM generalitemsview";
//                try {
//                    con = DbConnection.getConnection();
//
//                    PreparedStatement prep = con.prepareStatement(sql);
//
//                    ResultSet rs = prep.executeQuery();
//
//                    DynamicTableView.populateTable(tableView, rs, tableCopy);
//
//                } catch (Exception e) {
//                    // Create an error alert
//                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                    errorAlert.setTitle("Error");
//                    errorAlert.setHeaderText("An error occurred while loading data.");
//                    errorAlert.setContentText(e.getMessage());
//
//                    // Show the alert and wait for user interaction
//                    errorAlert.showAndWait();
//                }
//            } else {
//                sql = "SELECT * FROM generalitemsview_iddesc";
//                try {
//                    con = DbConnection.getConnection();
//
//                    PreparedStatement prep = con.prepareStatement(sql);
//
//                    ResultSet rs = prep.executeQuery();
//
//                    DynamicTableView.populateTable(tableView, rs, tableCopy);
//
//                } catch (Exception e) {
//                    // Create an error alert
//                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                    errorAlert.setTitle("Error");
//                    errorAlert.setHeaderText("An error occurred while loading data.");
//                    errorAlert.setContentText(e.getMessage());
//
//                    // Show the alert and wait for user interaction
//                    errorAlert.showAndWait();
//                }
//            }
//        } else if (alpha) {
//            if (asc) {
//                sql = "SELECT * FROM generalitemsview_nameasc";
//                try {
//                    con = DbConnection.getConnection();
//
//                    PreparedStatement prep = con.prepareStatement(sql);
//
//                    ResultSet rs = prep.executeQuery();
//
//                    DynamicTableView.populateTable(tableView, rs, tableCopy);
//
//                } catch (Exception e) {
//                    // Create an error alert
//                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                    errorAlert.setTitle("Error");
//                    errorAlert.setHeaderText("An error occurred while loading data.");
//                    errorAlert.setContentText(e.getMessage());
//
//                    // Show the alert and wait for user interaction
//                    errorAlert.showAndWait();
//                };
//            } else {
//                sql = "SELECT * FROM generalitemsview_namedesc";
//                try {
//                    con = DbConnection.getConnection();
//
//                    PreparedStatement prep = con.prepareStatement(sql);
//
//                    ResultSet rs = prep.executeQuery();
//
//                    DynamicTableView.populateTable(tableView, rs, tableCopy);
//
//                } catch (Exception e) {
//                    // Create an error alert
//                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                    errorAlert.setTitle("Error");
//                    errorAlert.setHeaderText("An error occurred while loading data.");
//                    errorAlert.setContentText(e.getMessage());
//
//                    // Show the alert and wait for user interaction
//                    errorAlert.showAndWait();
//                }
//            }
//        } else if (stock) {
//            if (asc) {
//                sql = "SELECT * FROM generalitemsview_stockasc";
//                try {
//                    con = DbConnection.getConnection();
//
//                    PreparedStatement prep = con.prepareStatement(sql);
//
//                    ResultSet rs = prep.executeQuery();
//
//                    DynamicTableView.populateTable(tableView, rs, tableCopy);
//
//                } catch (Exception e) {
//                    // Create an error alert
//                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                    errorAlert.setTitle("Error");
//                    errorAlert.setHeaderText("An error occurred while loading data.");
//                    errorAlert.setContentText(e.getMessage());
//
//                    // Show the alert and wait for user interaction
//                    errorAlert.showAndWait();
//                }
//            } else {
//                sql = "SELECT * FROM generalitemsview_stockdesc";
//                try {
//                    con = DbConnection.getConnection();
//
//                    PreparedStatement prep = con.prepareStatement(sql);
//
//                    ResultSet rs = prep.executeQuery();
//
//                    DynamicTableView.populateTable(tableView, rs, tableCopy);
//
//                } catch (Exception e) {
//                    // Create an error alert
//                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                    errorAlert.setTitle("Error");
//                    errorAlert.setHeaderText("An error occurred while loading data.");
//                    errorAlert.setContentText(e.getMessage());
//
//                    // Show the alert and wait for user interaction
//                    errorAlert.showAndWait();
//                }
//            }
//        } else {
//            System.out.println("No valid sorting parameters provided.");
//        }
    }

}
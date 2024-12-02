package popupWindows;

import com.jfoenix.controls.JFXButton;
import com.mycompany.systemprototype.pos.App;
import com.mycompany.systemprototype.pos.InventoryController;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import secured.DbConnection;
import tableManipulation.DynamicFields;
import tableManipulation.DynamicTableView;

public class AddProductController implements Initializable {

    private String style;

    @FXML
    private ComboBox<String> catCB;

    @FXML
    private TextArea catDesc;

    @FXML
    private TextField catName;

    @FXML
    private Label existingCat;

    @FXML
    private DatePicker expiration;

    @FXML
    private DatePicker manufactured;

    @FXML
    private JFXButton next1;

    @FXML
    private JFXButton next2;

    @FXML
    private JFXButton prev2;

    @FXML
    private JFXButton previous3;

    @FXML
    private TextArea prodDesc;

    @FXML
    private TextField prodName;

    @FXML
    private TextField prodPrice;

    @FXML
    private JFXButton save;

    @FXML
    private Tab step1;

    @FXML
    private Tab step2;

    @FXML
    private Tab step3;

    @FXML
    private TextField stockLimit;

    @FXML
    private TextField stockSize;

    @FXML
    private TextField supplier;

    @FXML
    void nextBtn1(ActionEvent event) {
        if (!prodName.getText().trim().isEmpty() && !prodPrice.getText().trim().isEmpty() && !prodDesc.getText().trim().isEmpty()) {
            step2.setDisable(false);
            TabPane tabPane = (TabPane) step1.getTabPane();
            tabPane.getSelectionModel().select(step2);

            prodName.setStyle(style);
            prodPrice.setStyle(style);
            prodDesc.setStyle(style);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Wait!");
            alert.setContentText("Input all necessary details first before you can proceed.");

            alert.showAndWait();

            if (prodName.getText().trim().isEmpty()) {
                prodName.setStyle("-fx-border-color: #ff0000");
            }

            if (prodPrice.getText().trim().isEmpty()) {
                prodPrice.setStyle("-fx-border-color: #ff0000");
            }

            if (prodDesc.getText().trim().isEmpty()) {
                prodDesc.setStyle("-fx-border-color: #ff0000");
            }
            step2.setDisable(true);

        }
    }

    @FXML
    void nextBtn2(ActionEvent event) {
        if (!catName.getText().trim().isEmpty() && !catDesc.getText().trim().isEmpty()) {
            catName.setStyle(style);
            catDesc.setStyle(style);

            step3.setDisable(false);
            TabPane tabPane = (TabPane) step2.getTabPane();
            tabPane.getSelectionModel().select(step3);

        } else if (catName.getText().trim().isEmpty() && !catDesc.getText().trim().isEmpty() && catCB.isVisible()) {
            catName.setStyle(style);
            catDesc.setStyle(style);

            step3.setDisable(false);
            TabPane tabPane = (TabPane) step2.getTabPane();
            tabPane.getSelectionModel().select(step3);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Wait!");
            alert.setContentText("Input all necessary details first before you can proceed.");

            alert.showAndWait();

            if (catName.getText().trim().isEmpty()) {
                catName.setStyle("-fx-border-color: #ff0000");
            }

            if (catDesc.getText().trim().isEmpty()) {
                catDesc.setStyle("-fx-border-color: #ff0000");
            }
            step3.setDisable(true);

        }
    }

    @FXML
    void onSave(ActionEvent event) throws Exception {
        System.out.println("here");
        if (!stockSize.getText().trim().isEmpty() && !stockLimit.getText().trim().isEmpty()
                && manufactured.getValue() != null && expiration.getValue() != null
                && !catName.getText().trim().isEmpty() && !catDesc.getText().trim().isEmpty()
                && !prodName.getText().trim().isEmpty() && !prodPrice.getText().trim().isEmpty()
                && !prodDesc.getText().trim().isEmpty() && !supplier.getText().trim().isEmpty()) {
            InventoryController.pc.setCatName(catName.getText().trim());
            System.out.println("still here");
            boolean dateValid = DynamicFields.validateDates(manufactured, expiration);

            if (Integer.valueOf(stockSize.getText().trim()) > Integer.valueOf(stockLimit.getText().trim()) && dateValid) {
                stockSize.setStyle(style);
                stockLimit.setStyle(style);
                manufactured.setStyle(style);
                expiration.setStyle(style);
                supplier.setStyle(style);

                try {
                    Connection con = DbConnection.getConnection();
                    PreparedStatement prep = con.prepareStatement("{call sp_insert_stock_category_product(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

                    java.sql.Date manu = DynamicFields.toSqlDate(manufactured.getValue());
                    java.sql.Date exp = DynamicFields.toSqlDate(expiration.getValue());

                    prep.setInt(1, 1);
                    prep.setString(2, prodName.getText().trim());
                    prep.setDouble(3, Double.parseDouble(prodPrice.getText().trim()));
                    prep.setString(4, prodDesc.getText().trim());
                    prep.setString(5, InventoryController.pc.getCatName());
                    prep.setString(6, catDesc.getText().trim());
                    prep.setInt(7, Integer.parseInt(stockSize.getText().trim()));
                    prep.setDate(8, manu);
                    prep.setDate(9, exp);
                    prep.setInt(10, Integer.parseInt(stockLimit.getText().trim()));
                    prep.setString(11, supplier.getText().trim());

                    int i = prep.executeUpdate();

                    System.out.println("still here madam");

                    if (i > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Notification");
                        alert.setHeaderText("Added " + prodName.getText() + " Successfully!");
                        alert.setContentText(String.format("Product Price : %s "
                                + "%nProduct Description : %s "
                                + "%nCategory Name : %s "
                                + "%nCategory Description : %s "
                                + "%nStock Size : %s "
                                + "%nStock Limit : %s "
                                + "%nManufactured On : %s "
                                + "%nExpiring On : %s "
                                + "%nSupplier : %s ", prodPrice.getText().trim(), prodDesc.getText().trim(),
                                InventoryController.pc.getCatName(), catDesc.getText().trim(), stockSize.getText().trim(),
                                stockLimit.getText().trim(), manufactured.getValue(), expiration.getValue(),
                                supplier.getText().trim()));

                        alert.showAndWait();

                        refreshCB();

                        App.popupStage.close();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("An error occurred while loading data.");
                        errorAlert.setContentText("Insertion Failed.");

                        errorAlert.showAndWait();
                    }
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());
                }
            } else if (Integer.valueOf(stockSize.getText().trim()) <= Integer.valueOf(stockLimit.getText().trim()) && !dateValid) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Error!!");
                alert.setContentText("Stock quantity should be higher than stock limit!");

                alert.showAndWait();

                stockLimit.setStyle("-fx-border-color: #ff0000");

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Notification");
                alert1.setHeaderText("Error!!");
                alert1.setContentText("Expiration date should be after the manufactured date!");

                alert1.showAndWait();

                expiration.setStyle("-fx-border-color: #ff0000");
            } else if (Integer.valueOf(stockSize.getText().trim()) <= Integer.valueOf(stockLimit.getText().trim()) && dateValid) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Notification");
                alert1.setHeaderText("Error!!");
                alert1.setContentText("Expiration date should be after the manufactured date!");

                alert1.showAndWait();

                expiration.setStyle("-fx-border-color: #ff0000");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Error!!");
                alert.setContentText("Stock quantity should be higher than stock limit!");

                alert.showAndWait();

                stockLimit.setStyle("-fx-border-color: #ff0000");
            }

        } else if (!stockSize.getText().trim().isEmpty() && !stockLimit.getText().trim().isEmpty()
                && manufactured.getValue() != null && expiration.getValue() != null
                && catName.getText().trim().isEmpty() && !catDesc.getText().trim().isEmpty()
                && !prodName.getText().trim().isEmpty() && !prodPrice.getText().trim().isEmpty()
                && !prodDesc.getText().trim().isEmpty() && !supplier.getText().trim().isEmpty() && catCB.isVisible()) {
            InventoryController.pc.setCatName(catCB.getSelectionModel().getSelectedItem());

            boolean dateValid = DynamicFields.validateDates(manufactured, expiration);

            if (Integer.valueOf(stockSize.getText().trim()) > Integer.valueOf(stockLimit.getText().trim()) && dateValid) {
                stockSize.setStyle(style);
                stockLimit.setStyle(style);
                manufactured.setStyle(style);
                expiration.setStyle(style);
                supplier.setStyle(style);

                try {
                    Connection con = DbConnection.getConnection();
                    PreparedStatement prep = con.prepareStatement("{call sp_insert_stock_category_product(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

                    java.sql.Date manu = DynamicFields.toSqlDate(manufactured.getValue());
                    java.sql.Date exp = DynamicFields.toSqlDate(expiration.getValue());

                    prep.setInt(1, 1);
                    prep.setString(2, prodName.getText().trim());
                    prep.setDouble(3, Double.parseDouble(prodPrice.getText().trim()));
                    prep.setString(4, prodDesc.getText().trim());
                    prep.setString(5, InventoryController.pc.getCatName());
                    prep.setString(6, InventoryController.pc.getCatDesc());
                    prep.setInt(7, Integer.parseInt(stockSize.getText().trim()));
                    prep.setDate(8, manu);
                    prep.setDate(9, exp);
                    prep.setInt(10, Integer.parseInt(stockLimit.getText().trim()));
                    prep.setString(11, supplier.getText().trim());

                    int i = prep.executeUpdate();

                    if (i > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Notification");
                        alert.setHeaderText("Added " + prodName.getText() + " Successfully!");
                        alert.setContentText(String.format("Product Price : %s "
                                + "%nProduct Description : %s "
                                + "%nCategory Name : %s "
                                + "%nCategory Description : %s "
                                + "%nStock Size : %s "
                                + "%nStock Limit : %s "
                                + "%nManufactured On : %s "
                                + "%nExpiring On : %s "
                                + "%nSupplier : %s ", prodPrice.getText().trim(), prodDesc.getText().trim(),
                                InventoryController.pc.getCatName(), catDesc.getText().trim(), stockSize.getText().trim(),
                                stockLimit.getText().trim(), manufactured.getValue(), expiration.getValue(),
                                supplier.getText().trim()));

                        alert.showAndWait();

                        refreshCB();

                        App.popupStage.close();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("An error occurred while loading data.");
                        errorAlert.setContentText("Insertion Failed.");
                    }
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("An error occurred while loading data.");
                    errorAlert.setContentText(e.getMessage());
                }
            } else if (Integer.valueOf(stockSize.getText().trim()) <= Integer.valueOf(stockLimit.getText().trim()) && !dateValid) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Error!!");
                alert.setContentText("Stock quantity should be higher than stock limit!");

                alert.showAndWait();

                stockLimit.setStyle("-fx-border-color: #ff0000");

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Notification");
                alert1.setHeaderText("Error!!");
                alert1.setContentText("Expiration date should be after the manufactured date!");

                alert1.showAndWait();

                expiration.setStyle("-fx-border-color: #ff0000");
            } else if (Integer.valueOf(stockSize.getText().trim()) <= Integer.valueOf(stockLimit.getText().trim()) && dateValid) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Notification");
                alert1.setHeaderText("Error!!");
                alert1.setContentText("Expiration date should be after the manufactured date!");

                alert1.showAndWait();

                expiration.setStyle("-fx-border-color: #ff0000");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Error!!");
                alert.setContentText("Stock quantity should be higher than stock limit!");

                alert.showAndWait();

                stockLimit.setStyle("-fx-border-color: #ff0000");
            }

        } else if (stockSize.getText().trim().isEmpty() || stockLimit.getText().trim().isEmpty()
                || manufactured.getValue() == null || expiration.getValue() == null || supplier.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Wait!");
            alert.setContentText("Input all necessary details first before you can proceed.");

            alert.showAndWait();

            if (stockSize.getText().trim().isEmpty()) {
                stockSize.setStyle("-fx-border-color: #ff0000");
            }

            if (stockLimit.getText().trim().isEmpty()) {
                stockLimit.setStyle("-fx-border-color: #ff0000");
            }

            if (manufactured.getValue() == null) {
                manufactured.setStyle("-fx-border-color: #ff0000");
            }

            if (expiration.getValue() == null) {
                expiration.setStyle("-fx-border-color: #ff0000");
            }

            if (supplier.getText().trim().isEmpty()) {
                supplier.setStyle("-fx-border-color: #ff0000");
            }

        } else {
            stockSize.setStyle(style);
            stockLimit.setStyle(style);
            manufactured.setStyle(style);
            expiration.setStyle(style);
            supplier.setStyle(style);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Wait you have missed important details!");
            alert.setContentText("Input all necessary details first before you can proceed. \n\nGoing back to Step 1.");

            alert.showAndWait();

            if (prodName.getText().trim().isEmpty()) {
                prodName.setStyle("-fx-border-color: #ff0000");
            }

            if (prodPrice.getText().trim().isEmpty()) {
                prodPrice.setStyle("-fx-border-color: #ff0000");
            }

            if (prodDesc.getText().trim().isEmpty()) {
                prodDesc.setStyle("-fx-border-color: #ff0000");
            }

            if (catName.getText().trim().isEmpty()) {
                catName.setStyle("-fx-border-color: #ff0000");
            }

            if (catDesc.getText().trim().isEmpty()) {
                catDesc.setStyle("-fx-border-color: #ff0000");
            }

            if (stockSize.getText().trim().isEmpty()) {
                stockSize.setStyle("-fx-border-color: #ff0000");
            }

            if (stockLimit.getText().trim().isEmpty()) {
                stockLimit.setStyle("-fx-border-color: #ff0000");
            }

            if (manufactured.getValue() == null) {
                manufactured.setStyle("-fx-border-color: #ff0000");
            }

            if (expiration.getValue() == null) {
                expiration.setStyle("-fx-border-color: #ff0000");
            }

            if (supplier.getText().trim().isEmpty()) {
                supplier.setStyle("-fx-border-color: #ff0000");
            }

            TabPane tabPane = (TabPane) step3.getTabPane();
            tabPane.getSelectionModel().select(step1);

            step2.setDisable(true);
            step3.setDisable(true);

        }
    }

    @FXML
    void prevBtn2(ActionEvent event) {
        TabPane tabPane = (TabPane) step2.getTabPane();
        tabPane.getSelectionModel().select(step1);
    }

    @FXML
    void prevBtn3(ActionEvent event) {
        TabPane tabPane = (TabPane) step3.getTabPane();
        tabPane.getSelectionModel().select(step2);
    }

    @FXML
    void selectedCB(ActionEvent event) {
        if (catCB.getSelectionModel().getSelectedItem() != null) {
            catName.setDisable(true);
            catName.clear();
            catDesc.setDisable(true);

            String cName = catCB.getSelectionModel().getSelectedItem();
            String desc = "";

            try {
                Connection con = DbConnection.getConnection();

                PreparedStatement prep = con.prepareStatement("SELECT Description FROM category WHERE CatName = ?");

                prep.setString(1, cName);

                ResultSet rs = prep.executeQuery();

                while (rs.next()) {
                    desc = rs.getString(1);
                }
                catDesc.setText(desc);
                InventoryController.pc.setCatDesc(desc);
            } catch (Exception e) {
            }

        } else {
            catName.setDisable(false);
            catDesc.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        style = prodName.getStyle();
        step2.setDisable(true);
        step3.setDisable(true);

        manufactured.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        expiration.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        DynamicFields.applyAlphabeticalWithLimit(prodName, 120);
        DynamicFields.applyTextFormatter(prodPrice, DynamicFields.FormatType.PRICE);
        DynamicFields.applyAlphabeticalSpecialWithLimitArea(prodDesc, 300);

        DynamicFields.applyAlphabeticalWithLimit(catName, 120);
        DynamicFields.applyAlphabeticalSpecialWithLimitArea(catDesc, 300);

        DynamicFields.applyTextFormatter(stockSize, DynamicFields.FormatType.LIMIT);
        DynamicFields.applyTextFormatter(stockLimit, DynamicFields.FormatType.LIMIT);
        DynamicFields.applyAlphabeticalWithLimit(supplier, 120);

        DynamicFields.setDatePickerFormat(manufactured);
        DynamicFields.setDatePickerFormat(expiration);

        refreshCB();
    }

    public void refreshCB() {
        try {
            Connection con = DbConnection.getConnection();

            PreparedStatement prep = con.prepareStatement("SELECT `CatName` FROM `category`");

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                if (!rs.getString(1).isEmpty()) {
                    existingCat.setVisible(true);
                    DynamicFields.populateComboBox(catCB, rs);
                    catCB.setVisible(true);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

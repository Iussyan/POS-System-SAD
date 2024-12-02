package popupWindows;

import com.mycompany.systemprototype.pos.InventoryController;
import java.lang.invoke.VarHandle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class OverviewController implements Initializable {

    @FXML
    private BorderPane basePane;

    @FXML
    private TextArea catDesc;

    @FXML
    private TextField catName;

    @FXML
    private TextField expiration;

    @FXML
    private TextField limit;

    @FXML
    private TextField manufactured;

    @FXML
    private TextArea prDesc;

    @FXML
    private TextField prID;

    @FXML
    private TextField prName;

    @FXML
    private TextField prPrice;

    @FXML
    private TextField quantity;

    @FXML
    private TextField supplier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prID.setText(String.valueOf(InventoryController.pc.getProdId()));
        prName.setText(InventoryController.pc.getProdName());
        prPrice.setText(String.valueOf(InventoryController.pc.getProdPrice()));
        prDesc.setText(InventoryController.pc.getProdDesc());
        
        catName.setText(InventoryController.pc.getCatName());
        catDesc.setText(InventoryController.pc.getCatDesc());
        
        quantity.setText(String.valueOf(InventoryController.pc.getQuantity()));
        limit.setText(String.valueOf(InventoryController.pc.getLimit()));
        manufactured.setText(InventoryController.pc.getManufactured());
        expiration.setText(InventoryController.pc.getExpiration());
        supplier.setText(InventoryController.pc.getSupplier());
    }

}

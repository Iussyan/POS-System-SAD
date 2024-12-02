module com.mycompany.systemprototype.pos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.systemprototype.pos to javafx.fxml;
    opens popupWindows to javafx.fxml;
    exports com.mycompany.systemprototype.pos;
    exports popupWindows;
    exports secured;
    exports tableManipulation;
    exports dbConstructors;
    
    requires com.jfoenix;
    requires fontawesomefx;
    requires org.controlsfx.controls;
    requires java.sql;
}

module FISK {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    opens ihm to javafx.fxml;
    exports ihm;

}
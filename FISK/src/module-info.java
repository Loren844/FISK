module FISK {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires MaterialFX;

    opens ihm to javafx.fxml;
    exports ihm;

}
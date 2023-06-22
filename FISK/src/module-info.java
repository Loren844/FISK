module FISK {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires MaterialFX;
    requires java.sql;

    opens ihm to javafx.fxml;
    exports ihm;
}
package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;

public class TitleMenuListeners {
    @FXML
    public void onPlayButtonClick(ActionEvent event) throws IOException {
        // Chargement du fichier FXML
        FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("menu_titre.fxml"));
        Parent root = loadMenu.load();

        // Affichage de la scène
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loadMap = new FXMLLoader(getClass().getResource("menu_map.fxml"));
        Scene map = new Scene(loadMap.load());
        stage.setScene(map);
    }
}
package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfilListeners {

    @FXML
    public void onRetourButtonClick(ActionEvent event) throws IOException {
        // Chargement du fichier FXML
        FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("parametrage_partie.fxml"));
        Parent root = loadMenu.load();

        // Affichage de la scène
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loadMap = new FXMLLoader(getClass().getResource("menu_titre.fxml"));
        Scene map = new Scene(loadMap.load());
        stage.setScene(map);
        stage.setFullScreen(true);
    }
}
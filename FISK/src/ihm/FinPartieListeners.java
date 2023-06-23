package ihm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.jeu.Partie;
import org.w3c.dom.events.Event;

import javafx.event.ActionEvent;
import java.io.IOException;

public class FinPartieListeners {

    public void onRetourMenuClick(ActionEvent event) throws IOException {
        Partie.resetPartie();
        // Chargement du fichier FXML
        FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("fin_partie.fxml"));
        Parent root = loadMenu.load();

        // Affichage de la scène
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loadMap = new FXMLLoader(getClass().getResource("menu_titre.fxml"));
        Scene map = new Scene(loadMap.load());
        stage.setScene(map);
        stage.setFullScreen(true);
    }

    public void onQuitterClick(ActionEvent event) {
        System.exit(0);
    }
}

package ihm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Lanceur extends Application {

    @Override
    public void start(Stage fenetre) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Lanceur.class.getResource("menu_titre.fxml"));
        Scene menuTitre = new Scene(fxmlLoader.load());
        fenetre.setTitle("FISK");
        fenetre.setScene(menuTitre);
        fenetre.setFullScreenExitHint("");
        fenetre.show();
    }

    public static void main(String[] args) {
        launch();

    }
}

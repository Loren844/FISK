package ihm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.*;

public class ProfilListeners {
    public static String pseudo;

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

    public void onRechercherClick(MouseEvent event) throws IOException, SQLException {
        ImageView source = (ImageView) event.getSource();
        Scene scene = source.getScene();

        MFXTextField rechercheJoueur = (MFXTextField) scene.lookup("#rechercheJoueur");
        String pseudoAdvers = rechercheJoueur.getText();

        //Connexion a la bdd
        String url = "jdbc:mysql://192.168.143.162:3306/FISK_BDD";
        String utilisateur = "fisk";
        String motDePasse = "fiskCgenial";
        Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

        //Récupération de NbParties dans la BDD
        String requeteNbParties = "Select count(*)\n" +
                "from JOUEURS_PARTIES AS JP1 Inner join JOUEURS_PARTIES AS JP2 ON (JP1.IdPartie = JP2.IdPartie)\n" +
                "where JP1.IdJoueur = (select IdJoueur from JOUEURS where Pseudo = ?) AND JP2.IdJoueur = (select IdJoueur from JOUEURS where Pseudo = ?)";
        PreparedStatement statementNbParties = connexion.prepareStatement(requeteNbParties);
        statementNbParties.setString(1, pseudo);
        statementNbParties.setString(2, pseudoAdvers);
        ResultSet resultSetNbParties = statementNbParties.executeQuery();
        int nombreParties=0;
        if (resultSetNbParties.next())
        {
            nombreParties = resultSetNbParties.getInt(1);
        }
        statementNbParties.close();

        //Récupération de NbVictoires dans la BDD
        String requeteNbVictoires = "SELECT COUNT(*)\n" +
                "FROM JOUEURS_PARTIES AS JP1\n" +
                "INNER JOIN JOUEURS_PARTIES AS JP2 ON JP1.IdPartie = JP2.IdPartie\n" +
                "INNER JOIN PARTIES ON JP1.IdPartie = PARTIES.IdPartie\n" +
                "WHERE JP1.IdJoueur = (SELECT IdJoueur FROM JOUEURS WHERE Pseudo = ?)\n" +
                "  AND JP2.IdJoueur = (SELECT IdJoueur FROM JOUEURS WHERE Pseudo = ?)\n" +
                "  AND PARTIES.IdGagnant = JP1.IdJoueur;\n";
        PreparedStatement statementNbVictoires = connexion.prepareStatement(requeteNbVictoires);
        statementNbVictoires.setString(1, pseudo);
        statementNbVictoires.setString(2, pseudoAdvers);
        ResultSet resultSetNbVictoires = statementNbVictoires.executeQuery();
        int nbVictoires=0;
        if (resultSetNbVictoires.next())
        {
            nbVictoires = resultSetNbVictoires.getInt(1);
        }
        statementNbVictoires.close();

        Label nbParties2 = (Label) scene.lookup("#nbParties2");
        nbParties2.setText(String.valueOf(nombreParties));
        Label nbPartiesGagnees2 = (Label) scene.lookup("#nbPartiesGagnees2");
        nbPartiesGagnees2.setText(String.valueOf(nbVictoires));
        Label pourcentageVictoire2 = (Label) scene.lookup("#pourcentageVictoire2");
        if(nombreParties==0)
        {
            pourcentageVictoire2.setText("N/A");
        }
        else
        {
            float pourcentage = (float) Math.round((nbVictoires*100.0)/nombreParties);
            pourcentageVictoire2.setText(""+pourcentage+"%");
        }
    }
}

package ihm;


import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ConnexionsListeners {

    public void onCreerButtonClick(MouseEvent event) throws IOException, SQLException
    {
        MFXButton source = (MFXButton) event.getSource();
        Scene scene = source.getScene();

        MFXTextField creerPseudo = (MFXTextField) scene.lookup("#creerPseudo");
        MFXPasswordField creerMdp = (MFXPasswordField) scene.lookup("#creerMdp");
        MFXPasswordField creerConfirm = (MFXPasswordField) scene.lookup("#creerConfirm");

        String pseudo = creerPseudo.getText();
        String mdp = creerMdp.getText();
        String confirm = creerConfirm.getText();

        if(mdp.equals(confirm))
        {
            //Connexion a la bdd
            String url = "jdbc:mysql://192.168.143.162:3306/FISK_BDD";
            String utilisateur = "fisk";
            String motDePasse = "fiskCgenial";
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            //Verification de la dispo du pseudo
            String verif = "SELECT * from JOUEURS where Pseudo=?";
            PreparedStatement statementVerif = connexion.prepareStatement(verif);
            statementVerif.setString(1,pseudo);
            ResultSet resultat = statementVerif.executeQuery();

            if(resultat.next())
            {
                System.out.println("Erreur");
                statementVerif.close();
            }

            else
            {
                statementVerif.close();

                //Creation du joueur dans la bdd
                String requete = "INSERT INTO JOUEURS (Pseudo, Mdp) VALUES (?, ?)";
                PreparedStatement statement = connexion.prepareStatement(requete);
                statement.setString(1, pseudo);
                statement.setString(2, mdp);
                statement.executeUpdate();
                statement.close();

                //Ouverture du profil
                ouvertureProfil(source,pseudo);
            }
            connexion.close();
        }


    }

    public void onConnecterButtonClick(MouseEvent event) throws IOException, SQLException
    {
        MFXButton source = (MFXButton) event.getSource();
        Scene scene = source.getScene();

        MFXTextField connecterPseudo = (MFXTextField) scene.lookup("#connecterPseudo");
        MFXPasswordField connecterMdp = (MFXPasswordField) scene.lookup("#connecterMdp");

        String pseudo = connecterPseudo.getText();
        String mdp = connecterMdp.getText();

        //Connexion a la bdd
        String url = "jdbc:mysql://192.168.143.162:3306/FISK_BDD";
        String utilisateur = "fisk";
        String motDePasse = "fiskCgenial";
        Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

        //Récupération du mdp
        String verifMdp = "SELECT Mdp from JOUEURS where Pseudo=?";
        PreparedStatement statementVerifMdp = connexion.prepareStatement(verifMdp);
        statementVerifMdp.setString(1,pseudo);
        ResultSet resultSetVerifMdp = statementVerifMdp.executeQuery();

        //Vérification du mdp
        if (resultSetVerifMdp.next())
        {
            String mdpBDD = resultSetVerifMdp.getString("Mdp");
            statementVerifMdp.close();

            if(mdp.equals(mdpBDD))
            {
                ouvertureProfil(source,pseudo);
            }
            else
            {
                System.out.println("Mauvais mdp");
            }
        }
    }

    public void ouvertureProfil(MFXButton source, String pseudo) throws IOException, SQLException
    {
        // Chargement du fichier FXML
        FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("connexions.fxml"));
        Parent root = loadMenu.load();

        // Affichage de la scène
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loadMap = new FXMLLoader(getClass().getResource("profil.fxml"));
        Scene map = new Scene(loadMap.load());
        stage.setScene(map);
        stage.setFullScreen(true);

        Scene scene = stage.getScene();

        //Connexion a la bdd
        String url = "jdbc:mysql://192.168.143.162:3306/FISK_BDD";
        String utilisateur = "fisk";
        String motDePasse = "fiskCgenial";
        Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

        //Récupération des données dans la BDD
        String requeteNbParties = "SELECT count(*) from JOUEURS_PARTIES where IdJoueur = (SELECT IdJoueur from JOUEURS where Pseudo=?);";
        PreparedStatement statementNbParties = connexion.prepareStatement(requeteNbParties);
        statementNbParties.setString(1, pseudo);
        ResultSet resultSetNbParties = statementNbParties.executeQuery();
        int nombreParties=0;
        if (resultSetNbParties.next())
        {
            nombreParties = resultSetNbParties.getInt(1);
        }
        statementNbParties.close();

        String requeteNbPartiesGagnees = "SELECT count(*) from PARTIES where IdGagnant = (SELECT IdJoueur from JOUEURS where Pseudo=?);";
        PreparedStatement statementNbPartiesGagnees = connexion.prepareStatement(requeteNbPartiesGagnees);
        statementNbPartiesGagnees.setString(1, pseudo);
        ResultSet resultSetNbPartiesGagnees = statementNbPartiesGagnees.executeQuery();
        int nombrePartiesGagnees =0;
        if (resultSetNbPartiesGagnees.next())
        {
            nombrePartiesGagnees = resultSetNbPartiesGagnees.getInt(1);
        }
        statementNbPartiesGagnees.close();

        //Récupération des labels
        Label labelNbParties1 = (Label) scene.lookup("#nbParties1");
        Label labelNbPartiesGagnees1 = (Label) scene.lookup("#nbPartiesGagnees1");
        Label labelPourcentageVictoire1 = (Label) scene.lookup("#pourcentageVictoire1");

        //MAJ des labels
        labelNbPartiesGagnees1.setText(""+nombrePartiesGagnees);
        labelNbParties1.setText(""+nombreParties);
        if(nombreParties==0)
        {
            labelPourcentageVictoire1.setText("N/A");
        }
        else
        {
            float pourcentage = (float) (nombrePartiesGagnees*100.0)/nombreParties;
            labelPourcentageVictoire1.setText(""+pourcentage+"%");
        }

        connexion.close();
    }
}

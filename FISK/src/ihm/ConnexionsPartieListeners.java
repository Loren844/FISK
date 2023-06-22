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
import modele.jeu.Partie;

import java.io.IOException;
import java.sql.*;

public class ConnexionsPartieListeners {

    public void onCreerButtonClick(MouseEvent event) throws IOException, SQLException
    {
        MFXButton source = (MFXButton) event.getSource();
        Scene scene = source.getScene();

        Label erreurPseudoMdp = (Label) scene.lookup("#erreurPseudoMdp");
        Label erreurPseudo = (Label) scene.lookup("#erreurPseudo");
        Label erreurMdp = (Label) scene.lookup("#erreurMdp");

        erreurPseudoMdp.setVisible(false);
        erreurPseudo.setVisible(false);
        erreurMdp.setVisible(false);

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
                erreurPseudo.setVisible(true);
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

                //stocker l'id du joueur
                String requeteIdJoueur = "SELECT IdJoueur from JOUEURS where Pseudo=?";
                PreparedStatement statementIdJoueur = connexion.prepareStatement(requeteIdJoueur);
                statementIdJoueur.setString(1,pseudo);
                ResultSet resultSetIdJoueur = statementIdJoueur.executeQuery();

                if (resultSetIdJoueur.next())
                {
                    Partie.getJoueursRestants()[Partie.getNbJoueursBDD()].setPseudo(pseudo);
                    Partie.setIdJoueurBDD(resultSetIdJoueur.getInt(1));
                }


                int nbJoueurs = Partie.nbJoueursRestants();
                if(Partie.getNbJoueursBDD() < nbJoueurs)
                {
                    creerPseudo.clear();
                    creerMdp.clear();
                    creerConfirm.clear();
                    Label numJoueur = (Label) scene.lookup("#numJoueur");
                    int nJoueur = Partie.getNbJoueursBDD() + 1 ;
                    numJoueur.setText("Joueur " + nJoueur);
                }

                else
                {
                    //ouvrir partie
                    // Chargement du fichier FXML
                    FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("connexions_partie.fxml"));
                    Parent root = loadMenu.load();

                    // Affichage de la scène
                    Stage stage = (Stage) source.getScene().getWindow();
                    FXMLLoader loadMap = new FXMLLoader(getClass().getResource("jeu.fxml"));
                    Scene map = new Scene(loadMap.load());
                    stage.setScene(map);
                    stage.setFullScreen(true);
                }
            }
            connexion.close();
        }
        else
        {
            erreurMdp.setVisible(true);
        }


    }

    public void onConnecterButtonClick(MouseEvent event) throws IOException, SQLException
    {
        MFXButton source = (MFXButton) event.getSource();
        Scene scene = source.getScene();

        Label erreurPseudoMdp = (Label) scene.lookup("#erreurPseudoMdp");
        Label erreurPseudo = (Label) scene.lookup("#erreurPseudo");
        Label erreurMdp = (Label) scene.lookup("#erreurMdp");

        erreurPseudoMdp.setVisible(false);
        erreurPseudo.setVisible(false);
        erreurMdp.setVisible(false);

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


                //récupérer l'id du joueur
                String requeteIdJoueur = "SELECT IdJoueur from JOUEURS where Pseudo=?";
                PreparedStatement statementIdJoueur = connexion.prepareStatement(requeteIdJoueur);
                statementIdJoueur.setString(1,pseudo);
                ResultSet resultSetIdJoueur = statementIdJoueur.executeQuery();

                if (resultSetIdJoueur.next())
                {
                    //verifier que le joueur n'est pas déjà connecté
                    int idJoueurBDD = resultSetIdJoueur.getInt(1);
                    int i = 0;
                    while(i < 4 && idJoueurBDD != Partie.getIdJoueursBDD()[i])
                    {
                        i++;
                    }

                    if(i == 4)
                    {
                        Partie.getJoueursRestants()[Partie.getNbJoueursBDD()].setPseudo(pseudo);
                        Partie.setIdJoueurBDD(resultSetIdJoueur.getInt(1));
                    }
                    else
                    {
                        erreurPseudoMdp.setText("Le joueur est déjà connecté");
                        erreurPseudoMdp.setVisible(true);
                    }
                }

                int nbJoueurs = Partie.nbJoueursRestants();
                if(Partie.getNbJoueursBDD() < nbJoueurs)
                {
                    connecterPseudo.clear();
                    connecterMdp.clear();
                    Label numJoueur = (Label) scene.lookup("#numJoueur");
                    int nJoueur = Partie.getNbJoueursBDD() + 1 ;
                    numJoueur.setText("Joueur " + nJoueur);
                }

                else
                {
                    //ouvrir partie
                    // Chargement du fichier FXML
                    FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("connexions_partie.fxml"));
                    Parent root = loadMenu.load();

                    // Affichage de la scène
                    Stage stage = (Stage) source.getScene().getWindow();
                    FXMLLoader loadMap = new FXMLLoader(getClass().getResource("jeu.fxml"));
                    Scene map = new Scene(loadMap.load());
                    stage.setScene(map);
                    stage.setFullScreen(true);
                }
            }
            else
            {
                erreurPseudoMdp.setText("Pseudo/Mot de passe incorrect");
                erreurPseudoMdp.setVisible(true);
            }
        }
    }
}

package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modele.carte.Carte;
import modele.jeu.Joueur;
import modele.jeu.Partie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParametrageListeners {
    private static boolean clicProspere = false;
    private static boolean clicImmediate = false;
    private static boolean clicDeuxJoueurs = false;
    private static boolean clicTroisJoueurs = false;
    private static boolean clicQuatreJoueurs = false;
    private static boolean creerCliquable = false;

    @FXML
    public void onRetourButtonClick(ActionEvent event) throws IOException
    {
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

    @FXML
    public void onCreerButtonClick(ActionEvent event) throws IOException {
        if (creerCliquable)
        {
            // Chargement du fichier FXML
            FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("parametrage_partie.fxml"));
            Parent root = loadMenu.load();

            // Affichage de la scène
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            FXMLLoader loadMap = new FXMLLoader(getClass().getResource("connexions_partie.fxml"));
            Scene map = new Scene(loadMap.load());
            stage.setScene(map);
            stage.setFullScreen(true);

            //initialisation de la partie
            int nbJoueurs = 2;
            int nbToursMax = -1;
            if(clicTroisJoueurs)
            {
                nbJoueurs = 3;
            }
            else if(clicQuatreJoueurs)
            {
                nbJoueurs = 4;
            }

            if(clicImmediate)
            {
                nbToursMax = 30;
            }

            Carte carte = new Carte();

            int posTirage;
            List listTirages = new ArrayList();
            for(int i = 0; i < 38; i++)
            {
                listTirages.add(i);
            }

            Joueur.setNbInstances(0);
            Joueur[] joueurs = new Joueur[nbJoueurs];

            if(nbJoueurs == 2)
            {
                //Creation des joueurs
                for(int i = 0; i < nbJoueurs; i++)
                {
                    //tirage des agences
                    int[] idAgences = new int[38];
                    //initialiser le tableau avec des -1
                    for(int j = 0; j < 38; j++)
                    {
                        idAgences[j] = -1;
                    }

                    for(int j = 0; j < 19; j++)
                    {
                        posTirage = (int) (Math.random() * (listTirages.size()));
                        idAgences[j] = (int) listTirages.get(posTirage);
                        listTirages.remove(posTirage);
                    }

                    joueurs[i] = new Joueur(idAgences);
                    //placement aléatoire des banquiers restants
                    for(int j = 0; j < 31; j++)
                    {
                        int posAgenceAlea = (int) (Math.random() * (19));
                        joueurs[i].getAgences()[posAgenceAlea].setNbBanquiers(joueurs[i].getAgences()[posAgenceAlea].getNbBanquiers()+1);
                    }
                }
            }

            else if(nbJoueurs == 3)
            {
                for(int i = 0; i < nbJoueurs; i++)
                {
                    int[] idAgences = new int[38];
                    //initialiser le tableau avec des -1
                    for(int j = 0; j < 38; j++)
                    {
                        idAgences[j] = -1;
                    }

                    for(int j = 0; j < 12; j++)
                    {
                        posTirage = (int) (Math.random() * (listTirages.size()));
                        idAgences[j] = (int) listTirages.get(posTirage);
                        listTirages.remove(posTirage);

                    }

                    if(i!=0)
                    {
                        posTirage = (int) (Math.random() * (listTirages.size()));
                        idAgences[12] = (int) listTirages.get(posTirage);
                        listTirages.remove(posTirage);
                    }
                    joueurs[i] = new Joueur(idAgences);

                    //placement aléatoire des banquiers restants
                    int nbAgences = 13;
                    if(i == 0)
                    {
                        nbAgences = 12;
                    }

                    for(int j = 0; j < 35-nbAgences; j++)
                    {
                        int posAgenceAlea = (int) (Math.random() * (nbAgences));
                        joueurs[i].getAgences()[posAgenceAlea].setNbBanquiers(joueurs[i].getAgences()[posAgenceAlea].getNbBanquiers()+1);
                    }

                }
            }

            else if(nbJoueurs == 4) {
                for (int i = 0; i < nbJoueurs; i++) {
                    int[] idAgences = new int[38];
                    //initialiser le tableau avec des -1
                    for(int j = 0; j < 38; j++)
                    {
                        idAgences[j] = -1;
                    }

                    for (int j = 0; j < 9; j++) {
                        posTirage = (int) (Math.random() * (listTirages.size()));
                        idAgences[j] = (int) listTirages.get(posTirage);
                        listTirages.remove(posTirage);
                    }

                    if (i > 1) {
                        posTirage = (int) (Math.random() * (listTirages.size()));
                        idAgences[9] = (int) listTirages.get(posTirage);
                        listTirages.remove(posTirage);
                    }
                    joueurs[i] = new Joueur(idAgences);

                    //placement aléatoire des banquiers restants
                    int nbAgences = 10;
                    if(i < 2)
                    {
                        nbAgences = 9;
                    }

                    for(int j = 0; j < 30-nbAgences; j++)
                    {
                        int posAgenceAlea = (int) (Math.random() * (nbAgences));
                        joueurs[i].getAgences()[posAgenceAlea].setNbBanquiers(joueurs[i].getAgences()[posAgenceAlea].getNbBanquiers()+1);
                    }
                }
            }
            JeuListeners.initJeu(joueurs, nbToursMax);
        }
    }

    @FXML
    public void onImmediateClick(ActionEvent event) throws IOException{
        Button immediateMode = (Button) event.getSource();
        Button prospereMode = (Button) immediateMode.getScene().lookup("#modeRichesseProspere");
        Button creer = (Button) immediateMode.getScene().lookup("#boutonCreer");
        if(clicImmediate)
        {
            clicImmediate = false;
            creer.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            creerCliquable = false;
            immediateMode.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
        else{
            if(clicDeuxJoueurs || clicTroisJoueurs || clicQuatreJoueurs)
            {
                creerCliquable = true;
                creer.setStyle("-fx-background-color: #43ab43; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            }
            clicProspere = false;
            clicImmediate = true;
            prospereMode.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            immediateMode.setStyle("-fx-background-color: #fce14d; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
    }

    @FXML
    public void onProspereClick(ActionEvent event) throws IOException{
        Button prospereMode = (Button) event.getSource();
        Button immediateMode = (Button) prospereMode.getScene().lookup("#modeRichesseImmediate");
        Button creer = (Button) prospereMode.getScene().lookup("#boutonCreer");
        if(clicProspere)
        {
            creerCliquable = false;
            creer.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            clicProspere = false;
            prospereMode.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
        else{
            if(clicDeuxJoueurs || clicTroisJoueurs || clicQuatreJoueurs)
            {
                creerCliquable = true;
                creer.setStyle("-fx-background-color: #43ab43; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            }
            clicProspere = true;
            clicImmediate = false;
            immediateMode.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            prospereMode.setStyle("-fx-background-color: #fce14d; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
    }

    @FXML
    public void on2JoueursClick(ActionEvent event) throws IOException{
        Button deuxJoueurs = (Button) event.getSource();
        Button troisJoueurs = (Button) deuxJoueurs.getScene().lookup("#nbJoueurs3");
        Button quatreJoueurs = (Button) deuxJoueurs.getScene().lookup("#nbJoueurs4");
        Button creer = (Button) deuxJoueurs.getScene().lookup("#boutonCreer");
        if(clicDeuxJoueurs)
        {
            creerCliquable = false;
            creer.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            clicDeuxJoueurs = false;
            deuxJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
        else{
            if(clicImmediate || clicProspere)
            {
                creerCliquable = true;
                creer.setStyle("-fx-background-color: #43ab43; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            }
            clicDeuxJoueurs = true;
            clicTroisJoueurs = false;
            clicQuatreJoueurs = false;
            troisJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            quatreJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            deuxJoueurs.setStyle("-fx-background-color: #fce14d; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
    }

    @FXML
    public void on3JoueursClick(ActionEvent event) throws IOException{
        Button troisJoueurs = (Button) event.getSource();
        Button deuxJoueurs = (Button) troisJoueurs.getScene().lookup("#nbJoueurs2");
        Button quatreJoueurs = (Button) troisJoueurs.getScene().lookup("#nbJoueurs4");
        Button creer = (Button) troisJoueurs.getScene().lookup("#boutonCreer");
        if(clicTroisJoueurs)
        {
            creerCliquable = false;
            creer.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            clicTroisJoueurs = false;
            troisJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
        else{
            if(clicImmediate || clicProspere)
            {
                creerCliquable = true;
                creer.setStyle("-fx-background-color: #43ab43; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            }
            clicTroisJoueurs = true;
            clicDeuxJoueurs = false;
            clicQuatreJoueurs = false;
            deuxJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            quatreJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            troisJoueurs.setStyle("-fx-background-color: #fce14d; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
    }

    @FXML
    public void on4JoueursClick(ActionEvent event) throws IOException{
        Button quatreJoueurs = (Button) event.getSource();
        Button deuxJoueurs = (Button) quatreJoueurs.getScene().lookup("#nbJoueurs2");
        Button troisJoueurs = (Button) quatreJoueurs.getScene().lookup("#nbJoueurs3");
        Button creer = (Button) quatreJoueurs.getScene().lookup("#boutonCreer");
        if(clicQuatreJoueurs)
        {
            creerCliquable = false;
            creer.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            clicQuatreJoueurs = false;
            quatreJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
        else{
            if(clicImmediate || clicProspere)
            {
                creerCliquable = true;
                creer.setStyle("-fx-background-color: #43ab43; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            }
            clicQuatreJoueurs = true;
            clicDeuxJoueurs = false;
            clicTroisJoueurs = false;
            deuxJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            troisJoueurs.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
            quatreJoueurs.setStyle("-fx-background-color: #fce14d; -fx-background-radius: 5px; -fx-border-width: 6px; -fx-border-radius: 8px; -fx-border-insets: -6px; -fx-border-color: rgba(0,0,0,0.5);");
        }
    }
}

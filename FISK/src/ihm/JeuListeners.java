package ihm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modele.carte.Agence;
import modele.carte.Carte;
import modele.jeu.AgentFisk;
import modele.jeu.Joueur;
import modele.jeu.Partie;
import io.github.palexdev.materialfx.controls.MFXSlider;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class JeuListeners {
    private boolean vue = true; //false = vue normale, true = vue des villes
    private boolean banquiers = false;
    private boolean placements = false;
    private boolean agentFisk = false;
    private String polySourceId = null;
    private String polyDestId = null;
    private boolean transfertEffectue = false;

    public static void initJeu(Joueur[] joueurs, int nbToursMax) throws IOException
    {
        //Création de la partie
        Partie partie = new Partie(nbToursMax, joueurs);
        AgentFisk.setProchainEvenement();
    }

    public void onAiderButtonClick(MouseEvent event) throws URISyntaxException, IOException {
        String url = "https://loren844.github.io/FISK/src/regles_fisk.pdf";
        Desktop.getDesktop().browse(new URI(url));
    }

    @FXML
    public void onAccederButtonClick(MouseEvent event) throws IOException
    {
        Button acceder = (Button) event.getSource();
        Scene scene = acceder.getScene();

        supprMessageIntro(scene);

        afficheAdvers(scene);

        //Initialisation du compteur
        Label cpt = (Label) scene.lookup("#labelCpt");
        if(Partie.getNbToursMax() == 30)
        {
            cpt.setText("0  3  0");
        }

        //Changement de la vue
        Circle btnVue = (Circle) scene.lookup("#btnVue");
        btnVue.fireEvent(event);

        //Changement du nombre de banquiers
        for(Joueur joueur : Partie.getJoueursRestants())
        {
            for(Agence agence : joueur.getAgences())
            {
                if(agence != null)
                {
                    int id = agence.getId();
                    Label nbBanquiers = (Label) scene.lookup("#l" + id);
                    nbBanquiers.setText(""+agence.getNbBanquiers());
                }
            }
        }

        //mettre a jour infos joueurs
        Partie.setArgentParTour(Partie.getJoueurActuel());
        Partie.getJoueurActuel().setArgentDispo(Partie.getJoueurActuel().getArgentDispo() + Partie.getJoueurActuel().getArgentParTour());

        majInfosAdvers(scene);
        majInfosJoueur(scene);
    }

    @FXML
    public void onLabelClick(MouseEvent event) throws IOException
    {
        Label clique = (Label) event.getSource();
        String polyId = "#p" + clique.getId().substring(1);
        Polygon polySource = (Polygon) clique.getScene().lookup(polyId);
        polySource.fireEvent(event);
    }

    @FXML
    public void onPolygonClick(MouseEvent event) throws IOException
    {
        Polygon clique = (Polygon) event.getSource();
        Scene scene = clique.getScene();

        if(vue == false)
        {
            //si phase achat un seul polygone cliquable
            if(Partie.getPhase() == 1 && Partie.getJoueurActuel().getNbBanquiersDispo() > 0)
            {
                //verifier que le polygone est une agence du joueur
                if(Partie.getJoueurActuel().possede("#"+clique.getId()))
                {

                    //si aucun polygone n'a été cliqué
                    if (polySourceId == null) {
                        afficherBandeau(scene);
                        polySourceId = "#" + clique.getId();
                        effetsPolygonSelect(event);
                    }

                    //si le polygone est déjà cliqué
                    else if(polySourceId.equals("#"+clique.getId()))
                    {
                        desafficherBandeau(scene);
                        effetsPolygonDefaut(scene, polySourceId);
                        polySourceId = null;
                    }

                    //si un polygone a été cliqué avant
                    else {
                        effetsPolygonDefaut(scene, polySourceId);
                        afficherBandeau(scene);
                        polySourceId = "#" + clique.getId();
                        effetsPolygonSelect(event);
                    }
                }
            }
            else if(Partie.getPhase() == 2)
            {
                //aucun polygone cliqué
                if(polySourceId == null)
                {
                    String polyClique = "#" + clique.getId();
                    int idClique = Integer.parseInt(polyClique.substring(2));
                    Agence agenceClique = Carte.getAgenceById(idClique);

                    //l'agence doit avoir plus d'un banquier sur l'agence
                    if(agenceClique.getNbBanquiers() > 1)
                    {
                        //le polygone cliqué doit appartenir au joueur
                        if(Partie.getJoueurActuel().possede("#"+clique.getId()))
                        {
                            polySourceId = "#" + clique.getId();
                            effetsPolygonSelect(event);
                        }
                    }
                }

                //seul polysource est cliqué
                else if(polyDestId == null)
                 {
                    String polyClique = "#" + clique.getId();
                    int idClique = Integer.parseInt(polyClique.substring(2));
                    Agence agenceClique = Carte.getAgenceById(idClique);

                    //le polygone appartient au joueur
                    if(Partie.getJoueurActuel().possede(polyClique))
                    {
                        if(agenceClique.getNbBanquiers() > 1)
                        {
                            //si le polygone est déjà cliqué
                            if(polySourceId.equals(polyClique))
                            {
                                effetsPolygonDefaut(scene, polySourceId);
                                polySourceId = null;
                            }

                            //si un polygone a été cliqué avant
                            else {
                                effetsPolygonDefaut(scene, polySourceId);
                                polySourceId = "#" + clique.getId();
                                effetsPolygonSelect(event);
                            }
                        }
                    }

                    else if(agenceClique.estFrontaliere(polySourceId))
                    {
                        afficherBandeau(scene);
                        effetsPolygonSelect(event);
                        polyDestId = polyClique;
                    }
                }
                else
                {
                    String polyClique = "#" + clique.getId();
                    int idClique = Integer.parseInt(polyClique.substring(2));
                    Agence agenceClique = Carte.getAgenceById(idClique);

                    //si l'agence est attaquable depuis polySource, polyDest est écrasée
                    if( !(Partie.getJoueurActuel().possede(polyClique)) && agenceClique.estFrontaliere(polySourceId) )
                    {
                        effetsPolygonDefaut(scene, polyDestId);
                        polyDestId = polyClique;
                        effetsPolygonSelect(event);
                        effetsPolygonSelect(scene, polySourceId);
                    }

                    //si c'est une agence du joueur, polySource est écrasée et polyDest supprimée
                    else if(Partie.getJoueurActuel().possede(polyClique))
                    {
                        if(agenceClique.getNbBanquiers() > 1)
                        {
                            effetsPolygonDefaut(scene, polyDestId);
                            polyDestId = null;
                            desafficherBandeau(scene);

                            if(polyClique.equals(polySourceId))
                            {
                                effetsPolygonDefaut(scene, polySourceId);
                                polySourceId = null;
                            }
                            else
                            {
                                effetsPolygonDefaut(scene, polySourceId);
                                polySourceId = polyClique;
                                effetsPolygonSelect(event);
                            }
                        }
                    }
                }
            }
            else if(Partie.getPhase() == 3) {
                if(!transfertEffectue)
                {
                    String polyClique = "#" + clique.getId();
                    int idClique = Integer.parseInt(polyClique.substring(2));
                    Agence agenceClique = Carte.getAgenceById(idClique);

                    //vérifier que le polygone appartient au joueur
                    if(Partie.getJoueurActuel().possede(polyClique))
                    {
                        //aucun polygone cliqué
                        if(polySourceId == null)
                        {
                            //l'agence doit avoir plus d'un banquier sur l'agence
                            if(agenceClique.getNbBanquiers() > 1)
                            {
                                polySourceId = "#" + clique.getId();
                                effetsPolygonSelect(event);
                            }
                        }

                        //seul polysource est cliqué
                        else if(polyDestId == null)
                        {
                            //si le polygone est déjà cliqué
                            if (polySourceId.equals(polyClique))
                            {
                                effetsPolygonDefaut(scene, polySourceId);
                                polySourceId = null;
                            }

                            //si le transfert est possible
                            else if(Carte.getAgenceById(Integer.parseInt(polySourceId.substring(2))).transfertPossible(idClique))
                            {
                                polyDestId = "#" + clique.getId();
                                effetsPolygonSelect(event);
                                afficherBandeau(scene);
                            }

                            else{
                                if(agenceClique.getNbBanquiers() > 1)
                                {
                                    effetsPolygonDefaut(scene, polySourceId);
                                    polySourceId = "#" + clique.getId();
                                    effetsPolygonSelect(event);
                                }
                            }
                        }

                        else
                        {
                            //si le polygone est déjà cliqué
                            if (polySourceId.equals(polyClique))
                            {
                                effetsPolygonDefaut(scene, polySourceId);
                                polySourceId = null;
                                effetsPolygonDefaut(scene, polyDestId);
                                polyDestId = null;
                                desafficherBandeau(scene);
                            }

                            else if(Carte.getAgenceById(Integer.parseInt(polySourceId.substring(2))).transfertPossible(idClique))
                            {
                                effetsPolygonDefaut(scene, polyDestId);
                                polyDestId = "#" + clique.getId();
                                effetsPolygonSelect(event);
                                afficherBandeau(scene);
                            }

                            else if(Carte.getAgenceById(idClique).getNbBanquiers() > 1)
                            {
                                effetsPolygonDefaut(scene, polyDestId);
                                polyDestId = null;
                                effetsPolygonDefaut(scene, polySourceId);
                                polySourceId = "#" + clique.getId();
                                effetsPolygonSelect(event);
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void onImgClick(MouseEvent event) throws IOException
    {
        ImageView clique = (ImageView) event.getSource();
        String btnId = "#btn" + clique.getId().substring(3);
        Circle btnSource = (Circle) clique.getScene().lookup(btnId);
        btnSource.fireEvent(event);
    }

    public void onVueClick(MouseEvent event) throws IOException
    {
        Circle clique = (Circle) event.getSource();
        Scene scene = clique.getScene();
        if(vue == false)
        {
            vue = true;

            if(polySourceId != null)
            {
                desafficherBandeau(scene);
                effetsPolygonDefaut(scene, polySourceId);
                polySourceId = null;
            }

            for(int i = 0; i < 38; i++)
            {
                String id="#p"+i;
                Polygon polygon = (Polygon) scene.lookup(id);
                if (i > 31)
                {
                    polygon.setFill(Paint.valueOf("#26ff1f"));
                }

                else if (i > 25)
                {
                    polygon.setFill(Paint.valueOf("#c0ff1c"));
                }

                else if (i > 19)
                {
                    polygon.setFill(Paint.valueOf("#1fbfff"));
                }

                else if (i > 10)
                {
                    polygon.setFill(Paint.valueOf("#2bffce"));
                }

                else if (i > 5)
                {
                    polygon.setFill(Paint.valueOf("#ffffff"));
                }
                else {

                    polygon.setFill(Paint.valueOf("#4f65ff"));
                }
                String labelId = "#l" + i;
                Label label = (Label) scene.lookup(labelId);
                label.setVisible(false);
            }
        }


        else
        {
            vue = false;
            int cpt = 0;
            for(Joueur joueur:Partie.getJoueursRestants())
            {
                for(Agence a:joueur.getAgences())
                {
                    if(a != null)
                    {
                        String polyId="#p"+a.getId();
                        Polygon polygon = (Polygon) scene.lookup(polyId);
                        polygon.setFill(Paint.valueOf(joueur.getCouleur()));
                    }
                }
            }

            for(int i = 0; i < 38; i++)
            {
                String labelId = "#l" + i;
                Label label = (Label) scene.lookup(labelId);
                label.setVisible(true);
            }
        }
    }

    public void onSuivClick(MouseEvent event) throws IOException, SQLException {
        Circle source = (Circle) event.getSource();
        Scene scene = source.getScene();

        //Si fin des 3 phases d'un joueur
        if(Partie.getPhase() == 3)
        {
            //Si nouveau tour
            if(Partie.getJoueurSuivant().equals(Partie.getJoueursRestants()[0]))
            {
                //tous les 3 tours
                if((Partie.getTour() % 3 == 0 && Partie.getNbToursMax() == -1) || (Partie.getTour() % 3 == 1 && Partie.getNbToursMax() == 30))
                {
                    if(AgentFisk.getAgenceCible() != null)
                    {
                        String labelId =  "#l" + AgentFisk.getAgenceCible().getId();
                        Label label = (Label) scene.lookup(labelId);
                        label.setTextFill(Color.BLACK);
                    }
                    AgentFisk.setProchainEvenement();
                }

                if(Partie.aUnGagnant() != null)
                {
                    Joueur gagnant = Partie.aUnGagnant();
                    //afficher écran de fin de partie
                    // Chargement du fichier FXML
                    FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("jeu.fxml"));
                    Parent root = loadMenu.load();

                    // Affichage de la scène
                    Stage stage = (Stage) scene.getWindow();
                    FXMLLoader loadMap = new FXMLLoader(getClass().getResource("fin_partie.fxml"));
                    Scene map = new Scene(loadMap.load());
                    stage.setScene(map);
                    stage.setFullScreen(true);
                    Label label = (Label) map.lookup("#labelMessage");
                    label.setText(gagnant.getPseudo() + " , avez-vous pensé à vous lancer dans le monde des affaires ? Vous venez de prouver votre superiorité sur le monde en gagnant une partie de FISK, ce jeu fantastique.");



                    //Connexion a la bdd
                    String url = "jdbc:mysql://192.168.143.162:3306/FISK_BDD";
                    String utilisateur = "fisk";
                    String motDePasse = "fiskCgenial";
                    Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

                    //Récupération de l'id max de PARTIES
                    String requeteIdPartie = "SELECT max(IdPartie) FROM PARTIES";
                    PreparedStatement statementIdPartie = connexion.prepareStatement(requeteIdPartie);
                    ResultSet resultSetIdPartie= statementIdPartie.executeQuery();

                    int maxId = -1;
                    if(resultSetIdPartie.next())
                    {
                        maxId = resultSetIdPartie.getInt(1);
                    }
                    statementIdPartie.close();

                    //Enregistrement de la partie
                    String requeteEnr = "INSERT INTO PARTIES (IdGagnant,IdPartie) VALUES (?,?)";
                    PreparedStatement statementEnr = connexion.prepareStatement(requeteEnr);
                    statementEnr.setString(1, String.valueOf(Partie.getIdJoueursBDD()[gagnant.getIdJoueur()]));
                    statementEnr.setString(2, String.valueOf(maxId+1));
                    statementEnr.executeUpdate();
                    statementEnr.close();

                    //MAJ JOUEURS_PARTIES
                    for(int i=0; i<Partie.getNbJoueursBDD();i++)
                    {
                        String requeteEnrPartJ = "INSERT INTO JOUEURS_PARTIES (IdJoueur,IdPartie) VALUES (?,?)";
                        PreparedStatement statementEnrPartJ = connexion.prepareStatement(requeteEnrPartJ);
                        statementEnrPartJ.setString(1, String.valueOf(Partie.getIdJoueursBDD()[i]));
                        statementEnrPartJ.setString(2, String.valueOf(maxId+1));
                        statementEnrPartJ.executeUpdate();
                        statementEnrPartJ.close();
                    }

                    connexion.close();

                }
                else{
                    nouveauTour(scene);
                }

            }

            changementJoueur(scene, event);

        }

        else
        {
            //conditions de sortie de la phase d'achats
            if(Partie.getPhase() == 1)
            {
                if(Partie.getJoueurActuel().getNbBanquiersDispo() != 0)
                {
                    affichagePopUp(scene, 1);
                }
                else
                {
                    changementPhase(scene);
                }
            }

            else if(Partie.getPhase() == 2)
            {
                if(polyDestId != null) {
                    effetsPolygonDefaut(scene, polyDestId);
                    polyDestId = null;
                    effetsPolygonDefaut(scene, polySourceId);
                    polySourceId = null;
                }

                else if(polySourceId != null)
                {
                    effetsPolygonDefaut(scene, polySourceId);
                    polySourceId = null;
                }
                changementPhase(scene);
            }

            else {
                changementPhase(scene);
            }
        }

        //fermer le menu d'achat
        desafficherBandeau(scene);
        desafficherMenuBanquiers(scene);
        desafficherMenuPlacements(scene);
        ImageView refAgentFisk = (ImageView) scene.lookup("#refAgentFisk");
        refAgentFisk.fireEvent(event);
    }

    public void onValCond1Click(MouseEvent event) throws IOException
    {
        ImageView source = (ImageView) event.getSource();
        Scene scene = source.getScene();

        activerBoutons(scene);
        //desaffichage du pop up
        Rectangle rectCond1 = (Rectangle) scene.lookup("#rectCond1");
        Label textCond1 = (Label) scene.lookup("#textCond1");
        ImageView valCond1 = (ImageView) scene.lookup("#valCond1");

        rectCond1.setVisible(false);
        textCond1.setVisible(false);
        valCond1.setVisible(false);

        ImageView fond = (ImageView) scene.lookup("#bgRegles");
        fond.setVisible(false);
    }

    public void onBanquiersClick(MouseEvent event) throws IOException
    {
        if(Partie.getPhase() == 1)
        {
            Circle source = (Circle) event.getSource();
            Scene scene = source.getScene();

            if(placements)
            {
                Circle placements = (Circle) scene.lookup("#btnPlacements");
                placements.fireEvent(event);
            }
            else if(agentFisk)
            {
                Circle agentFisk = (Circle) scene.lookup("#btnAgentFisk");
                agentFisk.fireEvent(event);
            }

            Rectangle barre = (Rectangle) scene.lookup("#barreBanquiers");
            ImageView refuser = (ImageView) scene.lookup("#refBanquiers");
            ImageView valider = (ImageView) scene.lookup("#valBanquiers");
            MFXSlider jauge = (MFXSlider) scene.lookup("#jaugeBanquiers");

            //si la barre etait ouverte
            if(banquiers)
            {
                banquiers = false;
                barre.setVisible(false);
                refuser.setVisible(false);
                valider.setVisible(false);
                jauge.setVisible(false);
            }

            //si la barre etait fermée
            else
            {
                banquiers = true;
                barre.setVisible(true);
                refuser.setVisible(true);
                valider.setVisible(true);
                jauge.setVisible(true);

                jauge.setMin(0);
                jauge.setMax(Partie.getJoueurActuel().getArgentDispo()/1000);
            }
        }
    }

    public void onRefBanquiersClick(MouseEvent event) throws IOException
    {
        ImageView source = (ImageView) event.getSource();
        Scene scene = source.getScene();

        desafficherMenuBanquiers(scene);
    }

    public void onValBanquiersClick(MouseEvent event) throws IOException
    {
        ImageView valBanquiers = (ImageView) event.getSource();
        Scene scene = valBanquiers.getScene();
        MFXSlider jaugeBanquiers = (MFXSlider) scene.lookup("#jaugeBanquiers");

        //récupérer le nombre de banquiers selectionnés
        int nbBanquiersRecrut = (int) jaugeBanquiers.getValue();

        //enlever l'argent
        Partie.getJoueurActuel().setArgentDispo(Partie.getJoueurActuel().getArgentDispo() - nbBanquiersRecrut * 1000);

        //ajouter le nombre de banquiers
        Partie.getJoueurActuel().setNbBanquiersDispo(Partie.getJoueurActuel().getNbBanquiersDispo() + nbBanquiersRecrut);

        //actualiser l'affichage
        Label labelNbBanquiers = (Label) scene.lookup("#labelNbBanquiers");
        Label labelDispo = (Label) scene.lookup("#labelDispo");

        labelNbBanquiers.setText("" +Partie.getJoueurActuel().getNbBanquiersDispo());
        labelDispo.setText("" + Partie.getJoueurActuel().getArgentDispo() + " $");

        //fermer la barre
        desafficherMenuBanquiers(scene);
    }

    public void onPlacementsClick(MouseEvent event) throws IOException
    {
        if(Partie.getPhase() == 1)
        {
            Circle source = (Circle) event.getSource();
            Scene scene = source.getScene();

            if(banquiers)
            {
                Circle banquiers = (Circle) scene.lookup("#btnBanquiers");
                banquiers.fireEvent(event);
            }
            else if(agentFisk)
            {
                Circle agentFisk = (Circle) scene.lookup("#btnAgentFisk");
                agentFisk.fireEvent(event);
            }

            Rectangle barre = (Rectangle) scene.lookup("#barrePlacements");
            ImageView refuser = (ImageView) scene.lookup("#refPlacements");
            ImageView valider = (ImageView) scene.lookup("#valPlacements");
            MFXSlider jauge = (MFXSlider) scene.lookup("#jaugePlacements");

            //si la barre etait ouverte
            if(placements)
            {
                placements = false;
                barre.setVisible(false);
                refuser.setVisible(false);
                valider.setVisible(false);
                jauge.setVisible(false);
            }

            //si la barre etait fermée
            else
            {
                placements = true;
                barre.setVisible(true);
                refuser.setVisible(true);
                valider.setVisible(true);
                jauge.setVisible(true);

                jauge.setUnitIncrement(100);
                jauge.valueProperty().addListener((observable, oldValue, newValue) -> {
                    int roundedValue = (int) Math.round(newValue.doubleValue() / 100) * 100;
                    jauge.setValue(roundedValue);
                });
                jauge.setMax(Partie.getJoueurActuel().getArgentDispo());
            }
        }
    }

    public void onRefPlacementsClick(MouseEvent event) throws IOException
    {
        ImageView source = (ImageView) event.getSource();
        Scene scene = source.getScene();

        desafficherMenuPlacements(scene);
    }

    public void onValPlacementsClick(MouseEvent event) throws IOException
    {
        ImageView valPlacements = (ImageView) event.getSource();
        Scene scene = valPlacements.getScene();
        MFXSlider jaugePlacements = (MFXSlider) scene.lookup("#jaugePlacements");

        //récupérer la somme d'argent placée
        int argentPlace = (int) jaugePlacements.getValue();

        //enlever l'argent
        Partie.getJoueurActuel().setArgentDispo(Partie.getJoueurActuel().getArgentDispo() - argentPlace);

        //ajouter la somme aux placements
        Partie.getJoueurActuel().placerArgent(argentPlace);

        //actualiser l'argent par tour
        actArgentParTour(scene);

        //actualiser l'affichage
        Label labelPlace = (Label) scene.lookup("#labelPlace");
        Label labelDispo = (Label) scene.lookup("#labelDispo");

        labelPlace.setText("" +Partie.getJoueurActuel().getArgentPlace() + " $");
        labelDispo.setText("" + Partie.getJoueurActuel().getArgentDispo() + " $");

        //fermer la barre
        desafficherMenuPlacements(scene);
    }

    public void onAgentFiskClick(MouseEvent event) throws IOException
    {
        if(Partie.getPhase() == 1 && !Partie.getJoueurActuel().isInfoAchetee())
        {
            Circle source = (Circle) event.getSource();
            Scene scene = source.getScene();

            if(banquiers)
            {
                Circle banquiers = (Circle) scene.lookup("#btnBanquiers");
                banquiers.fireEvent(event);
            }
            else if(placements)
            {
                Circle placements = (Circle) scene.lookup("#btnPlacements");
                placements.fireEvent(event);
            }

            Rectangle barre = (Rectangle) scene.lookup("#barreAgentFisk");
            ImageView refuser = (ImageView) scene.lookup("#refAgentFisk");
            ImageView valider = (ImageView) scene.lookup("#valAgentFisk");
            Label label = (Label) scene.lookup("#labelAgentFisk");

            //mettre le prix a jour
            label.setText(AgentFisk.getPrixInfo() + " $");

            if(agentFisk)
            {
                agentFisk = false;
                barre.setVisible(false);
                refuser.setVisible(false);
                valider.setVisible(false);
                label.setVisible(false);
            }
            else
            {
                agentFisk = true;
                barre.setVisible(true);
                refuser.setVisible(true);
                valider.setVisible(true);
                label.setVisible(true);
            }
        }
    }

    public void onValAgentFiskClick(MouseEvent event) throws IOException
    {
        ImageView source = (ImageView) event.getSource();
        Scene scene = source.getScene();

        if(Partie.getJoueurActuel().getArgentDispo() >= AgentFisk.getPrixInfo())
        {
            Partie.getJoueurActuel().setArgentDispo(Partie.getJoueurActuel().getArgentDispo()-AgentFisk.getPrixInfo());
            Partie.getJoueurActuel().setInfoAchetee(true);
            actualiserAgentFisk(scene);
            majInfosJoueur(scene);
            majInfosAdvers(scene);

            Rectangle barre = (Rectangle) scene.lookup("#barreAgentFisk");
            ImageView refuser = (ImageView) scene.lookup("#refAgentFisk");
            ImageView valider = (ImageView) scene.lookup("#valAgentFisk");
            Label label = (Label) scene.lookup("#labelAgentFisk");

            agentFisk = false;
            barre.setVisible(false);
            refuser.setVisible(false);
            valider.setVisible(false);
            label.setVisible(false);
        }

        else
        {
            Rectangle barre = (Rectangle) scene.lookup("#barreAgentFisk");
            ImageView refuser = (ImageView) scene.lookup("#refAgentFisk");
            ImageView valider = (ImageView) scene.lookup("#valAgentFisk");
            Label label = (Label) scene.lookup("#labelAgentFisk");

            agentFisk = false;
            barre.setVisible(false);
            refuser.setVisible(false);
            valider.setVisible(false);
            label.setVisible(false);
            affichagePopUp(scene, 2);
        }
    }

    public void onRefAgentFiskClick(MouseEvent event) throws IOException
    {
        ImageView source = (ImageView) event.getSource();
        Scene scene = source.getScene();

        Rectangle barre = (Rectangle) scene.lookup("#barreAgentFisk");
        ImageView refuser = (ImageView) scene.lookup("#refAgentFisk");
        ImageView valider = (ImageView) scene.lookup("#valAgentFisk");
        Label label = (Label) scene.lookup("#labelAgentFisk");

        agentFisk = false;
        barre.setVisible(false);
        refuser.setVisible(false);
        valider.setVisible(false);
        label.setVisible(false);
    }

    public void onRefBandeauClick(MouseEvent event) throws IOException
    {
        ImageView refBandeau = (ImageView) event.getSource();
        Scene scene = refBandeau.getScene();
        desafficherBandeau(scene);
        effetsPolygonDefaut(scene, polySourceId);
        polySourceId = null;
        if(polyDestId != null)
        {
            effetsPolygonDefaut(scene, polyDestId);
            polyDestId = null;
        }
    }

    public void onValBandeauClick(MouseEvent event) throws IOException, SQLException {
        ImageView valBandeau = (ImageView) event.getSource();
        Scene scene = valBandeau.getScene();

        if(Partie.getPhase() == 1)
        {
            validBandeauPhase1(scene);
        }

        else if(Partie.getPhase() == 2)
        {
            validBandeauPhase2(scene);
        }

        else
        {
            validBandeauPhase3(scene);
        }

    }

    //-------------------------------------------------------------------------------------------------------------------------------------//
    public void supprMessageIntro(Scene scene)
    {
        ImageView fond = (ImageView) scene.lookup("#bgRegles");
        fond.setVisible(false);
        ImageView logo = (ImageView) scene.lookup("#logoRegles");
        logo.setVisible(false);
        Rectangle rectangle = (Rectangle) scene.lookup("#rectRegles");
        rectangle.setVisible(false);
        Label msgRegles1 = (Label) scene.lookup("#msgRegles1");
        msgRegles1.setVisible(false);
        Label msgRegles2 = (Label) scene.lookup("#msgRegles2");
        msgRegles2.setVisible(false);
        MFXButton bouton = (MFXButton) scene.lookup("#boutonAcceder");
        bouton.setVisible(false);
        ImageView imgAide = (ImageView) scene.lookup("#imgAide1");
        imgAide.setVisible(false);
        ImageView imgAide2 = (ImageView) scene.lookup("#imgAide2");
        imgAide2.setVisible(true);
    }

    public void afficherBandeau(Scene scene)
    {
        Rectangle rectBandeau = (Rectangle) scene.lookup("#rectBandeau");
        Label labelBandeau = (Label) scene.lookup("#labelBandeau");
        ImageView valBandeau = (ImageView) scene.lookup("#valBandeau");
        ImageView refBandeau = (ImageView) scene.lookup("#refBandeau");
        MFXSlider jaugeBandeau = (MFXSlider) scene.lookup("#jaugeBandeau");
        rectBandeau.setVisible(true);
        labelBandeau.setVisible(true);
        valBandeau.setVisible(true);
        refBandeau.setVisible(true);
        jaugeBandeau.setVisible(true);

        if(Partie.getPhase() == 1)
        {
            jaugeBandeau.setMax(Partie.getJoueurActuel().getNbBanquiersDispo());
        }

        else
        {
            int idClique = Integer.parseInt(polySourceId.substring(2));
            Agence agenceClique = Carte.getAgenceById(idClique);

            jaugeBandeau.setMin(1);
            jaugeBandeau.setMax(agenceClique.getNbBanquiers()-1);
        }
    }

    public void desafficherBandeau(Scene scene)
    {
        Rectangle rectBandeau = (Rectangle) scene.lookup("#rectBandeau");
        Label labelBandeau = (Label) scene.lookup("#labelBandeau");
        ImageView valBandeau = (ImageView) scene.lookup("#valBandeau");
        ImageView refBandeau = (ImageView) scene.lookup("#refBandeau");
        MFXSlider jaugeBandeau = (MFXSlider) scene.lookup("#jaugeBandeau");
        rectBandeau.setVisible(false);
        labelBandeau.setVisible(false);
        valBandeau.setVisible(false);
        refBandeau.setVisible(false);
        jaugeBandeau.setVisible(false);
    }

    public void effetsPolygonDefaut(Scene scene, String polyId)
    {
        Polygon poly = (Polygon) scene.lookup(polyId);
        poly.setStroke(Color.BLACK);
        poly.setStrokeWidth(3);
    }

    public void effetsPolygonSelect(MouseEvent event)
    {
        Polygon poly = (Polygon) event.getSource();
        poly.setStroke(Color.WHITE);
        poly.setStrokeWidth(6);
        poly.toFront();
        String polyId = poly.getId().substring(1);
        Label l = (Label) poly.getScene().lookup("#l" + polyId);
        l.toFront();
    }

    public void effetsPolygonSelect(Scene scene, String polyId)
    {
        Polygon poly = (Polygon) scene.lookup(polyId);
        poly.setStroke(Color.WHITE);
        poly.setStrokeWidth(6);
        poly.toFront();
        polyId = poly.getId().substring(1);
        Label l = (Label) poly.getScene().lookup("#l" + polyId);
        l.toFront();
    }

    public void nouveauTour(Scene scene)
    {
        //Si mode = richesse prospere
        if(Partie.getNbToursMax() == -1)
        {
            Partie.setTour(Partie.getTour()+1);
        }
        //Si mode = richesse immediate
        else
        {
            Partie.setTour(Partie.getTour()-1);
        }

        String tour = String.valueOf(Partie.getTour());
        while(tour.length() != 3)
        {
            tour = "0" + tour;
        }

        //incrémenter/décrémenter le compteur
        Label compteur = (Label) scene.lookup("#labelCpt");
        compteur.setText(tour.substring(0,1) + "  " + tour.substring(1,2) + "  " + tour.substring(2,3));
    }

    public void changementJoueur(Scene scene, MouseEvent event) throws IOException
    {
        //Changement de joueur
        Partie.setJoueurActuel(Partie.getJoueurSuivant());

        //activer agent fisk
        //pour richesse prospere : au bon tour et pour le bon joueur
        if(Partie.getTour() % 3 == 0 && Partie.getNbToursMax() == -1 && Partie.getJoueurActuel().getIdJoueur() >= AgentFisk.getIdPremierJoueur() && !AgentFisk.estActif() )
        {
            AgentFisk.activer();
            actualiserEffetFisk(scene);
            affichagePopUp(scene, 3);
        }

        //pour richesse immediate : au bon tour et pour le bon joueur
        else if(Partie.getTour() % 3 == 1 && Partie.getNbToursMax() == 30 && Partie.getJoueurActuel().getIdJoueur() >= AgentFisk.getIdPremierJoueur() && !AgentFisk.estActif())
        {
            AgentFisk.activer();
            actualiserEffetFisk(scene);
            affichagePopUp(scene, 3);
        }

        //Ajouter argent au joueur
        Partie.getJoueurActuel().setVillesMonop();
        Partie.setArgentParTour(Partie.getJoueurActuel());
        Partie.getJoueurActuel().setArgentDispo(Partie.getJoueurActuel().getArgentDispo() + Partie.getJoueurActuel().getArgentParTour());

        //initialiser la phase
        Partie.setPhase(1);

        //Changement du nom de phase
        Label labelPhase = (Label) scene.lookup("#labelPhase");
        labelPhase.setText("Achats");

        //Remettre les deux barres en blanc
        Rectangle barrePhase2 = (Rectangle) scene.lookup("#barrePhase2");
        Rectangle barrePhase3 = (Rectangle) scene.lookup("#barrePhase3");
        barrePhase2.setFill(Color.WHITE);
        barrePhase3.setFill(Color.WHITE);

        //Changer la couleur du joueur
        Rectangle menuPhases = (Rectangle) scene.lookup("#menuPhases");
        Rectangle menuAchats = (Rectangle) scene.lookup("#menuAchats");
        Rectangle cadreJoueur = (Rectangle) scene.lookup("#cadreJoueur");
        menuPhases.setFill(Paint.valueOf(Partie.getJoueurActuel().getCouleur()));
        menuAchats.setFill(Paint.valueOf(Partie.getJoueurActuel().getCouleur()));
        cadreJoueur.setFill(Paint.valueOf(Partie.getJoueurActuel().getCouleur()));

        //actualiser les infos joueurs
        majInfosJoueur(scene);

        //actualiser infos adversaires
        majInfosAdvers(scene);

        //actualiser cadre en bas a droite agent fisk
        actualiserAgentFisk(scene);

        //rendre le transfert possible
        transfertEffectue = false;

        //Passage en vue Joueurs
        if(vue)
        {
            Circle btnVue = (Circle) scene.lookup("#btnVue");
            btnVue.fireEvent(event);
        }
    }

    public void changementPhase(Scene scene)
    {
        //incrémenter la phase
        Partie.setPhase(Partie.getPhase()+1);

        //actualiser argent par tour
        Partie.setArgentParTour(Partie.getJoueurActuel());

        //changement du nom de la phase
        Label labelPhase = (Label) scene.lookup("#labelPhase");
        if(Partie.getPhase() == 2)
        {
            labelPhase.setText("Combats fiscaux");
            Rectangle barrePhase2 = (Rectangle) scene.lookup("#barrePhase2");
            barrePhase2.setFill(Paint.valueOf("#f0ff1a"));
        }
        else
        {
            labelPhase.setText("Transfert stratégique");
            Rectangle barrePhase3 = (Rectangle) scene.lookup("#barrePhase3");
            barrePhase3.setFill(Paint.valueOf("#f0ff1a"));
        }
    }

    public void desactiverBoutons(Scene scene)
    {
        Circle banquiers = (Circle) scene.lookup("#btnBanquiers");
        Circle placements = (Circle) scene.lookup("#btnPlacements");
        Circle agentFisk = (Circle) scene.lookup("#btnAgentFisk");
        Circle vue = (Circle) scene.lookup("#btnVue");
        Circle suivant = (Circle) scene.lookup("#btnSuiv");

        banquiers.setDisable(true);
        placements.setDisable(true);
        agentFisk.setDisable(true);
        vue.setDisable(true);
        suivant.setDisable(true);

        ImageView imgBanquiers = (ImageView) scene.lookup("#imgBanquiers");
        ImageView imgPlacements = (ImageView) scene.lookup("#imgPlacements");
        ImageView imgAgentFisk = (ImageView) scene.lookup("#imgAgentFisk");
        ImageView imgVue = (ImageView) scene.lookup("#imgVue");
        ImageView imgSuivant = (ImageView) scene.lookup("#imgSuiv");

        imgBanquiers.setDisable(true);
        imgPlacements.setDisable(true);
        imgAgentFisk.setDisable(true);
        imgVue.setDisable(true);
        imgSuivant.setDisable(true);
    }

    public void activerBoutons(Scene scene)
    {
        Circle banquiers = (Circle) scene.lookup("#btnBanquiers");
        Circle placements = (Circle) scene.lookup("#btnPlacements");
        Circle agentFisk = (Circle) scene.lookup("#btnAgentFisk");
        Circle vue = (Circle) scene.lookup("#btnVue");
        Circle suivant = (Circle) scene.lookup("#btnSuiv");

        banquiers.setDisable(false);
        placements.setDisable(false);
        agentFisk.setDisable(false);
        vue.setDisable(false);
        suivant.setDisable(false);

        ImageView imgBanquiers = (ImageView) scene.lookup("#imgBanquiers");
        ImageView imgPlacements = (ImageView) scene.lookup("#imgPlacements");
        ImageView imgAgentFisk = (ImageView) scene.lookup("#imgAgentFisk");
        ImageView imgVue = (ImageView) scene.lookup("#imgVue");
        ImageView imgSuivant = (ImageView) scene.lookup("#imgSuiv");

        imgBanquiers.setDisable(false);
        imgPlacements.setDisable(false);
        imgAgentFisk.setDisable(false);
        imgVue.setDisable(false);
        imgSuivant.setDisable(false);
    }

    public void afficheAdvers(Scene scene)
    {
        for (int i = 1; i <= Partie.getJoueursRestants().length; i++)
        {
            String id0 = "#advers" + i + "0";
            String id1 = "#advers" + i + "1";
            String idImgBat = "#imgBat" + i;
            String idImgDollar = "#imgDollar" + i;
            String idLabelAgences = "#labelAgences" + i;
            String idLabelDollar = "#labelDollar" + i;
            Circle circleId0 = (Circle) scene.lookup(id0);
            Rectangle rectId1 = (Rectangle) scene.lookup(id1);
            ImageView imgBat = (ImageView) scene.lookup(idImgBat);
            ImageView imgDollar = (ImageView) scene.lookup(idImgDollar);
            Label labelAgences = (Label) scene.lookup(idLabelAgences);
            Label labelDollar = (Label) scene.lookup(idLabelDollar);
            circleId0.setVisible(true);
            rectId1.setVisible(true);
            imgBat.setVisible(true);
            imgDollar.setVisible(true);
            labelAgences.setVisible(true);
            labelDollar.setVisible(true);
        }
    }

    public void majInfosAdvers(Scene scene)
    {
        for(int i = 0; i < Partie.nbJoueursRestants(); i++)
        {
            //recuperer numero du joueur (id+1)
            int id = Partie.getJoueursRestants()[i].getIdJoueur() + 1;

            Label labelDollar = (Label) scene.lookup("#labelDollar" + id);
            Label labelAgences = (Label) scene.lookup("#labelAgences" + id);
            labelDollar.setText("" + Partie.getJoueursRestants()[i].getArgentDispo() + " $");
            labelAgences.setText("" + Partie.getJoueursRestants()[i].getNbAgences());
        }

        for(int i = 0; i < Partie.nbJoueursElimines(); i++)
        {
            //recuperer numero du joueur (id+1)
            int id = Partie.getJoueursElimines()[i].getIdJoueur() + 1;

            //desafficher les infos
            Label labelDollar = (Label) scene.lookup("#labelDollar" + id);
            Label labelAgences = (Label) scene.lookup("#labelAgences" + id);
            ImageView imgBat = (ImageView) scene.lookup("#imgBat" + id);
            ImageView imgDollar = (ImageView) scene.lookup("#imgDollar" + id);
            Rectangle rectId1 = (Rectangle) scene.lookup("#advers" + id + "1");
            labelDollar.setVisible(false);
            labelAgences.setVisible(false);
            imgBat.setVisible(false);
            imgDollar.setVisible(false);
            rectId1.setVisible(false);

            //afficher la croix
            ImageView imgElem = (ImageView) scene.lookup("#elem" + id);
            imgElem.setVisible(true);
        }
    }

    public void majInfosJoueur(Scene scene)
    {
        Label labelPseudo = (Label) scene.lookup("#labelPseudo");
        Label labelDispo = (Label) scene.lookup("#labelDispo");
        Label labelParTour = (Label) scene.lookup("#labelParTour");
        Label labelPlace = (Label) scene.lookup("#labelPlace");
        labelDispo.setText("" + Partie.getJoueurActuel().getArgentDispo() + " $");
        labelParTour.setText("" + Partie.getJoueurActuel().getArgentParTour() + " $");
        labelPlace.setText("" + Partie.getJoueurActuel().getArgentPlace() + " $");
        labelPseudo.setText(Partie.getJoueurActuel().getPseudo());
    }

    public void desafficherMenuBanquiers(Scene scene)
    {
        Rectangle barre = (Rectangle) scene.lookup("#barreBanquiers");
        ImageView refuser = (ImageView) scene.lookup("#refBanquiers");
        ImageView valider = (ImageView) scene.lookup("#valBanquiers");
        MFXSlider jauge = (MFXSlider) scene.lookup("#jaugeBanquiers");

        banquiers = false;
        barre.setVisible(false);
        refuser.setVisible(false);
        valider.setVisible(false);
        jauge.setVisible(false);
    }

    public void desafficherMenuPlacements(Scene scene)
    {
        Rectangle barre = (Rectangle) scene.lookup("#barrePlacements");
        ImageView refuser = (ImageView) scene.lookup("#refPlacements");
        ImageView valider = (ImageView) scene.lookup("#valPlacements");
        MFXSlider jauge = (MFXSlider) scene.lookup("#jaugePlacements");

        placements = false;
        barre.setVisible(false);
        refuser.setVisible(false);
        valider.setVisible(false);
        jauge.setVisible(false);
    }

    public void affichagePopUp(Scene scene, int code)
    {
        if(polySourceId != null)
        {
            effetsPolygonDefaut(scene, polySourceId);
            polySourceId = null;
        }
        desactiverBoutons(scene);
        Label textCond1 = (Label) scene.lookup("#textCond1");
        if(code == 1)
        {
            textCond1.setText("Pour passer à la phase suivante, vous devez placer tous les banquiers que vous avez recruté.");
            textCond1.setVisible(true);
        }

        else if(code == 2)
        {
            textCond1.setText("Vous n'avez pas assez d'argent pour payer le pot de vin.");
            textCond1.setVisible(true);
        }

        else if(code == 3)
        {
            ImageView fond = (ImageView) scene.lookup("#bgRegles");
            fond.setVisible(true);
            fond.toFront();

            textCond1.setText("L'agent FISK passe à l'action : " + AgentFisk.getNom() + ".");
            textCond1.setVisible(true);
        }
        Rectangle rectCond1 = (Rectangle) scene.lookup("#rectCond1");
        ImageView valCond1 = (ImageView) scene.lookup("#valCond1");
        rectCond1.setVisible(true);
        rectCond1.toFront();
        valCond1.setVisible(true);
        valCond1.toFront();
        textCond1.toFront();
    }

    public void actArgentParTour(Scene scene)
    {
        Partie.setArgentParTour(Partie.getJoueurActuel());
        Label labelParTour = (Label) scene.lookup("#labelParTour");
        labelParTour.setText(Partie.getJoueurActuel().getArgentParTour()+" $");

    }

    public void validBandeauPhase1(Scene scene)
    {
        MFXSlider jaugeBandeau = (MFXSlider) scene.lookup("#jaugeBandeau");

        //récupérer banquiers a placer
        int nbBanquiersPlaces = (int) jaugeBandeau.getValue();

        //diminuer le nombre de banquiers dispo
        Partie.getJoueurActuel().setNbBanquiersDispo(Partie.getJoueurActuel().getNbBanquiersDispo() - nbBanquiersPlaces);

        //ajouter les banquiers a l'agence
        int id = Integer.parseInt(polySourceId.substring(2));
        Agence agence = Carte.getAgenceById(id);
        agence.setNbBanquiers(agence.getNbBanquiers() + nbBanquiersPlaces);

        //actualiser affichage
        Label labelNbBanquiers = (Label) scene.lookup("#labelNbBanquiers");
        Label nbBanquiersAgence = (Label) scene.lookup("#l" + id);
        labelNbBanquiers.setText(""+Partie.getJoueurActuel().getNbBanquiersDispo());
        nbBanquiersAgence.setText(""+agence.getNbBanquiers());

        //deselectionner l'agence
        effetsPolygonDefaut(scene, polySourceId);
        polySourceId = null;

        //fermer le bandeau
        desafficherBandeau(scene);
    }

    public void validBandeauPhase2(Scene scene) throws IOException, SQLException {
        //récupérer le nombre de banquiers a placer
        MFXSlider jaugeBandeau = (MFXSlider) scene.lookup("#jaugeBandeau");
        int nbAttaquants = (int) jaugeBandeau.getValue();

        //récupérer l'agence source et l'agence destination
        int idSource = Integer.parseInt(polySourceId.substring(2));
        Agence agenceSource = Carte.getAgenceById(idSource);
        int idDest = Integer.parseInt(polyDestId.substring(2));
        Agence agenceDest = Carte.getAgenceById(idDest);

        //attaquer
        agenceSource.attaque(agenceDest, nbAttaquants);
        actualiserAttaque(scene);

        desafficherBandeau(scene);
        effetsPolygonDefaut(scene, polySourceId);
        effetsPolygonDefaut(scene, polyDestId);

        polySourceId = null;
        polyDestId = null;
    }

    public void validBandeauPhase3(Scene scene)
    {
        //récupérer le nombre de banquiers a transferer
        MFXSlider jaugeBandeau = (MFXSlider) scene.lookup("#jaugeBandeau");
        int nbBanquiers = (int) jaugeBandeau.getValue();

        //récupérer l'agence source et l'agence destination
        int idSource = Integer.parseInt(polySourceId.substring(2));
        Agence agenceSource = Carte.getAgenceById(idSource);
        int idDest = Integer.parseInt(polyDestId.substring(2));
        Agence agenceDest = Carte.getAgenceById(idDest);

        //transferer
        agenceSource.transfertVers(agenceDest, nbBanquiers);
        Label lSource = (Label) scene.lookup("#l" + polySourceId.substring(2));
        Label lDest = (Label) scene.lookup("#l" + polyDestId.substring(2));
        lSource.setText(String.valueOf(agenceSource.getNbBanquiers()));
        lDest.setText(String.valueOf(agenceDest.getNbBanquiers()));

        desafficherBandeau(scene);
        effetsPolygonDefaut(scene, polySourceId);
        effetsPolygonDefaut(scene, polyDestId);

        transfertEffectue = true;
        polySourceId = null;
        polyDestId = null;
    }

    public void actualiserAttaque(Scene scene) throws IOException, SQLException {
        //menu adversaires
        majInfosAdvers(scene);

        //infos joueur
        Partie.setArgentParTour(Partie.getJoueurActuel());
        majInfosJoueur(scene);

        //polySource (label)
        int idSource = Integer.parseInt(polySourceId.substring(2));
        Agence agenceSource = Carte.getAgenceById(idSource);

        Label labelSource = (Label) scene.lookup("#l" + polySourceId.substring(2));
        labelSource.setText(String.valueOf(agenceSource.getNbBanquiers()));

        //polyDest (poly + label)
        int idDest = Integer.parseInt(polyDestId.substring(2));
        Agence agenceDest = Carte.getAgenceById(idDest);

        Polygon polyDest = (Polygon) scene.lookup(polyDestId);
        polyDest.setFill(Paint.valueOf(agenceDest.getJoueur().getCouleur()));

        Label labelDest = (Label) scene.lookup("#l" + polyDestId.substring(2));
        labelDest.setText(String.valueOf(agenceDest.getNbBanquiers()));

        actualiserAgentFisk(scene);


        if(Partie.aUnGagnant() != null)
        {
            Joueur gagnant = Partie.aUnGagnant();
            //afficher écran de fin de partie
            // Chargement du fichier FXML
            FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("jeu.fxml"));
            Parent root = loadMenu.load();

            // Affichage de la scène
            Stage stage = (Stage) scene.getWindow();
            FXMLLoader loadMap = new FXMLLoader(getClass().getResource("fin_partie.fxml"));
            Scene map = new Scene(loadMap.load());
            stage.setScene(map);
            stage.setFullScreen(true);
            Label label = (Label) map.lookup("#labelMessage");
            label.setText(gagnant.getPseudo() + " , avez-vous pensé à vous lancer dans le monde des affaires ? Vous venez de prouver votre superiorité sur le monde en gagnant une partie de FISK, ce jeu fantastique.");

            //Connexion a la bdd
            String url = "jdbc:mysql://192.168.143.162:3306/FISK_BDD";
            String utilisateur = "fisk";
            String motDePasse = "fiskCgenial";
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            //Récupération de l'id max de PARTIES
            String requeteIdPartie = "SELECT max(IdPartie) FROM PARTIES";
            PreparedStatement statementIdPartie = connexion.prepareStatement(requeteIdPartie);
            ResultSet resultSetIdPartie= statementIdPartie.executeQuery();

            int maxId = -1;
            if(resultSetIdPartie.next())
            {
                maxId = resultSetIdPartie.getInt(1);
            }
            statementIdPartie.close();

            //Enregistrement de la partie
            String requeteEnr = "INSERT INTO PARTIES (IdGagnant,IdPartie) VALUES (?,?)";
            PreparedStatement statementEnr = connexion.prepareStatement(requeteEnr);
            statementEnr.setString(1, String.valueOf(Partie.getIdJoueursBDD()[gagnant.getIdJoueur()]));
            statementEnr.setString(2, String.valueOf(maxId+1));
            statementEnr.executeUpdate();
            statementEnr.close();

            //MAJ JOUEURS_PARTIES
            for(int i=0; i<Partie.getNbJoueursBDD();i++)
            {
                String requeteEnrPartJ = "INSERT INTO JOUEURS_PARTIES (IdJoueur,IdPartie) VALUES (?,?)";
                PreparedStatement statementEnrPartJ = connexion.prepareStatement(requeteEnrPartJ);
                statementEnrPartJ.setString(1, String.valueOf(Partie.getIdJoueursBDD()[i]));
                statementEnrPartJ.setString(2, String.valueOf(maxId+1));
                statementEnrPartJ.executeUpdate();
                statementEnrPartJ.close();
            }

            connexion.close();
        }

    }

    public void actualiserAgentFisk(Scene scene) throws IOException
    {
        Label labelMessage = (Label) scene.lookup("#labelFisk");
        if(!Partie.getJoueurActuel().isInfoAchetee())
        {
            labelMessage.setText("Vous n'avez pas payé de pot de vin, vous n'avez donc pas accès à l'information.");
            if(AgentFisk.getAgenceCible() != null)
            {
                String labelId =  "#l" + AgentFisk.getAgenceCible().getId();
                Label label = (Label) scene.lookup(labelId);
                label.setTextFill(Color.BLACK);
            }
        }
        else
        {
            labelMessage.setText(AgentFisk.getMessage());
            if(AgentFisk.getEvenement() != 5  || AgentFisk.getAgenceCible().getJoueur().equals(Partie.getJoueurActuel()))
            {
                String labelId =  "#l" + AgentFisk.getAgenceCible().getId();
                Label label = (Label) scene.lookup(labelId);
                label.setTextFill(Color.WHITE);
            }
            else
            {
                String labelId =  "#l" + AgentFisk.getAgenceCible().getId();
                Label label = (Label) scene.lookup(labelId);
                label.setTextFill(Color.BLACK);
            }
        }
    }

    public void actualiserEffetFisk(Scene scene)
    {
        int idAgence = AgentFisk.getAgenceCible().getId();

        Label labelAgence = (Label) scene.lookup("#l" + idAgence);
        labelAgence.setText(String.valueOf(AgentFisk.getAgenceCible().getNbBanquiers()));
        labelAgence.setTextFill(Color.BLACK);

        majInfosJoueur(scene);
        majInfosAdvers(scene);
    }
}

package ihm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import modele.carte.Agence;
import modele.carte.Carte;
import modele.jeu.Joueur;
import modele.jeu.Partie;
import io.github.palexdev.materialfx.controls.MFXSlider;

import java.io.IOException;

public class JeuListeners {
    private boolean vue = true; //false = vue normale, true = vue des villes
    private boolean banquiers = false;
    private boolean placements = false;
    private boolean agentFisk = false;
    private String polyIDSource;
    private String polyIDDest;

    public static void initJeu(Joueur[] joueurs, int nbToursMax) throws IOException
    {
        //Création de la partie
        Partie partie = new Partie(nbToursMax, joueurs);

    }

    @FXML
    public void onAccederButtonClick(MouseEvent event) throws IOException
    {
        Button acceder = (Button) event.getSource();
        Scene scene = acceder.getScene();

        //Suppression du message d'intro
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

        //Affichage des infos adversaires
        for (int i = 1; i <= Partie.getJoueursRestants().length; i++)
        {
            String id0 = "#advers" + i + "0";
            String id1 = "#advers" + i + "1";
            String idImgBat = "#imgBat" + i;
            String idImgDollar = "#imgDollar" + i;
            String idLabelBat = "#labelBat" + i;
            String idLabelDollar = "#labelDollar" + i;
            Circle circleId0 = (Circle) scene.lookup(id0);
            Rectangle rectId1 = (Rectangle) scene.lookup(id1);
            ImageView imgBat = (ImageView) scene.lookup(idImgBat);
            ImageView imgDollar = (ImageView) scene.lookup(idImgDollar);
            Label labelBat = (Label) scene.lookup(idLabelBat);
            Label labelDollar = (Label) scene.lookup(idLabelDollar);
            circleId0.setVisible(true);
            rectId1.setVisible(true);
            imgBat.setVisible(true);
            imgDollar.setVisible(true);
            labelBat.setVisible(true);
            labelDollar.setVisible(true);
        }

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
                int id = agence.getId();
                Label nbBanquiers = (Label) scene.lookup("#l" + id);
                nbBanquiers.setText(""+agence.getNbBanquiers());
            }
        }
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
        Polygon source = (Polygon) event.getSource();
        Scene scene = source.getScene();
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
            for(Joueur joueur:Partie.getJoueursRestants())
            {
                for(Agence a:joueur.getAgences())
                {
                    String polyId="#p"+a.getId();
                    Polygon polygon = (Polygon) scene.lookup(polyId);
                    polygon.setFill(Paint.valueOf(joueur.getCouleur()));
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

    public void onSuivClick(MouseEvent event) throws IOException
    {
        Circle source = (Circle) event.getSource();
        Scene scene = source.getScene();

        //Si fin des 3 phases d'un joueur
        if(Partie.getPhase()%3 == 0)
        {
            //Si nouveau tour
            if(Partie.getJoueurSuivant().equals(Partie.getJoueursRestants()[0]))
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

            //Changement de joueur
            Partie.setJoueurActuel(Partie.getJoueurSuivant());

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

            //Passage en vue Joueurs
            //Changement de la vue
            if(vue)
            {
                Circle btnVue = (Circle) scene.lookup("#btnVue");
                btnVue.fireEvent(event);
            }
        }

        else
        {
            //incrémenter la phase
            Partie.setPhase(Partie.getPhase()+1);

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
    }

    public void onBanquiersClick(MouseEvent event) throws IOException
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

        if(banquiers)
        {
            banquiers = false;
            barre.setVisible(false);
            refuser.setVisible(false);
            valider.setVisible(false);
            jauge.setVisible(false);
        }
        else
        {
            banquiers = true;
            barre.setVisible(true);
            refuser.setVisible(true);
            valider.setVisible(true);
            jauge.setVisible(true);
        }
    }

    public void onPlacementsClick(MouseEvent event) throws IOException
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

        if(placements)
        {
            placements = false;
            barre.setVisible(false);
            refuser.setVisible(false);
            valider.setVisible(false);
            jauge.setVisible(false);
        }
        else
        {
            placements = true;
            barre.setVisible(true);
            refuser.setVisible(true);
            valider.setVisible(true);
            jauge.setVisible(true);
        }
    }

    public void onAgentFiskClick(MouseEvent event) throws IOException
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

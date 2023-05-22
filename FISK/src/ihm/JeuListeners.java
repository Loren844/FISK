package ihm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import modele.jeu.Joueur;
import modele.jeu.Partie;

import java.io.IOException;

public class JeuListeners {

    public static void initJeu(Joueur[] joueurs, int nbToursMax)
    {
        Partie partie = new Partie(nbToursMax, joueurs);
        System.out.println("Partie créée");
        System.out.println("Nombre de joueurs : " + Partie.nbJoueursRestants());
        System.out.println("Nombre de tours max : " + partie.getNbToursMax());
        for(int i = 0; i < joueurs.length; i++)
        {
            System.out.println("Joueur " + i+1 + " : ");
            for(int j =  0; j < joueurs[i].getAgences().length; j++)
            {
                System.out.print(joueurs[i].getAgences()[j].getId() + ", ");
            }
            System.out.println();
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
        System.out.println(source.getId());
    }
}

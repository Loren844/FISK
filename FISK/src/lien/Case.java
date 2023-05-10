package lien;

import modele.carte.Agence;
import javafx.scene.shape.Polygon;

public class Case {
    private Polygon poly;
    private Agence agence;
    private boolean estClique = false;

    public Case(Polygon p, Agence a)
    {
        poly = p;
        agence = a;
    }
}

package lien;

import carte.Agence;
import javafx.scene.shape.Polygon;

public class Case {
    private Polygon poly;
    private Agence agence;

    public Case(Polygon p, Agence a)
    {
        poly = p;
        agence = a;
    }
}

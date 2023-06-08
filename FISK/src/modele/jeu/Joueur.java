package modele.jeu;

import modele.carte.*;

public class Joueur {
    private static int nbInstances = 0;
    private int idJoueur;
    private String pseudo;
    private int argentDispo;
    private int argentPlace;
    private int argentParTour;
    private int nbBanquiersDispo = 0;
    private Agence[] agences = new Agence[38];
    private boolean infoAchetee;
    private Ville[] villesMonop = new Ville[6];
    private static final String[] couleurs = {"#ff0000","#ffa100","#9500ff","#ff5d8f"};

    public Joueur()
    {
        idJoueur = nbInstances;
        nbInstances++;
    }

    public Joueur(int[] idAgences)
    {

        for(int i = 0; i < idAgences.length; i++)
        {
            this.agences[i] = Carte.getAgenceById(idAgences[i]);
        }
        for(int i = idAgences.length; i < 38; i++)
        {
            this.agences[i] = new Agence();
        }

        idJoueur = nbInstances;
        nbInstances++;
    }

    public Agence[] getAgences()
    {
        return agences;
    }

    public String getCouleur()
    {
        return couleurs[idJoueur];
    }

    public int getArgentDispo() {
        return argentDispo;
    }

    public void setArgentDispo(int argentDispo) {
        this.argentDispo = argentDispo;
    }

    public int getArgentPlace() {
        return argentPlace;
    }

    public void placerArgent(int argentPlace) {
        this.argentPlace += argentPlace;
    }

    public int getArgentParTour() {
        return argentParTour;
    }

    public void setArgentParTour(int argentParTour) {
        this.argentParTour = argentParTour;
    }

    public Ville[] getVillesMonop() {
        return villesMonop;
    }

    public int getNbBanquiersDispo() {
        return nbBanquiersDispo;
    }

    public void setNbBanquiersDispo(int nbBanquiersDispo) {
        this.nbBanquiersDispo = nbBanquiersDispo;
    }

    public int getNbAgences(){
        int cpt = 0;
        for(int i = 0; i < agences.length; i++)
        {
            if(agences[i] != null)
            {
                cpt++;
            }
        }
        return cpt;
    }

    public boolean possede(String id)
    {
        String agenceId = id.substring(2);
        int intId = Integer.parseInt(agenceId);
        Agence a = Carte.getAgenceById(intId);
        for(int i = 0; i < agences.length; i++)
        {
            if(a.equals(agences[i]))
            {
                return true;
            }
        }
        return false;
    }

    public void gagnerAgence(Agence a)
    {
        int pos = this.getNbAgences();
        this.agences[pos] = a;
    }

    public void perdreAgence(Agence a)
    {
        int pos = 0;
        while(!(agences[pos].equals(a)))
        {
            pos++;
        }

        for(int i = pos; i < this.getNbAgences()-1; i++)
        {
            agences[i] = agences[i+1];
        }
        agences[this.getNbAgences()-1] = null;
    }
}

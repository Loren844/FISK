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
    private boolean infoAchetee = false;
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
        int numJoueur = idJoueur+1;
        pseudo = "Joueur " + numJoueur;
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

    public void setArgentPlace(int argentPlace)
    {
        this.argentPlace = argentPlace;
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

    public void setVillesMonop()
    {
        int cpt = 0;
        int idAgence = 0;

        for(int i = 0; i < 6; i++)
        {
            villesMonop[i] = null;
        }

        while(Carte.getAgenceById(idAgence).getJoueur().equals(this) && idAgence != 5)
        {
            idAgence++;
        }
        if(idAgence == 5)
        {
            villesMonop[cpt] = Carte.getVille(0);
            cpt++;
        }

        idAgence = 6;

        while(Carte.getAgenceById(idAgence).getJoueur().equals(this) && idAgence != 10)
        {
            idAgence++;
        }
        if(idAgence == 10)
        {
            villesMonop[cpt] = Carte.getVille(1);
            cpt++;
        }

        idAgence = 11;

        while(Carte.getAgenceById(idAgence).getJoueur().equals(this) && idAgence != 19)
        {
            idAgence++;
        }
        if(idAgence == 19)
        {
            villesMonop[cpt] = Carte.getVille(2);
            cpt++;
        }

        idAgence = 20;

        while(Carte.getAgenceById(idAgence).getJoueur().equals(this) && idAgence != 25)
        {
            idAgence++;
        }
        if(idAgence == 25)
        {
            villesMonop[cpt] = Carte.getVille(3);
            cpt++;
        }

        idAgence = 26;

        while(Carte.getAgenceById(idAgence).getJoueur().equals(this) && idAgence != 31)
        {
            idAgence++;
        }
        if(idAgence == 31)
        {
            villesMonop[cpt] = Carte.getVille(4);
            cpt++;
        }

        idAgence = 32;

        while(Carte.getAgenceById(idAgence).getJoueur().equals(this) && idAgence != 37)
        {
            idAgence++;
        }
        if(idAgence == 37)
        {
            villesMonop[cpt] = Carte.getVille(5);
            cpt++;
        }
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

    public static void setNbInstances(int nbInstances) {
        Joueur.nbInstances = nbInstances;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public boolean isInfoAchetee() {
        return infoAchetee;
    }

    public void setInfoAchetee(boolean infoAchetee) {
        this.infoAchetee = infoAchetee;
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

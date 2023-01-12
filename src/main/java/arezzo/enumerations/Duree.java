package arezzo.enumerations;

import abc.notation.Note;
import arezzo.MainAzerro;
import javafx.scene.image.Image;

/**
 * Enumération des durées
 */
public enum Duree{
    CROCHE("/"), NOIRE(""), BLANCHE("2"), RONDE("4");

    private String duree;

    /**
     * Constructeur privé
     * @param duree
     */
    private Duree(String duree)
    {
        this.duree = duree;
    }

    /**
     * Getter du string
     * @return duree
     */
    public String getDuree()
    {
        return this.duree;
    }

    /**
     * Retourne le nombre de temps x2 de la durée
     * @return durée x2
     */
    public int getDureex2() {
        switch (duree){
            case "/":
                return 1;
            case "":
                return 2;
            case "2":
                return 4;
            case "4":
                return 8;
        }
        return 1; // En cas d'erreur
    }

    @Override
    public String toString()
    {
        if (getDuree().contains(CROCHE.getDuree()))
            return "1/2 temps";
        else if (getDuree().contains(NOIRE.getDuree()))
            return "1 temps";
        else if (getDuree().contains(BLANCHE.getDuree()))
            return "2 temps";
        else if (getDuree().contains(RONDE.getDuree()))
            return "4 temps";
        return "1 temps";
    }
}
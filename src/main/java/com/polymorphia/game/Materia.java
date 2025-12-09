package com.polymorphia.game;

/**
 * Materia: objet spécial pouvant être appliqué à un équipement pour l'améliorer.
 */
public class Materia extends Objet {
    private int pointsAmelioration;

    public Materia(String nom, int prix, int pointsAmelioration) {
        super(nom, prix);
        this.pointsAmelioration = pointsAmelioration;
    }

    public int getPointsAmelioration() { return pointsAmelioration; }

    public void appliquer(Equipement e) {
        e.ameliorer(pointsAmelioration);
    }

    @Override
    public void utiliser(Object cible) {
        if (cible instanceof Equipement) {
            appliquer((Equipement)cible);
        }
    }
}

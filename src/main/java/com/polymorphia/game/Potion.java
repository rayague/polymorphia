package com.polymorphia.game;

/**
 * Potion consommable qui soigne le joueur.
 */
public class Potion extends Objet {
    private int soin;

    public Potion(String nom, int prix, int soin) {
        super(nom, prix);
        this.soin = soin;
    }

    public int getSoin() { return soin; }

    @Override
    public void utiliser(Object cible) {
        if (cible instanceof Joueur) {
            Joueur j = (Joueur) cible;
            j.gagnerPV(soin);
        }
    }
}

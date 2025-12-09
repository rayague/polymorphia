package com.polymorphia.game;

/**
 * Représente un sort utilisable en combat ou hors-combat.
 */
public class Sort extends Objet {
    public enum Type { ATTAQUE, DEFENSE }

    private int puissance;
    private Type type;

    public Sort(String nom, int prix, int puissance, Type type) {
        super(nom, prix);
        this.puissance = puissance;
        this.type = type;
    }

    public int getPuissance() { return puissance; }
    public Type getType() { return type; }

    public void lancer(Object cible) {
        // Exemple simplifié: si cible est Monstre ou Joueur, appliquer dommage
        if (type == Type.ATTAQUE && cible instanceof Monstre) {
            Monstre m = (Monstre)cible;
            m.recevoirDegats(puissance);
        }
    }

    @Override
    public void utiliser(Object cible) {
        lancer(cible);
    }
}

package com.polymorphia.game;

/**n+ * Représente un monstre ennemi.
 */
public class Monstre {
    private String nom;
    private int attaque;
    private int defense;
    private int pv;
    private int recompenseCoins;

    public Monstre(String nom, int attaque, int defense, int pv, int recompense) {
        this.nom = nom;
        this.attaque = attaque;
        this.defense = defense;
        this.pv = pv;
        this.recompenseCoins = recompense;
    }

    public String getNom() { return nom; }
    public int getAttaque() { return attaque; }
    public int getDefense() { return defense; }
    public int getPV() { return pv; }

    public void attaquer(Joueur j) {
        int degats = Math.max(0, attaque - 0); // simplifié
        j.perdrePV(degats);
    }

    public void recevoirDegats(int d) {
        pv = Math.max(0, pv - d);
    }

    public boolean estMort() { return pv <= 0; }

    public int getRecompenseCoins() { return recompenseCoins; }
}

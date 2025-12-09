package com.polymorphia.game;

/**
 * Représente un joueur du jeu.
 */
public class Joueur {
    private String nom;
    private int pv;
    private int attaque;
    private int defense;
    private int niveau;
    private Inventaire inventaire;
    private Equipement equipementCourant;

    public Joueur(String nom, int pv, int attaque, int defense) {
        this.nom = nom;
        this.pv = pv;
        this.attaque = attaque;
        this.defense = defense;
        this.niveau = 1;
        this.inventaire = new Inventaire();
    }

    public String getNom() { return nom; }
    public int getPV() { return pv; }

    public void perdrePV(int points) { pv = Math.max(0, pv - points); }
    public void gagnerPV(int points) { pv += points; }

    public void attaquer(Monstre m) {
        // Dégâts simplifiés: attaque du joueur moins défense du monstre
        int degats = Math.max(0, attaque - m.getDefense());
        m.recevoirDegats(degats);
    }

    public void equiper(Equipement e) {
        // Equipe l'objet et applique ses bonus (simplifié)
        this.equipementCourant = e;
        this.attaque += e.getBonusAttaque();
        this.defense += e.getBonusDefense();
    }

    public Inventaire getInventaire() { return inventaire; }
}

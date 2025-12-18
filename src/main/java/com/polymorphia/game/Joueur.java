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
    private int experience;
    private int argent;

    public Joueur(String nom, int pv, int attaque, int defense) {
        this.nom = nom;
        this.pv = pv;
        this.attaque = attaque;
        this.defense = defense;
        this.niveau = 1;
        this.inventaire = new Inventaire();
        this.experience = 0;
        this.argent = 100; // Argent de départ pour acheter
    }

    public String getNom() { return nom; }
    public int getPV() { return pv; }
    public int getNiveau() { return niveau; }

    public void perdrePV(int points) { pv = Math.max(0, pv - points); }
    public void gagnerPV(int points) { pv += points; }

    public boolean estVivant() { return pv > 0; }

    public int getAttaque() { return attaque; }
    public int getDefense() { return defense; }
    public int getArgent() { return argent; }
    public void ajouterArgent(int montant) { argent += montant; }
    public boolean retirerArgent(int montant) {
        if (argent >= montant) {
            argent -= montant;
            return true;
        }
        return false;
    }

    // Ajouter de l'expérience et gérer le niveau (simplifié)
    public void ajouterExperience(int xp) {
        experience += xp;
        while (experience >= niveau * 10) {
            experience -= niveau * 10;
            niveau++;
            // amélioration des stats lors du niveau
            attaque += 2;
            defense += 1;
            pv += 5;
            System.out.println(nom + " a atteint le niveau " + niveau + " !");
        }
    }

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

    @Override
    public String toString() {
        return nom + " (PV:" + pv + ", ATK:" + attaque + ", DEF:" + defense + ", LVL:" + niveau + ")";
    }
}

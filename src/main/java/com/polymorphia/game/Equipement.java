package com.polymorphia.game;

/**
 * Equipement (arme, armure...) qui modifie les statistiques du joueur.
 */
public class Equipement extends Objet {
    private int bonusAttaque;
    private int bonusDefense;

    public Equipement(String nom, int prix, int bonusAttaque, int bonusDefense) {
        super(nom, prix);
        this.bonusAttaque = bonusAttaque;
        this.bonusDefense = bonusDefense;
    }

    public int getBonusAttaque() { return bonusAttaque; }
    public int getBonusDefense() { return bonusDefense; }

    // Permet d'améliorer l'équipement (points d'amélioration simplifiés)
    public void ameliorer(int points) {
        // Par défaut, on augmente attaque et défense proportionnellement
        this.bonusAttaque += points / 2;
        this.bonusDefense += points / 2;
    }

    @Override
    public void utiliser(Object cible) {
        // Exemple: équiper sur un joueur
        if (cible instanceof Joueur) {
            ((Joueur)cible).equiper(this);
        }
    }
}

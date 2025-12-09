package com.polymorphia.game;

/**
 * Classe de base pour tous les objets du jeu.
 * Contient les propriétés communes comme le nom et le prix.
 */
public class Objet {
    protected String nom;
    protected int prix;

    public Objet(String nom, int prix) {
        this.nom = nom;
        this.prix = prix;
    }

    // Retourne le nom de l'objet
    public String getNom() { return nom; }

    // Retourne le prix de l'objet
    public int getPrix() { return prix; }

    // Action à effectuer quand l'objet est utilisé. À redéfinir.
    public void utiliser(Object cible) {
        // Implémentation par défaut: ne fait rien
    }
}

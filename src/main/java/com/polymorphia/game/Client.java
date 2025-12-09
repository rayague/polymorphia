package com.polymorphia.game;

/**
 * Client (squelette) qui représente la couche côté joueur pour réseau.
 */
public class Client {
    private Joueur joueur;
    private Serveur serveur;

    public Client(Joueur joueur, Serveur serveur) {
        this.joueur = joueur;
        this.serveur = serveur;
    }

    public void envoyerAction(String action) {
        // Transmettre une action au serveur (placeholder)
    }

    public void recevoirEtat(String etat) {
        // Recevoir mise à jour du serveur
    }
}

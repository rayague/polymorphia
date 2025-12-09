package com.polymorphia.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Serveur simplifié qui maintient des clients (squelette pour futur réseau).
 */
public class Serveur {
    private List<Client> clients = new ArrayList<>();

    public Serveur() {}

    public void attendreConnexion() {
        // Point d'extension: accepter des connexions réseau
    }

    public void gererCombat(Joueur j, Monstre m) {
        // Logique du combat côté serveur (placeholder)
    }

    public void ajouterClient(Client c) { clients.add(c); }
}

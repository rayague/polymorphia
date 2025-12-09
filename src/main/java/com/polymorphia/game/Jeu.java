package com.polymorphia.game;

import java.util.Scanner;

/**
 * Point d'entrée du jeu (runner simple).
 * Fournit un menu minimal pour tester l'architecture.
 */
public class Jeu {
    public static void main(String[] args) {
        System.out.println("=== Polymorphia (squelette) ===");
        Joueur joueur = new Joueur("Heros", 100, 10, 5);
        Monstre m = new Monstre("Gobelin", 6, 2, 30, 10);
        Scanner sc = new Scanner(System.in);

        System.out.println("Rencontre: " + m.getNom());
        System.out.println("Appuyez sur Entrée pour attaquer.");
        sc.nextLine();
        joueur.attaquer(m);
        System.out.println("PV du monstre après attaque: " + m.getPV());
        sc.close();
    }
}

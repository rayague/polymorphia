package com.polymorphia.game;

import java.util.Random;

/**
 * Usine simple pour créer des monstres aléatoires (bestiary simplifié).
 */
public class MonstreFactory {
    private static final Random rnd = new Random();

    public static Monstre creeMonstreAleatoire(int zoneNiveau) {
        int choix = rnd.nextInt(6);
        switch (choix) {
            case 0:
                return new Monstre("Loup", 5 + zoneNiveau, 2 + zoneNiveau/2, 20 + zoneNiveau*5, 5 + zoneNiveau);
            case 1:
                return new Monstre("Zombie", 4 + zoneNiveau, 3 + zoneNiveau/2, 25 + zoneNiveau*6, 6 + zoneNiveau);
            case 2:
                return new Monstre("Troll", 8 + zoneNiveau, 4 + zoneNiveau, 40 + zoneNiveau*8, 12 + zoneNiveau*2);
            case 3:
                return new Monstre("Gobelin", 6 + zoneNiveau, 2 + zoneNiveau/2, 18 + zoneNiveau*4, 4 + zoneNiveau);
            case 4:
                return new Monstre("Orc", 7 + zoneNiveau, 3 + zoneNiveau, 30 + zoneNiveau*6, 8 + zoneNiveau);
            default:
                // Dragon (rare et puissant)
                return new Monstre("Dragon", 15 + zoneNiveau*2, 8 + zoneNiveau, 80 + zoneNiveau*10, 25 + zoneNiveau*3);
        }
    }
}

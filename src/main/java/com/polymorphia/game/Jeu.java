package com.polymorphia.game;

import java.util.Scanner;

/**
 * Point d'entrée du jeu (runner simple).
 * Fournit un menu minimal pour tester l'architecture.
 */
public class Jeu {
    public static void main(String[] args) {
        System.out.println("=== Polymorphia - Début de l'aventure ===");
        Joueur joueur = new Joueur("Javalt de Riv", 100, 8, 5);
        // Quelques intcoins de départ
        joueur.getInventaire().ajouterMonnaie(20);

        Marchand marchand = new Marchand();
        marchand.initStockPourNiveau(1);

        Scanner sc = new Scanner(System.in);
        boolean quitter = false;
        int zoneNiveau = 1; // difficulté progressive

        while (!quitter && joueur.estVivant()) {
            System.out.println();
            System.out.println("Joueur: " + joueur);
            System.out.println("Choisissez une action:");
            System.out.println("1) Commercer avec le marchand");
            System.out.println("2) Explorer (risque de rencontrer un monstre)");
            System.out.println("3) Afficher inventaire");
            System.out.println("4) Equiper un objet (seulement première arme disponible)");
            System.out.println("5) Utiliser une potion");
            System.out.println("6) Se connecter au PvP (à implémenter)");
            System.out.println("0) Quitter");
            System.out.print("> ");
            String choix = sc.nextLine().trim();

            switch (choix) {
                case "1":
                    commercier(sc, joueur, marchand);
                    break;
                case "2":
                    Monstre m = MonstreFactory.creeMonstreAleatoire(zoneNiveau);
                    System.out.println("Vous rencontrez un " + m.getNom() + " !");
                    combat(sc, joueur, m);
                    if (!joueur.estVivant()) {
                        System.out.println("Vous êtes mort. Fin de la partie.");
                        quitter = true;
                    } else {
                        // Après victoire, progression légère
                        zoneNiveau++;
                    }
                    break;
                case "3":
                    joueur.getInventaire().afficherRésumé();
                    break;
                case "4":
                    // équiper la première arme trouvée
                    System.out.println("Équiper la première arme disponible (si existante)...");
                    if (!joueur.getInventaire().getEquipements().isEmpty()) {
                        Equipement e = joueur.getInventaire().getEquipements().get(0);
                        joueur.equiper(e);
                        System.out.println("Vous équipez: " + e.getNom());
                    } else {
                        System.out.println("Aucune arme disponible dans l'inventaire.");
                    }
                    break;
                case "5":
                    Potion p = joueur.getInventaire().consommerPremierePotion();
                    if (p != null) {
                        joueur.gagnerPV(p.getSoin());
                        System.out.println("Vous utilisez une potion et récupérez " + p.getSoin() + " PV.");
                    } else {
                        System.out.println("Vous n'avez pas de potion.");
                    }
                    break;
                case "6":
                    System.out.println("Fonction PvP non encore implémentée (sera ajouté bientôt).");
                    break;
                case "0":
                    quitter = true;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        sc.close();
        System.out.println("Merci d'avoir joué à Polymorphia.");
    }

    // Commerce simple: afficher, acheter par index
    private static void commercier(Scanner sc, Joueur joueur, Marchand marchand) {
        System.out.println("--- Marchand ---");
        marchand.afficherStock();
        System.out.println("Tapez le nom exact de l'objet à acheter ou 'retour' :");
        System.out.print("> ");
        String choix = sc.nextLine().trim();
        if (choix.equalsIgnoreCase("retour")) return;

        boolean vendu = marchand.vendreParNom(choix, joueur);
        if (vendu) {
            System.out.println("Achat réussi : " + choix);
        } else {
            System.out.println("Achat échoué (objet introuvable ou pas assez de monnaie).");
        }
    }

    // Combat joueur vs monstre (tour par tour)
    private static void combat(Scanner sc, Joueur joueur, Monstre m) {
        System.out.println("Début du combat contre " + m.getNom());
        while (joueur.estVivant() && !m.estMort()) {
            System.out.println("Votre PV: " + joueur.getPV() + " - Monstre PV: " + m.getPV());
            System.out.println("Actions: 1) Attaquer  2) Utiliser potion  3) Fuir (50% chance)");
            System.out.print("> ");
            String a = sc.nextLine().trim();
            if (a.equals("1")) {
                joueur.attaquer(m);
                System.out.println("Vous attaquez le " + m.getNom() + ". PV du monstre: " + m.getPV());
            } else if (a.equals("2")) {
                Potion p = joueur.getInventaire().consommerPremierePotion();
                if (p != null) {
                    joueur.gagnerPV(p.getSoin());
                    System.out.println("Vous buvez une potion et récupérez " + p.getSoin() + " PV.");
                } else System.out.println("Vous n'avez pas de potion.");
            } else if (a.equals("3")) {
                if (new java.util.Random().nextBoolean()) {
                    System.out.println("Fuite réussie !");
                    return;
                } else {
                    System.out.println("Fuite échouée, le combat continue.");
                }
            } else {
                System.out.println("Action non reconnue.");
            }

            if (m.estMort()) break;

            // Tour du monstre
            m.attaquer(joueur);
            System.out.println("Le " + m.getNom() + " vous attaque ! Vos PV: " + joueur.getPV());
        }

        if (!joueur.estVivant()) {
            System.out.println("Vous avez été vaincu...");
            return;
        }

        System.out.println("Monstre vaincu ! Vous gagnez " + m.getRecompenseCoins() + " intcoins.");
        joueur.getInventaire().ajouterMonnaie(m.getRecompenseCoins());
        // Drop aléatoire simplifié
        double drop = new java.util.Random().nextDouble();
        if (drop < 0.15) {
            Potion p = new Potion("Potion de soin", 0, 10);
            joueur.getInventaire().ajouter(p);
            System.out.println("Le monstre a laissé une potion !");
        } else if (drop < 0.20) {
            Materia mat = new Materia("Materia trouvée", 0, 2);
            joueur.getInventaire().ajouter(mat);
            System.out.println("Chanceux ! Vous trouvez une materia.");
        }
        // XP et potentiel montée de niveau
        joueur.ajouterExperience(5);
    }
}

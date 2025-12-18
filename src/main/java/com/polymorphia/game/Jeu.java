package com.polymorphia.game;

import java.util.List;
import java.util.Scanner;

/**
 * Point d'entrée du jeu (runner simple).
 * Fournit un menu minimal pour tester l'architecture.
 */
public class Jeu {
    public static void main(String[] args) {
        System.out.println("=== Polymorphia - Début de l'aventure ===");
        Joueur joueur = new Joueur("Javalt de Riv", 100, 8, 5);
        
        // Équipement de départ offert par le village
        System.out.println("Les villageois vous offrent un équipement de départ :");
        Equipement epeeDepart = new Equipement("Vieille épée", 0, 2, 0);
        Equipement bouclierDepart = new Equipement("Vieux bouclier", 0, 0, 1);
        joueur.getInventaire().ajouter(epeeDepart);
        joueur.getInventaire().ajouter(bouclierDepart);
        Materia materiaDepart = new Materia("Materia de départ", 0, 3);
        joueur.getInventaire().ajouter(materiaDepart);
        joueur.equiper(epeeDepart);
        joueur.equiper(bouclierDepart);
        System.out.println("  ✓ Vieille épée (ATK +2)");
        System.out.println("  ✓ Vieux bouclier (DEF +1)");
        System.out.println("  ✓ Materia de départ (+3 points)");
        
        // Plus d'intcoins de départ pour pouvoir acheter des potions et matériel
        joueur.getInventaire().ajouterMonnaie(50);
        System.out.println("  ✓ 50 intcoins\n");
        System.out.println("💡 ASTUCE: Explorez pour combattre des monstres et gagner des intcoins!");
        System.out.println("   Les monstres vaincus laissent de l'argent et parfois des objets rares.\n");

        Marchand marchand = new Marchand();
        marchand.initStockPourNiveau(1);

        Scanner sc = new Scanner(System.in);
        boolean quitter = false;
        int zoneNiveau = 1; // difficulté progressive
        int monstresVaincus = 0;
        int argentGagne = 0;

        while (!quitter && joueur.estVivant()) {
            System.out.println();
            System.out.println(joueur);
            System.out.println("💰 Intcoins: " + joueur.getInventaire().getMonnaie());
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Commercer avec le marchand");
            System.out.println("2) Explorer (risque de rencontrer un monstre)");
            System.out.println("3) Afficher inventaire détaillé");
            System.out.println("4) Gérer équipement (armes/armures)");
            System.out.println("5) Utiliser une potion");
            System.out.println("6) Utiliser de la materia sur un équipement");
            System.out.println("7) Combat PvP (2 joueurs)");
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
                    boolean victoire = combat(sc, joueur, m);
                    if (!joueur.estVivant()) {
                        System.out.println("Vous êtes mort. Fin de la partie.");
                        quitter = true;
                    } else if (victoire) {
                        // Après victoire, progression légère
                        monstresVaincus++;
                        argentGagne += m.getRecompenseCoins();
                        zoneNiveau++;
                    }
                    break;
                case "3":
                    joueur.getInventaire().afficherRésumé();
                    break;
                case "4":
                    gererEquipement(sc, joueur);
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
                    utiliserMateria(sc, joueur);
                    break;
                case "7":
                    combatPvP(sc);
                    break;
                case "0":
                    quitter = true;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        sc.close();
        
        // AFFICHAGE DU RÉSUMÉ FINAL
        System.out.println("\n" + "═".repeat(50));
        System.out.println("║" + " ".repeat(48) + "║");
        if (joueur.estVivant()) {
            System.out.println("║" + "          🎉 PARTIE TERMINÉE - VOUS AVEZ SURVÉCU! 🎉".substring(0, 48) + "║");
        } else {
            System.out.println("║" + "          ☠️  GAME OVER - VOUS ÊTES MORT  ☠️".substring(0, 48) + "║");
        }
        System.out.println("║" + " ".repeat(48) + "║");
        System.out.println("═".repeat(50));
        
        System.out.println("\n📊 STATISTIQUES FINALES:");
        System.out.println("─".repeat(50));
        System.out.println("👤 Héros: " + joueur.getNom());
        System.out.println("❤️  PV finaux: " + joueur.getPV());
        System.out.println("⚔️  Attaque: " + joueur.getAttaque());
        System.out.println("🛡️  Défense: " + joueur.getDefense());
        System.out.println("⭐ Niveau atteint: " + joueur.getNiveau());
        System.out.println("💰 Intcoins: " + joueur.getInventaire().getMonnaie());
        System.out.println("\n🏆 ACCOMPLISSEMENTS:");
        System.out.println("─".repeat(50));
        System.out.println("🐺 Monstres vaincus: " + monstresVaincus);
        System.out.println("💵 Argent gagné: " + argentGagne + " intcoins");
        System.out.println("🗡️  Équipements: " + joueur.getInventaire().getEquipements().size());
        System.out.println("🧪 Potions: " + joueur.getInventaire().getPotions().size());
        System.out.println("✨ Sorts: " + joueur.getInventaire().getSorts().size());
        System.out.println("💎 Materia: " + joueur.getInventaire().getMaterias().size());
        
        if (joueur.estVivant()) {
            System.out.println("\n🌟 " + joueur.getNom() + " restera dans les légendes!");
            if (monstresVaincus >= 10) {
                System.out.println("🏅 TITRE GAGNÉ: CHASSEUR LÉGENDAIRE!");
            } else if (monstresVaincus >= 5) {
                System.out.println("🏅 TITRE GAGNÉ: CHASSEUR CONFIRMÉ!");
            } else if (monstresVaincus >= 1) {
                System.out.println("🏅 TITRE GAGNÉ: CHASSEUR DÉBUTANT!");
            }
        } else {
            System.out.println("\n💀 Le monde de Polymorphia se souviendra de votre courage...");
        }
        
        System.out.println("\n" + "═".repeat(50));
        System.out.println("Merci d'avoir joué à Polymorphia.");
    }

    // Utiliser un sort en combat
    private static void utiliserSort(Scanner sc, Joueur joueur, Monstre m) {
        if (joueur.getInventaire().getSorts().isEmpty()) {
            System.out.println("Vous n'avez pas de sort!");
            return;
        }
        System.out.println("Choisissez un sort:");
        for (int i = 0; i < joueur.getInventaire().getSorts().size(); i++) {
            Sort s = joueur.getInventaire().getSorts().get(i);
            System.out.println("  " + (i+1) + ") " + s.getNom() + " (" + s.getType() + ", Puissance: " + s.getPuissance() + ")");
        }
        System.out.print("> ");
        try {
            int choix = Integer.parseInt(sc.nextLine().trim());
            if (choix > 0 && choix <= joueur.getInventaire().getSorts().size()) {
                Sort s = joueur.getInventaire().getSorts().get(choix - 1);
                if (s.getType() == Sort.Type.ATTAQUE) {
                    m.recevoirDegats(s.getPuissance());
                    System.out.println("Vous lancez " + s.getNom() + "! Le monstre perd " + s.getPuissance() + " PV!");
                } else {
                    System.out.println("Ce sort de défense n'est pas utilisable en combat pour l'instant.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Choix invalide.");
        }
    }

    // Gérer l'équipement (équiper armes/armures)
    private static void gererEquipement(Scanner sc, Joueur joueur) {
        System.out.println("\n=== Gestion équipement ===");
        System.out.println("Équipements disponibles:");
        List<Equipement> equipements = joueur.getInventaire().getEquipements();
        if (equipements.isEmpty()) {
            System.out.println("Aucun équipement dans l'inventaire.");
            return;
        }
        for (int i = 0; i < equipements.size(); i++) {
            Equipement e = equipements.get(i);
            System.out.println("  " + (i+1) + ") " + e.getNom() + " (ATK:+" + e.getBonusAttaque() + ", DEF:+" + e.getBonusDefense() + ")");
        }
        System.out.print("Choisissez l'équipement à utiliser (ou 0 pour annuler): ");
        try {
            int choix = Integer.parseInt(sc.nextLine().trim());
            if (choix > 0 && choix <= equipements.size()) {
                Equipement e = equipements.get(choix - 1);
                joueur.equiper(e);
                System.out.println("✓ Vous équipez: " + e.getNom());
            }
        } catch (NumberFormatException e) {
            System.out.println("Choix invalide.");
        }
    }

    // Utiliser de la materia pour améliorer un équipement
    private static void utiliserMateria(Scanner sc, Joueur joueur) {
        if (joueur.getInventaire().getMaterias().isEmpty()) {
            System.out.println("Vous n'avez pas de materia.");
            return;
        }
        if (joueur.getInventaire().getEquipements().isEmpty()) {
            System.out.println("Vous n'avez pas d'équipement à améliorer.");
            return;
        }

        System.out.println("\n=== Utiliser Materia ===");
        System.out.println("Materias disponibles:");
        for (int i = 0; i < joueur.getInventaire().getMaterias().size(); i++) {
            Materia mat = joueur.getInventaire().getMaterias().get(i);
            System.out.println("  " + (i+1) + ") " + mat.getNom() + " (+" + mat.getPointsAmelioration() + " points)");
        }
        System.out.print("Choisissez une materia: ");
        int choixMat;
        try {
            choixMat = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (choixMat < 0 || choixMat >= joueur.getInventaire().getMaterias().size()) {
                System.out.println("Choix invalide.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Choix invalide.");
            return;
        }

        System.out.println("\nÉquipements disponibles:");
        List<Equipement> equipements = joueur.getInventaire().getEquipements();
        for (int i = 0; i < equipements.size(); i++) {
            Equipement eq = equipements.get(i);
            System.out.println("  " + (i+1) + ") " + eq.getNom() + " (ATK:+" + eq.getBonusAttaque() + ", DEF:+" + eq.getBonusDefense() + ")");
        }
        System.out.print("Choisissez un équipement à améliorer: ");
        try {
            int choixEq = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (choixEq >= 0 && choixEq < equipements.size()) {
                Materia mat = joueur.getInventaire().getMaterias().remove(choixMat);
                Equipement eq = equipements.get(choixEq);
                mat.appliquer(eq);
                System.out.println("✓ " + eq.getNom() + " a été amélioré!");
                System.out.println("  Nouvelles stats: ATK:+" + eq.getBonusAttaque() + ", DEF:+" + eq.getBonusDefense());
            }
        } catch (NumberFormatException e) {
            System.out.println("Choix invalide.");
        }
    }

    // Combat PvP entre deux joueurs
    private static void combatPvP(Scanner sc) {
        System.out.println("\n========= MODE PVP =========");
        System.out.print("Nom du Joueur 1: ");
        String nom1 = sc.nextLine().trim();
        System.out.print("Nom du Joueur 2: ");
        String nom2 = sc.nextLine().trim();

        Joueur j1 = new Joueur(nom1, 50, 12, 6);
        Joueur j2 = new Joueur(nom2, 50, 12, 6);

        // Donner de l'argent et des objets aux deux joueurs
        j1.getInventaire().ajouterMonnaie(100);
        j2.getInventaire().ajouterMonnaie(100);
        
        // Équiper les joueurs de base
        j1.getInventaire().ajouter(new Equipement("Épée de fer", 0, 5, 0));
        j1.getInventaire().ajouter(new Equipement("Bouclier de fer", 0, 0, 3));
        j1.getInventaire().ajouter(new Potion("Potion", 0, 20));
        j1.getInventaire().ajouter(new Potion("Potion", 0, 20));
        
        j2.getInventaire().ajouter(new Equipement("Épée de fer", 0, 5, 0));
        j2.getInventaire().ajouter(new Equipement("Bouclier de fer", 0, 0, 3));
        j2.getInventaire().ajouter(new Potion("Potion", 0, 20));
        j2.getInventaire().ajouter(new Potion("Potion", 0, 20));

        Marchand marchand = new Marchand();
        marchand.initStockPourNiveau(2);

        // Phase de préparation
        System.out.println("\n=== PHASE DE PRÉPARATION ===");
        preparerJoueurPvP(sc, j1, marchand);
        preparerJoueurPvP(sc, j2, marchand);

        // Phase de combat
        System.out.println("\n========= COMBAT COMMENCE! =========");
        boolean tourJ1 = true;
        boolean quitter = false;

        while (j1.estVivant() && j2.estVivant() && !quitter) {
            Joueur attaquant = tourJ1 ? j1 : j2;
            Joueur defenseur = tourJ1 ? j2 : j1;

            System.out.println("\n┌─────────────────────────────────┐");
            System.out.println("│ Tour de " + attaquant.getNom() + " ".repeat(Math.max(0, 25 - attaquant.getNom().length())) + "│");
            System.out.println("└─────────────────────────────────┘");
            System.out.println(attaquant.getNom() + ": " + attaquant.getPV() + " PV (ATK:" + attaquant.getAttaque() + ", DEF:" + attaquant.getDefense() + ")");
            System.out.println(defenseur.getNom() + ": " + defenseur.getPV() + " PV (ATK:" + defenseur.getAttaque() + ", DEF:" + defenseur.getDefense() + ")");
            System.out.println("\nActions:");
            System.out.println("  1) Attaquer");
            System.out.println("  2) Utiliser une potion");
            System.out.println("  3) Voir inventaire");
            System.out.println("  4) Abandonner le combat");
            System.out.print("> ");
            String action = sc.nextLine().trim();

            if (action.equals("1")) {
                int degats = Math.max(1, attaquant.getAttaque() - defenseur.getDefense());
                defenseur.perdrePV(degats);
                System.out.println("\n⚔️  " + attaquant.getNom() + " attaque! " + defenseur.getNom() + 
                                 " perd " + degats + " PV.");
                tourJ1 = !tourJ1;
            } else if (action.equals("2")) {
                Potion p = attaquant.getInventaire().consommerPremierePotion();
                if (p != null) {
                    attaquant.gagnerPV(p.getSoin());
                    System.out.println("\n🧪 " + attaquant.getNom() + " utilise une potion et récupère " + p.getSoin() + " PV!");
                } else {
                    System.out.println("\n❌ Vous n'avez pas de potion!");
                }
                tourJ1 = !tourJ1;
            } else if (action.equals("3")) {
                attaquant.getInventaire().afficherRésumé();
            } else if (action.equals("4")) {
                System.out.println("\n❌ " + attaquant.getNom() + " abandonne le combat!");
                attaquant.perdrePV(attaquant.getPV()); // Le joueur perd
                quitter = true;
                
                // Afficher immédiatement le gagnant
                System.out.println("\n" + "═".repeat(35));
                System.out.println("🏆 " + defenseur.getNom() + " remporte le combat par abandon!");
                System.out.println("═".repeat(35));
                System.out.println("\n" + defenseur.getNom() + " - PV restants: " + defenseur.getPV());
                System.out.println(attaquant.getNom() + " - ABANDONNÉ");
                return;
            } else {
                System.out.println("Action invalide, tour passé.");
                tourJ1 = !tourJ1;
            }
        }

        // Affichage du résultat final (si pas abandonné)
        System.out.println("\n" + "═".repeat(35));
        if (j1.estVivant()) {
            System.out.println("🏆 " + j1.getNom() + " remporte le combat!");
            System.out.println("═".repeat(35));
            System.out.println("\n" + j1.getNom() + " - PV restants: " + j1.getPV());
            System.out.println(j2.getNom() + " - VAINCU");
        } else {
            System.out.println("🏆 " + j2.getNom() + " remporte le combat!");
            System.out.println("═".repeat(35));
            System.out.println("\n" + j2.getNom() + " - PV restants: " + j2.getPV());
            System.out.println(j1.getNom() + " - VAINCU");
        }
    }

    // Préparation d'un joueur avant le combat PvP
    private static void preparerJoueurPvP(Scanner sc, Joueur joueur, Marchand marchand) {
        System.out.println("\n--- Préparation de " + joueur.getNom() + " ---");
        boolean pret = false;
        
        while (!pret) {
            System.out.println("\n" + joueur.getNom() + " - PV:" + joueur.getPV() + ", ATK:" + joueur.getAttaque() + ", DEF:" + joueur.getDefense());
            System.out.println("💰 Intcoins: " + joueur.getInventaire().getMonnaie());
            System.out.println("\n1) Acheter au marchand");
            System.out.println("2) Équiper armes/armures");
            System.out.println("3) Voir inventaire");
            System.out.println("4) Prêt pour le combat!");
            System.out.print("> ");
            String choix = sc.nextLine().trim();
            
            switch (choix) {
                case "1":
                    commercier(sc, joueur, marchand);
                    break;
                case "2":
                    gererEquipement(sc, joueur);
                    break;
                case "3":
                    joueur.getInventaire().afficherRésumé();
                    break;
                case "4":
                    System.out.println("✓ " + joueur.getNom() + " est prêt!");
                    pret = true;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    // Commerce simple: afficher, acheter par index
    private static void commercier(Scanner sc, Joueur joueur, Marchand marchand) {
        System.out.println("\n=== MARCHAND ===");
        System.out.println("💰 Vos Intcoins: " + joueur.getInventaire().getMonnaie());
        System.out.println();
        marchand.afficherStock();
        System.out.println("\nTapez le nom exact de l'objet à acheter ou 'retour' :");
        System.out.print("> ");
        String choix = sc.nextLine().trim();
        if (choix.equalsIgnoreCase("retour")) return;

        boolean vendu = marchand.vendreParNom(choix, joueur);
        if (vendu) {
            System.out.println("✓ Achat réussi : " + choix);
            System.out.println("💰 Intcoins restants: " + joueur.getInventaire().getMonnaie());
        } else {
            System.out.println("✗ Achat échoué (objet introuvable ou pas assez de monnaie).");
            System.out.println("💰 Vous avez: " + joueur.getInventaire().getMonnaie() + " intcoins");
        }
    }

    // Combat joueur vs monstre (tour par tour)
    private static boolean combat(Scanner sc, Joueur joueur, Monstre m) {
        System.out.println("\n=== COMBAT contre " + m.getNom() + " ===");
        while (joueur.estVivant() && !m.estMort()) {
            System.out.println("\nVotre PV: " + joueur.getPV() + " | " + m.getNom() + " PV: " + m.getPV());
            System.out.println("Actions:");
            System.out.println("  1) Attaque normale");
            System.out.println("  2) Utiliser un sort");
            System.out.println("  3) Utiliser une potion");
            System.out.println("  4) Fuir (50% chance)");
            System.out.print("> ");
            String a = sc.nextLine().trim();
            
            if (a.equals("1")) {
                joueur.attaquer(m);
                System.out.println("Vous attaquez! Le " + m.getNom() + " perd des PV. PV restants: " + m.getPV());
            } else if (a.equals("2")) {
                utiliserSort(sc, joueur, m);
            } else if (a.equals("3")) {
                Potion p = joueur.getInventaire().consommerPremierePotion();
                if (p != null) {
                    joueur.gagnerPV(p.getSoin());
                    System.out.println("Vous buvez une potion et récupérez " + p.getSoin() + " PV.");
                } else {
                    System.out.println("Vous n'avez pas de potion!");
                }
            } else if (a.equals("4")) {
                if (new java.util.Random().nextBoolean()) {
                    System.out.println("Fuite réussie!");
                    return false;
                } else {
                    System.out.println("Fuite échouée, le combat continue.");
                }
            } else {
                System.out.println("Action non reconnue.");
            }

            if (m.estMort()) break;

            // Tour du monstre
            m.attaquer(joueur);
            System.out.println("\nLe " + m.getNom() + " vous attaque! Vos PV: " + joueur.getPV());
        }

        if (!joueur.estVivant()) {
            System.out.println("Vous avez été vaincu...");
            return false;
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
        return true;
    }
}

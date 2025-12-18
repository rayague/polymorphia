package com.polymorphia.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventaire du joueur: contient listes d'objets et monnaie.
 */
public class Inventaire {
    private List<Equipement> armes = new ArrayList<>();
    private List<Equipement> armures = new ArrayList<>();
    private List<Potion> potions = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private List<Materia> materias = new ArrayList<>();
    private int monnaie = 0;

    // Ajoute un objet à l'inventaire (surcharge simplifiée)
    public void ajouter(Objet o) {
        if (o instanceof Equipement) {
            armes.add((Equipement)o);
        } else if (o instanceof Potion) {
            potions.add((Potion)o);
        } else if (o instanceof Sort) {
            sorts.add((Sort)o);
        } else if (o instanceof Materia) {
            materias.add((Materia)o);
        }
    }

    public boolean retirer(Objet o) {
        if (o instanceof Equipement) return armes.remove((Equipement)o) || armures.remove((Equipement)o);
        if (o instanceof Potion) return potions.remove((Potion)o);
        if (o instanceof Sort) return sorts.remove((Sort)o);
        if (o instanceof Materia) return materias.remove((Materia)o);
        return false;
    }

    public List<Equipement> getEquipements() {
        List<Equipement> all = new ArrayList<>();
        all.addAll(armes);
        all.addAll(armures);
        return all;
    }

    public int getMonnaie() { return monnaie; }
    public void ajouterMonnaie(int qte) { monnaie += qte; }
    public boolean depenserMonnaie(int qte) {
        if (monnaie >= qte) { monnaie -= qte; return true; }
        return false;
    }

    // Récupère et retire la première potion disponible (ou null)
    public Potion consommerPremierePotion() {
        if (!potions.isEmpty()) {
            return potions.remove(0);
        }
        return null;
    }

    // Affiche un résumé de l'inventaire (console)
    public void afficherRésumé() {
        System.out.println("\n=== INVENTAIRE ===");
        System.out.println("Intcoins: " + monnaie);
        
        System.out.println("\n--- Armes (" + armes.size() + ") ---");
        for (int i = 0; i < armes.size(); i++) {
            Equipement e = armes.get(i);
            System.out.println("  " + (i+1) + ") " + e.getNom() + " (ATK:+" + e.getBonusAttaque() + ", DEF:+" + e.getBonusDefense() + ")");
        }
        
        System.out.println("\n--- Armures (" + armures.size() + ") ---");
        for (int i = 0; i < armures.size(); i++) {
            Equipement e = armures.get(i);
            System.out.println("  " + (i+1) + ") " + e.getNom() + " (ATK:+" + e.getBonusAttaque() + ", DEF:+" + e.getBonusDefense() + ")");
        }
        
        System.out.println("\n--- Potions (" + potions.size() + ") ---");
        for (int i = 0; i < potions.size(); i++) {
            System.out.println("  " + (i+1) + ") " + potions.get(i).getNom() + " (Soin: " + potions.get(i).getSoin() + " PV)");
        }
        
        System.out.println("\n--- Sorts (" + sorts.size() + ") ---");
        for (int i = 0; i < sorts.size(); i++) {
            Sort s = sorts.get(i);
            System.out.println("  " + (i+1) + ") " + s.getNom() + " (" + s.getType() + ", Puissance: " + s.getPuissance() + ")");
        }
        
        System.out.println("\n--- Materia (" + materias.size() + ") ---");
        for (int i = 0; i < materias.size(); i++) {
            System.out.println("  " + (i+1) + ") " + materias.get(i).getNom() + " (+" + materias.get(i).getPointsAmelioration() + " points)");
        }
        System.out.println("==================");
    }
    
    public List<Potion> getPotions() { return potions; }
    public List<Sort> getSorts() { return sorts; }
    public List<Materia> getMaterias() { return materias; }
}

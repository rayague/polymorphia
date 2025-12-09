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
        if (o instanceof Equipement) return armes.remove(o) || armures.remove(o);
        if (o instanceof Potion) return potions.remove(o);
        if (o instanceof Sort) return sorts.remove(o);
        if (o instanceof Materia) return materias.remove(o);
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
}

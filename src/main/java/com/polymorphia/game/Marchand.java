package com.polymorphia.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Marchand qui possède un stock d'objets et peut vendre au joueur.
 */
public class Marchand {
    private List<Objet> stock = new ArrayList<>();

    public Marchand() {}

    public void afficherStock() {
        System.out.println("Stock du marchand :");
        for (Objet o : stock) {
            System.out.println(" - " + o.getNom() + " (prix: " + o.getPrix() + ")");
        }
    }

    public boolean vendre(Objet o, Joueur j) {
        if (!stock.contains(o)) return false;
        if (j.getInventaire().depenserMonnaie(o.getPrix())) {
            stock.remove(o);
            j.getInventaire().ajouter(o);
            return true;
        }
        return false;
    }

    public void ajouterAuStock(Objet o) { stock.add(o); }
}

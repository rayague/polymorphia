package com.polymorphia.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Marchand qui possède un stock d'objets et peut vendre au joueur.
 */
public class Marchand {
    private List<Objet> stock = new ArrayList<>();

    public Marchand() {}

    // Initialise un stock d'objets basique selon le niveau du joueur
    public void initStockPourNiveau(int niveau) {
        stock.clear();
        stock.add(new Equipement("Epée basique", 10 + niveau*2, 3 + niveau, 0));
        stock.add(new Equipement("Bouclier simple", 8 + niveau*2, 0, 2 + niveau));
        stock.add(new Potion("Potion de soin", 5 + niveau, 10 + niveau*2));
        stock.add(new Materia("Materia mineure", 12 + niveau*3, 2 + niveau));
    }

    public void afficherStock() {
        System.out.println("Stock du marchand :");
        for (Objet o : stock) {
            System.out.println(" - " + o.getNom() + " (prix: " + o.getPrix() + ")");
        }
    }

    // Cherche un objet dans le stock par nom (nom exact)
    public Objet chercherParNom(String nom) {
        for (Objet o : stock) {
            if (o.getNom().equalsIgnoreCase(nom)) return o;
        }
        return null;
    }

    // Vend l'objet au joueur si possible
    public boolean vendreParNom(String nom, Joueur j) {
        Objet o = chercherParNom(nom);
        if (o == null) return false;
        if (j.getInventaire().depenserMonnaie(o.getPrix())) {
            stock.remove(o);
            j.getInventaire().ajouter(o);
            return true;
        }
        return false;
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

package com.polymorphia.game;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Client de jeu multijoueur - Se connecte au serveur pour jouer en réseau
 * Se connecte au serveur via IP:PORT
 */
public class ClientJeu {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private String nomJoueur;
    private String adversaire;
    
    public ClientJeu() {
        scanner = new Scanner(System.in);
    }
    
    public void connecter(String host, int port) {
        try {
            System.out.println("═══════════════════════════════════════");
            System.out.println("🎮 CLIENT POLYMORPHIA");
            System.out.println("═══════════════════════════════════════");
            System.out.println("📡 Connexion au serveur " + host + ":" + port + "...");
            
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            System.out.println("✓ Connecté au serveur!\n");
            
            // Envoyer le nom du joueur
            System.out.print("Entrez votre nom: ");
            nomJoueur = scanner.nextLine().trim();
            out.println(nomJoueur);
            
            // Recevoir le nom de l'adversaire
            String message = in.readLine();
            if (message.startsWith("ADVERSAIRE:")) {
                adversaire = message.substring(11);
                System.out.println("\n⚔️  Votre adversaire: " + adversaire);
                System.out.println("═══════════════════════════════════════\n");
            }
            
            // Boucle de jeu
            jouer();
            
        } catch (IOException e) {
            System.err.println("❌ Erreur de connexion: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fermerConnexion();
        }
    }
    
    private void jouer() {
        try {
            boolean enJeu = true;
            
            while (enJeu) {
                String message = in.readLine();
                
                if (message == null) {
                    break;
                }
                
                if (message.startsWith("ETAT:")) {
                    // Format: ETAT:pvJoueur,pvAdversaire,nomJoueur,nomAdversaire[,ATTENTE]
                    String[] parts = message.substring(5).split(",");
                    int pvAttaquant = Integer.parseInt(parts[0]);
                    int pvDefenseur = Integer.parseInt(parts[1]);
                    String nomAttaquant = parts[2];
                    String nomDefenseur = parts[3];
                    
                    boolean estMonTour = nomAttaquant.equals(nomJoueur);
                    
                    if (parts.length > 4 && parts[4].equals("ATTENTE")) {
                        System.out.println("\n⏳ En attente de " + nomAttaquant + "...");
                        System.out.println("   " + nomAttaquant + ": " + pvAttaquant + " PV");
                        System.out.println("   " + nomDefenseur + ": " + pvDefenseur + " PV");
                    } else {
                        // C'est notre tour
                        afficherEtatCombat(pvAttaquant, pvDefenseur, nomDefenseur);
                        String action = choisirAction();
                        out.println(action);
                        
                        if (action.equals("ABANDONNER")) {
                            enJeu = false;
                        }
                    }
                    
                } else if (message.startsWith("ACTION:")) {
                    System.out.println("\n⚡ " + message.substring(7));
                    
                } else if (message.startsWith("GAGNANT:")) {
                    String gagnant = message.substring(8);
                    afficherResultat(gagnant);
                    enJeu = false;
                }
            }
            
        } catch (IOException e) {
            System.err.println("❌ Erreur de communication: " + e.getMessage());
        }
    }
    
    private void afficherEtatCombat(int mesPV, int pvAdv, String nomAdv) {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│         C'EST VOTRE TOUR!           │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.println("💚 Vous (" + nomJoueur + "): " + mesPV + " PV");
        System.out.println("💔 " + nomAdv + ": " + pvAdv + " PV");
    }
    
    private String choisirAction() {
        System.out.println("\nActions disponibles:");
        System.out.println("  1) Attaquer");
        System.out.println("  2) Utiliser une potion (+20 PV)");
        System.out.println("  3) Abandonner");
        System.out.print("> ");
        
        String choix = scanner.nextLine().trim();
        
        switch (choix) {
            case "1":
                return "ATTAQUER";
            case "2":
                return "POTION";
            case "3":
                return "ABANDONNER";
            default:
                System.out.println("Choix invalide, attaque par défaut.");
                return "ATTAQUER";
        }
    }
    
    private void afficherResultat(String gagnant) {
        System.out.println("\n" + "═".repeat(40));
        if (gagnant.equals(nomJoueur)) {
            System.out.println("🏆 VICTOIRE! Vous avez gagné!");
        } else {
            System.out.println("💀 DÉFAITE! " + gagnant + " a gagné.");
        }
        System.out.println("═".repeat(40));
    }
    
    private void fermerConnexion() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            if (scanner != null) scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   POLYMORPHIA - CLIENT MULTIJOUEUR    ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Adresse IP du serveur (ou 'localhost'): ");
        String host = sc.nextLine().trim();
        if (host.isEmpty()) {
            host = "localhost";
        }
        
        ClientJeu client = new ClientJeu();
        client.connecter(host, 5555);
    }
}

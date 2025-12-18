package com.polymorphia.game;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Serveur de jeu multijoueur - Gère les combats PvP en réseau
 * Architecture: 1 serveur, 2 clients max
 * Port: 5555
 */
public class ServeurJeu {
    private static final int PORT = 5555;
    private static final int MAX_JOUEURS = 2;
    
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private Joueur joueur1;
    private Joueur joueur2;
    private boolean jeuEnCours;
    
    public ServeurJeu() {
        clients = new ArrayList<>();
        jeuEnCours = false;
    }
    
    public void demarrer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("═══════════════════════════════════════");
            System.out.println("🎮 SERVEUR POLYMORPHIA DÉMARRÉ");
            System.out.println("═══════════════════════════════════════");
            System.out.println("📡 Port: " + PORT);
            System.out.println("👥 En attente de " + MAX_JOUEURS + " joueurs...\n");
            
            // Accepter 2 connexions
            while (clients.size() < MAX_JOUEURS) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, clients.size() + 1);
                clients.add(handler);
                System.out.println("✓ Joueur " + clients.size() + " connecté: " + 
                                 clientSocket.getInetAddress().getHostAddress());
            }
            
            System.out.println("\n🎯 Tous les joueurs sont connectés!");
            System.out.println("═══════════════════════════════════════\n");
            
            // Démarrer le jeu
            demarrerPartie();
            
        } catch (IOException e) {
            System.err.println("❌ Erreur serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void demarrerPartie() {
        try {
            // Recevoir les noms des joueurs
            String nom1 = clients.get(0).recevoirMessage();
            String nom2 = clients.get(1).recevoirMessage();
            
            joueur1 = new Joueur(nom1, 50, 12, 6);
            joueur2 = new Joueur(nom2, 50, 12, 6);
            
            System.out.println("⚔️  COMBAT: " + nom1 + " VS " + nom2 + "\n");
            
            // Informer les clients
            clients.get(0).envoyerMessage("ADVERSAIRE:" + nom2);
            clients.get(1).envoyerMessage("ADVERSAIRE:" + nom1);
            
            jeuEnCours = true;
            boolean tourJ1 = true;
            
            // Boucle de jeu
            while (joueur1.estVivant() && joueur2.estVivant() && jeuEnCours) {
                Joueur attaquant = tourJ1 ? joueur1 : joueur2;
                Joueur defenseur = tourJ1 ? joueur2 : joueur1;
                ClientHandler clientActif = clients.get(tourJ1 ? 0 : 1);
                ClientHandler clientPassif = clients.get(tourJ1 ? 1 : 0);
                
                // Envoyer état du jeu
                String etat = "ETAT:" + attaquant.getPV() + "," + defenseur.getPV() + 
                             "," + attaquant.getNom() + "," + defenseur.getNom();
                clientActif.envoyerMessage(etat);
                clientPassif.envoyerMessage(etat + ",ATTENTE");
                
                // Recevoir action du joueur actif
                String action = clientActif.recevoirMessage();
                System.out.println(attaquant.getNom() + " -> " + action);
                
                if (action.equals("ABANDONNER")) {
                    System.out.println("❌ " + attaquant.getNom() + " abandonne!");
                    attaquant.perdrePV(attaquant.getPV());
                    jeuEnCours = false;
                } else if (action.equals("ATTAQUER")) {
                    int degats = Math.max(1, attaquant.getAttaque() - defenseur.getDefense());
                    defenseur.perdrePV(degats);
                    diffuserMessage("ACTION:" + attaquant.getNom() + " attaque! " + 
                                  defenseur.getNom() + " perd " + degats + " PV");
                } else if (action.equals("POTION")) {
                    attaquant.gagnerPV(20);
                    diffuserMessage("ACTION:" + attaquant.getNom() + " utilise une potion (+20 PV)");
                }
                
                tourJ1 = !tourJ1;
            }
            
            // Annoncer le gagnant
            String gagnant = joueur1.estVivant() ? joueur1.getNom() : joueur2.getNom();
            System.out.println("\n🏆 VICTOIRE: " + gagnant);
            diffuserMessage("GAGNANT:" + gagnant);
            
        } catch (IOException e) {
            System.err.println("❌ Erreur pendant la partie: " + e.getMessage());
        } finally {
            fermerConnexions();
        }
    }
    
    private void diffuserMessage(String message) {
        for (ClientHandler client : clients) {
            try {
                client.envoyerMessage(message);
            } catch (IOException e) {
                System.err.println("Erreur diffusion: " + e.getMessage());
            }
        }
    }
    
    private void fermerConnexions() {
        for (ClientHandler client : clients) {
            client.fermer();
        }
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Classe interne pour gérer chaque client
    private class ClientHandler {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private int numeroJoueur;
        
        public ClientHandler(Socket socket, int numero) throws IOException {
            this.socket = socket;
            this.numeroJoueur = numero;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }
        
        public void envoyerMessage(String message) throws IOException {
            out.println(message);
        }
        
        public String recevoirMessage() throws IOException {
            return in.readLine();
        }
        
        public void fermer() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   POLYMORPHIA - SERVEUR MULTIJOUEUR   ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        ServeurJeu serveur = new ServeurJeu();
        serveur.demarrer();
    }
}

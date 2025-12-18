# 🌐 Architecture Réseau Multijoueur - Polymorphia

## 📋 Vue d'ensemble

Ce document décrit l'architecture client-serveur mise en place pour permettre des combats PvP en réseau local (LAN).

---

## 🏗️ Architecture Technique

### **Topologie Réseau**

```
        RÉSEAU LOCAL (LAN)
┌─────────────────────────────────────────────┐
│                                             │
│  PC 1 (192.168.1.10)        PC 2 (Serveur) │
│  ┌─────────────┐            ┌────────────┐ │
│  │ ClientJeu   │            │ ServeurJeu │ │
│  │ (Joueur 1)  │◄──TCP─────►│  Port 5555 │ │
│  └─────────────┘            └────────────┘ │
│                                    ▲        │
│                                    │ TCP    │
│  PC 3 (192.168.1.15)               │        │
│  ┌─────────────┐                   │        │
│  │ ClientJeu   │───────────────────┘        │
│  │ (Joueur 2)  │                            │
│  └─────────────┘                            │
│                                             │
└─────────────────────────────────────────────┘
```

---

## 🎯 Rôles et Responsabilités

### **SERVEUR (ServeurJeu.java)**

**Responsabilités:**
- ✅ Écoute sur le port **5555**
- ✅ Accepte **maximum 2 connexions** simultanées
- ✅ Gère la **logique du combat** (tour par tour)
- ✅ Valide les **actions des joueurs**
- ✅ Synchronise **l'état du jeu** entre les clients
- ✅ Calcule les **dégâts** et mises à jour PV
- ✅ Détermine et annonce le **gagnant**

**Cycle de vie:**
1. Démarre et écoute sur le port 5555
2. Attend 2 connexions clientes
3. Reçoit les noms des joueurs
4. Initialise les objets Joueur (50 PV chacun)
5. Lance la boucle de combat
6. Alterne les tours entre joueurs
7. Diffuse les actions et états
8. Annonce le gagnant
9. Ferme les connexions

### **CLIENT (ClientJeu.java)**

**Responsabilités:**
- ✅ Se connecte au serveur via **IP:PORT**
- ✅ Envoie le **nom du joueur**
- ✅ Reçoit les **états du jeu** en temps réel
- ✅ Envoie les **actions** du joueur
- ✅ Affiche l'**interface console**
- ✅ Gère les notifications d'attente

**Cycle de vie:**
1. Demande l'IP du serveur
2. Se connecte au serveur
3. Envoie son nom
4. Reçoit le nom de l'adversaire
5. Attend son tour
6. Choisit une action (attaquer/potion/abandonner)
7. Envoie l'action au serveur
8. Reçoit le résultat
9. Répète jusqu'à la fin
10. Affiche le résultat final

---

## 📡 Protocole de Communication

### **Format des Messages**

#### **Client → Serveur**
| Message | Description | Exemple |
|---------|-------------|---------|
| `<nom>` | Nom du joueur | `Javalt` |
| `ATTAQUER` | Action d'attaque | `ATTAQUER` |
| `POTION` | Utiliser une potion | `POTION` |
| `ABANDONNER` | Abandonner le combat | `ABANDONNER` |

#### **Serveur → Client**
| Message | Description | Exemple |
|---------|-------------|---------|
| `ADVERSAIRE:<nom>` | Nom de l'adversaire | `ADVERSAIRE:Ray` |
| `ETAT:pv1,pv2,nom1,nom2` | État du jeu (ton tour) | `ETAT:45,30,Javalt,Ray` |
| `ETAT:...,ATTENTE` | État du jeu (attente) | `ETAT:45,30,Ray,Javalt,ATTENTE` |
| `ACTION:<texte>` | Description d'une action | `ACTION:Javalt attaque! Ray perd 5 PV` |
| `GAGNANT:<nom>` | Annonce du gagnant | `GAGNANT:Javalt` |

### **Séquence d'un Tour**

```
CLIENT 1            SERVEUR            CLIENT 2
   │                   │                   │
   │  ──ATTAQUER──>    │                   │
   │                   │ (Calcul dégâts)   │
   │                   │                   │
   │  <──ACTION:...    │  ──ACTION:...──>  │
   │                   │                   │
   │  <──ETAT:...,ATT  │  ──ETAT:...──>    │
   │                   │                   │
```

---

## ⚙️ Technologies Utilisées

### **Java Sockets (TCP)**

**Pourquoi TCP et pas UDP?**
- ✅ **Fiabilité**: Garantit la livraison des messages
- ✅ **Ordre**: Messages reçus dans l'ordre d'envoi
- ✅ **Simplicité**: API Java simple et robuste
- ✅ **Adapté au tour par tour**: Pas besoin de vitesse temps réel
- ✅ **Gestion d'erreurs**: Détection de déconnexion automatique

**Classes Java utilisées:**
- `ServerSocket`: Écoute des connexions entrantes
- `Socket`: Communication bidirectionnelle
- `BufferedReader`: Lecture des messages texte
- `PrintWriter`: Envoi de messages texte

---

## 🚀 Guide de Démarrage

### **Prérequis**
- Java 11+ installé
- 2-3 ordinateurs sur le même réseau local
- Pare-feu configuré pour autoriser le port 5555

### **Étape 1: Compiler le code**

```bash
cd C:\Users\hp\OneDrive\Desktop\polymorphia
javac -d out src\main\java\com\polymorphia\game\*.java
```

### **Étape 2: Démarrer le Serveur (PC 1)**

```bash
java -cp out com.polymorphia.game.ServeurJeu
```

**Sortie attendue:**
```
═══════════════════════════════════════
🎮 SERVEUR POLYMORPHIA DÉMARRÉ
═══════════════════════════════════════
📡 Port: 5555
👥 En attente de 2 joueurs...
```

**Note:** Trouvez l'adresse IP du serveur:
```bash
# Windows
ipconfig

# Linux/Mac
ifconfig
```
Exemple: `192.168.1.100`

### **Étape 3: Connecter les Clients (PC 2 et PC 3)**

```bash
java -cp out com.polymorphia.game.ClientJeu
```

**Interaction:**
```
╔═══════════════════════════════════════╗
║   POLYMORPHIA - CLIENT MULTIJOUEUR    ║
╚═══════════════════════════════════════╝

Adresse IP du serveur (ou 'localhost'): 192.168.1.100
═══════════════════════════════════════
🎮 CLIENT POLYMORPHIA
═══════════════════════════════════════
📡 Connexion au serveur 192.168.1.100:5555...
✓ Connecté au serveur!

Entrez votre nom: Javalt
```

### **Étape 4: Combat**

Le jeu démarre automatiquement quand 2 joueurs sont connectés!

---

## 🎮 Exemple de Session de Jeu

### **Serveur**
```
✓ Joueur 1 connecté: 192.168.1.10
✓ Joueur 2 connecté: 192.168.1.15

🎯 Tous les joueurs sont connectés!
═══════════════════════════════════════

⚔️  COMBAT: Javalt VS Ray

Javalt -> ATTAQUER
Ray -> POTION
Javalt -> ATTAQUER
Ray -> ATTAQUER
...

🏆 VICTOIRE: Javalt
```

### **Client 1 (Joueur actif)**
```
⚔️  Votre adversaire: Ray
═══════════════════════════════════════

┌─────────────────────────────────────┐
│         C'EST VOTRE TOUR!           │
└─────────────────────────────────────┘
💚 Vous (Javalt): 50 PV
💔 Ray: 50 PV

Actions disponibles:
  1) Attaquer
  2) Utiliser une potion (+20 PV)
  3) Abandonner
> 1

⚡ Javalt attaque! Ray perd 6 PV
```

### **Client 2 (En attente)**
```
⏳ En attente de Javalt...
   Javalt: 50 PV
   Ray: 50 PV

⚡ Javalt attaque! Ray perd 6 PV
```

---

## 🔧 Configuration Réseau

### **Ouvrir le Port (Windows Firewall)**

```powershell
# PowerShell (Admin)
New-NetFirewallRule -DisplayName "Polymorphia Serveur" -Direction Inbound -Protocol TCP -LocalPort 5555 -Action Allow
```

### **Test de Connectivité**

```bash
# Sur un client, tester la connexion
telnet 192.168.1.100 5555
```

### **Résolution de Problèmes**

| Problème | Solution |
|----------|----------|
| "Connection refused" | Vérifier que le serveur est démarré |
| "No route to host" | Vérifier IP et réseau local |
| "Connection timeout" | Désactiver pare-feu ou ouvrir port 5555 |
| Client se déconnecte | Vérifier stabilité réseau |

---

## 📊 Données Échangées

### **Volume de Données**
- Messages texte courts (~50-100 octets)
- Bande passante: < 1 KB/s
- Latence: ~10-50ms sur LAN

### **Sécurité**
⚠️ **Important:** Ce système est conçu pour LAN uniquement
- Pas de chiffrement
- Pas d'authentification
- Ne PAS exposer sur Internet

---

## 🎯 Avantages de cette Architecture

✅ **Simple**: Code facile à comprendre et maintenir  
✅ **Robuste**: TCP garantit la fiabilité  
✅ **Scalable**: Facile d'ajouter plus de fonctionnalités  
✅ **Performant**: Latence très faible en LAN  
✅ **Standard**: Utilise des protocoles éprouvés  

---

## 🔮 Évolutions Futures

- [ ] Support de plus de 2 joueurs
- [ ] Mode tournoi
- [ ] Chat intégré
- [ ] Reconnexion automatique
- [ ] Mode spectateur
- [ ] Logs de combat
- [ ] Interface graphique

---

## 📝 Notes Techniques

**Thread Safety:** Le serveur gère les clients de manière séquentielle (pas de multithreading nécessaire pour 2 joueurs)

**Gestion d'erreurs:** Les IOExceptions sont capturées et logguées

**Fermeture propre:** Les ressources sont libérées dans des blocs `finally`

---

**Auteurs:** Votre équipe  
**Date:** Décembre 2025  
**Version:** 1.0

# 🎮 Architecture Complète - Polymorphia

## Vue d'Ensemble des 3 Modes

```
┌─────────────────────────────────────────────────────────────────┐
│                     POLYMORPHIA - JEU RPG                        │
└─────────────────────────────────────────────────────────────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
        ▼                       ▼                       ▼
┌──────────────┐      ┌──────────────┐      ┌──────────────────┐
│  MODE SOLO   │      │  PVP LOCAL   │      │ MULTIJOUEUR LAN  │
│    (PvE)     │      │  (1 PC)      │      │   (Réseau TCP)   │
└──────────────┘      └──────────────┘      └──────────────────┘
```

---

## 📊 Détail Architecture par Mode

### **MODE 1: Solo (PvE)**

```
┌─────────────┐
│  Jeu.java   │
│  (Main)     │
└──────┬──────┘
       │
       ├──► Joueur ──► Inventaire ──► Objets
       │
       ├──► MonstreFactory ──► Monstre
       │
       └──► Marchand ──► Stock
```

**Flux:**
1. Joueur explore
2. Rencontre monstre aléatoire
3. Combat au tour par tour
4. Récompenses (intcoins, objets)
5. Commerce avec marchand
6. Progression (XP, niveau)

---

### **MODE 2: PvP Local (1 PC)**

```
┌─────────────────────────────────┐
│         Jeu.java                │
│   (Option 7: Combat PvP)        │
└────────────┬────────────────────┘
             │
    ┌────────┴────────┐
    │                 │
    ▼                 ▼
┌─────────┐      ┌─────────┐
│ Joueur1 │      │ Joueur2 │
│ (50 PV) │◄────►│ (50 PV) │
└─────────┘      └─────────┘
```

**Flux:**
1. Menu PvP → Création 2 joueurs
2. Phase de préparation (achats, équipement)
3. Combat au tour par tour
4. Alternance clavier
5. Annonce du gagnant

---

### **MODE 3: Multijoueur Réseau (LAN)**

```
        RÉSEAU LOCAL (192.168.x.x)
┌────────────────────────────────────────┐
│                                        │
│  PC 1                  PC 2 (Serveur)  │
│  ┌──────────┐         ┌────────────┐  │
│  │ClientJeu │         │ServeurJeu  │  │
│  │          │◄──TCP──►│ Port 5555  │  │
│  │Joueur 1  │  5555   │            │  │
│  └──────────┘         └────┬───────┘  │
│                            │ TCP       │
│  PC 3                      │           │
│  ┌──────────┐              │           │
│  │ClientJeu │──────────────┘           │
│  │Joueur 2  │                          │
│  └──────────┘                          │
│                                        │
└────────────────────────────────────────┘
```

**Architecture Client-Serveur:**

#### **Serveur (ServeurJeu.java)**
```java
ServerSocket (port 5555)
    │
    ├──► ClientHandler 1 (Socket)
    │        │
    │        ├──► BufferedReader (in)
    │        └──► PrintWriter (out)
    │
    └──► ClientHandler 2 (Socket)
             │
             ├──► BufferedReader (in)
             └──► PrintWriter (out)
```

**Responsabilités Serveur:**
- ✅ Écoute connexions (max 2)
- ✅ Initialise joueurs (50 PV)
- ✅ Gère logique combat
- ✅ Calcule dégâts
- ✅ Synchronise états
- ✅ Détermine gagnant

#### **Client (ClientJeu.java)**
```java
Socket ──► Serveur (IP:5555)
    │
    ├──► BufferedReader (in) ──► Messages serveur
    │
    └──► PrintWriter (out) ──► Actions joueur
```

**Responsabilités Client:**
- ✅ Connexion au serveur
- ✅ Interface utilisateur console
- ✅ Envoi actions (ATTAQUER/POTION/ABANDONNER)
- ✅ Réception et affichage états
- ✅ Affichage résultat

---

## 🔄 Protocole de Communication

### **Diagramme de Séquence**

```
CLIENT 1         SERVEUR         CLIENT 2
   │                │                │
   │──NOM: Javalt───►│                │
   │                │◄──NOM: Ray─────│
   │                │                │
   │◄─ADVERSAIRE────│──ADVERSAIRE───►│
   │                │                │
   │◄──ETAT:50,50───│────ETAT:...,ATT──►│
   │                │                │
   │──ATTAQUER──────►│                │
   │                │ (calcul)       │
   │◄──ACTION:...───│───ACTION:...──►│
   │                │                │
   │◄──ETAT:...,ATT─│────ETAT:44,50─►│
   │                │◄──POTION───────│
   │◄──ACTION:...───│───ACTION:...──►│
   │                │                │
   │      ...       │      ...       │
   │                │                │
   │◄──GAGNANT──────│───GAGNANT─────►│
   │                │                │
```

### **Messages du Protocole**

| Direction | Format | Exemple |
|-----------|--------|---------|
| C→S | `<nom>` | `Javalt` |
| C→S | `ATTAQUER` | `ATTAQUER` |
| C→S | `POTION` | `POTION` |
| C→S | `ABANDONNER` | `ABANDONNER` |
| S→C | `ADVERSAIRE:<nom>` | `ADVERSAIRE:Ray` |
| S→C | `ETAT:<pv1>,<pv2>,<n1>,<n2>` | `ETAT:45,30,Javalt,Ray` |
| S→C | `ETAT:...,ATTENTE` | `ETAT:45,30,Ray,Javalt,ATTENTE` |
| S→C | `ACTION:<description>` | `ACTION:Javalt attaque! Ray perd 5 PV` |
| S→C | `GAGNANT:<nom>` | `GAGNANT:Javalt` |

---

## 🗂️ Structure des Fichiers

```
polymorphia/
│
├── src/main/java/com/polymorphia/game/
│   ├── Jeu.java              ✅ Point d'entrée (solo + PvP local)
│   ├── ServeurJeu.java       ✅ Serveur réseau (TCP)
│   ├── ClientJeu.java        ✅ Client réseau
│   ├── Joueur.java           ✅ Modèle joueur
│   ├── Monstre.java          ✅ Modèle monstre
│   ├── MonstreFactory.java   ✅ Création monstres
│   ├── Inventaire.java       ✅ Gestion inventaire
│   ├── Marchand.java         ✅ Commerce
│   ├── Objet.java            ✅ Classe de base objets
│   ├── Equipement.java       ✅ Armes/Armures
│   ├── Potion.java           ✅ Potions de soin
│   ├── Sort.java             ✅ Sorts magiques
│   ├── Materia.java          ✅ Amélioration équipement
│   ├── Serveur.java          ⚠️  Ancien (placeholder)
│   └── Client.java           ⚠️  Ancien (placeholder)
│
├── out/                      ✅ Classes compilées
│
├── README.md                 ✅ Documentation principale
├── ARCHITECTURE_RESEAU.md    ✅ Documentation réseau détaillée
├── GUIDE_RAPIDE.md           ✅ Guide de démarrage
├── DIAGRAMMES.md             📄 Ce fichier
│
├── demarrer-serveur.bat      ✅ Script serveur
└── demarrer-client.bat       ✅ Script client
```

---

## ⚙️ Technologies Utilisées

### **Langage**
- ☕ **Java 11+**

### **Réseau**
- 🌐 **Java Sockets (TCP)**
  - `java.net.ServerSocket`
  - `java.net.Socket`
  - `java.io.BufferedReader`
  - `java.io.PrintWriter`

### **Pourquoi TCP?**
| Critère | TCP | UDP |
|---------|-----|-----|
| Fiabilité | ✅ Garantie | ❌ Non garanti |
| Ordre | ✅ Préservé | ❌ Non garanti |
| Simplicité | ✅ API simple | ⚠️  Plus complexe |
| Tour par tour | ✅ Parfait | ⚠️  Overkill |
| Latence LAN | ✅ ~10-50ms | ✅ ~5-30ms |

**Conclusion:** TCP est idéal pour ce jeu au tour par tour en LAN

---

## 📈 Performances

### **Réseau**
- **Latence:** 10-50ms (LAN)
- **Bande passante:** < 1 KB/s
- **Taille messages:** 50-100 octets
- **Fréquence:** ~1 message/seconde

### **Scalabilité**
- **Joueurs supportés:** 2 (actuellement)
- **Extensions possibles:**
  - Mode tournoi (4-8 joueurs)
  - Spectateurs
  - Plusieurs combats simultanés

---

## 🔒 Sécurité

⚠️ **ATTENTION: Réseau local uniquement!**

| Aspect | Status | Note |
|--------|--------|------|
| Chiffrement | ❌ Non | Données en clair |
| Authentification | ❌ Non | Pas de login |
| Validation | ⚠️  Basique | Validation serveur |
| Firewall | ⚠️  Requis | Port 5555 ouvert |

**Recommandations:**
- ✅ Utiliser uniquement sur LAN privé
- ❌ NE PAS exposer sur Internet
- ✅ Configurer pare-feu correctement
- ✅ Tester sur réseau de confiance

---

## 🎯 Avantages de l'Architecture

### **Simplicité**
✅ Code facile à comprendre  
✅ Pas de dépendances externes  
✅ Architecture classique client-serveur  

### **Robustesse**
✅ TCP garantit la fiabilité  
✅ Gestion d'erreurs complète  
✅ Fermeture propre des connexions  

### **Performance**
✅ Latence très faible en LAN  
✅ Pas de surcharge réseau  
✅ Adapté au tour par tour  

### **Maintenabilité**
✅ Code bien structuré  
✅ Séparation des responsabilités  
✅ Extensible facilement  

---

## 🚀 Évolutions Futures

### **Court Terme**
- [ ] Mode spectateur
- [ ] Chat intégré
- [ ] Logs de combat
- [ ] Reconnexion automatique

### **Moyen Terme**
- [ ] Support 4+ joueurs
- [ ] Mode tournoi
- [ ] Classement global
- [ ] Replay de combats

### **Long Terme**
- [ ] Interface graphique (JavaFX)
- [ ] Mode campagne coop
- [ ] Serveur dédié
- [ ] Système de guildes

---

**Créé par:** Votre équipe  
**Date:** Décembre 2025  
**Version:** 1.0 - Réseau Complet

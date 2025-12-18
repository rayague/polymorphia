# 🎮 POLYMORPHIA
### Jeu de Combat RPG Multijoueur en Réseau

---

## 📖 Description

**Polymorphia** est un jeu de rôle (RPG) de combat au tour par tour en console. Combattez des monstres, progressez en niveau, achetez des équipements et affrontez vos amis en réseau local !

### ✨ Modes de Jeu

1. **🐉 Mode Solo (PvE)** - Combattez des monstres et progressez
2. **👥 Mode PvP Local** - Duel sur le même ordinateur
3. **🌐 Mode Réseau (LAN)** - Jouez contre un ami sur un autre PC !

---

## 🚀 Comment Jouer - Guide Simple

### 🎯 Mode Réseau (2 Joueurs sur 2 PC différents)

#### **📋 Ce dont vous avez besoin:**
- 2 ordinateurs sur le même réseau WiFi ou reliés par câble Ethernet
- Java installé sur les deux PC
- Les fichiers du jeu sur les deux PC

#### **🔧 Étape 1: Lancer le Serveur (PC 1)**

Double-cliquez sur `demarrer-serveur.bat`

Vous verrez:
```
╔═══════════════════════════════════════╗
║   POLYMORPHIA - SERVEUR MULTIJOUEUR   ║
╚═══════════════════════════════════════╝

🎮 SERVEUR POLYMORPHIA DÉMARRÉ
📡 Port: 5555
🌐 IP: 192.168.1.100    ← Notez cette adresse!
👥 En attente de 2 joueurs...
```

**⚠️ Important:** Notez l'adresse IP affichée (ex: `192.168.1.100`)

---

#### **🔧 Étape 2: Connecter les Joueurs (PC 1 et PC 2)**

Sur **chaque PC joueur**, double-cliquez sur `demarrer-client.bat`

Le client vous demandera:
```
╔═══════════════════════════════════════╗
║   POLYMORPHIA - CLIENT MULTIJOUEUR    ║
╚═══════════════════════════════════════╝

Adresse IP du serveur (ou 'localhost'):
```

**Tapez l'adresse IP du serveur** (celle notée à l'étape 1)
- Si vous jouez sur le PC serveur, tapez: `localhost`
- Si vous jouez sur un autre PC, tapez: `192.168.1.100` (l'IP du serveur)

Puis entrez votre nom:
```
Entrez votre nom: Ray
```

---

#### **🔧 Étape 3: Le Combat Commence !**

Dès que 2 joueurs sont connectés, le combat démarre automatiquement !

**À votre tour, vous verrez:**
```
┌─────────────────────────────────────┐
│         C'EST VOTRE TOUR!           │
└─────────────────────────────────────┘
💚 Vous (Ray): 50 PV
💔 Maha: 50 PV

Actions disponibles:
  1) Attaquer
  2) Utiliser une potion (+20 PV)
  3) Acheter équipement/armure
  4) Abandonner
>
```

**Tapez le numéro de votre choix et appuyez sur ENTRÉE**

---

### 🏪 Menu d'Achat (Option 3)

Pendant le combat, vous pouvez acheter des équipements !

```
╔════════════════ MARCHAND ════════════════╗
║                                          ║
║  🛡️  ÉQUIPEMENTS DISPONIBLES:            ║
║                                          ║
║  1) Épée en fer      - 30 💰 (+3 ATK)   ║
║  2) Épée en acier    - 50 💰 (+5 ATK)   ║
║  3) Bouclier en bois - 25 💰 (+2 DEF)   ║
║  4) Armure légère    - 40 💰 (+3 DEF)   ║
║  5) Armure lourde    - 70 💰 (+6 DEF)   ║
║  6) Potion           - 15 💰 (+20 PV)   ║
║  7) Annuler l'achat                      ║
╚══════════════════════════════════════════╝
```

💰 **Vous commencez avec 100 pièces d'or**
⚔️ **Les équipements augmentent vos stats immédiatement !**

---

### 🎮 Mode Solo (Entraînement)

Pour jouer seul contre des monstres:

**Windows:**
```
java -cp out com.polymorphia.game.Jeu
```

**Puis choisissez l'option 1** dans le menu

---

## 📦 Installation

### **Prérequis:**
- Java 11 ou plus récent

### **Vérifier Java:**
```bash
java -version
```

### **Compiler le Jeu:**
```bash
javac -encoding UTF-8 -d out src\main\java\com\polymorphia\game\*.java
```

✅ Si aucune erreur n'apparaît, le jeu est prêt !

---

## 🎯 Règles du Jeu

### **Statistiques**
- **💚 PV (Points de Vie):** Si = 0, vous perdez
- **⚔️ ATK (Attaque):** Dégâts infligés à l'ennemi
- **🛡️ DEF (Défense):** Réduit les dégâts reçus
- **💰 Argent:** Pour acheter des équipements

### **Combat**
- Dégâts = `ATK de l'attaquant - DEF du défenseur` (minimum 1)
- Chaque joueur commence avec **50 PV** et **100 pièces d'or**
- Le premier à 0 PV perd
- Abandonnez avec l'option 4

### **Stratégies Gagnantes**
1. 🛡️ **Défense d'abord:** Achetez une armure pour encaisser les coups
2. ⚔️ **Attaque ensuite:** Augmentez vos dégâts avec une épée
3. 💊 **Gardez de l'argent:** Pour acheter des potions en urgence
4. ⏱️ **Timez vos achats:** Achetez au bon moment tactique

---

## 🛠️ Résolution de Problèmes

### ❌ "Connection refused"
- ✅ Vérifiez que le serveur est bien lancé
- ✅ Vérifiez l'adresse IP (faites `ipconfig` dans CMD)
- ✅ Désactivez le pare-feu Windows temporairement

### ❌ Le client ne trouve pas le serveur
- ✅ Les 2 PC doivent être sur le **même réseau WiFi**
- ✅ Notez bien l'IP affichée par le serveur
- ✅ Utilisez `localhost` si vous jouez sur le PC serveur

### ❌ "Cannot find symbol" lors de la compilation
```bash
# Utilisez ceci pour compiler avec le bon encodage:
javac -encoding UTF-8 -d out src\main\java\com\polymorphia\game\*.java
```

### ❌ Le jeu ne répond pas
- ✅ Vérifiez que vous avez bien appuyé sur **ENTRÉE** après votre choix
- ✅ Attendez votre tour (regardez qui est affiché en haut)

---

## 📂 Structure du Projet

```
polymorphia/
│
├── 📁 src/main/java/com/polymorphia/game/
│   ├── 🎮 Jeu.java              # Jeu principal (solo/PvP local)
│   ├── 🌐 ServeurJeu.java       # Serveur réseau
│   ├── 💻 ClientJeu.java        # Client réseau
│   ├── 👤 Joueur.java           # Gestion du joueur
│   ├── 👹 Monstre.java          # Ennemis
│   ├── 🏭 MonstreFactory.java   # Création de monstres
│   ├── 🎒 Inventaire.java       # Gestion inventaire
│   ├── 🏪 Marchand.java         # Boutique
│   ├── ⚔️ Equipement.java       # Armes et armures
│   ├── 💊 Potion.java           # Potions de soin
│   ├── ✨ Sort.java             # Sorts magiques
│   ├── 💎 Materia.java          # Materia (buffs)
│   └── 📦 Objet.java            # Classe de base
│
├── 📁 out/                      # Fichiers compilés
├── 🚀 demarrer-serveur.bat      # Lance le serveur
├── 🚀 demarrer-client.bat       # Lance le client
├── 📝 pom.xml                   # Configuration Maven
└── 📖 README.md                 # Ce fichier
```

---

## 🔧 Technologies Utilisées

| Technologie | Usage |
|-------------|-------|
| **Java 11+** | Langage principal |
| **Java Sockets (TCP)** | Communication réseau |
| **Port 5555** | Port de communication |
| **Maven** | Gestion de build |

---

## 💡 Conseils pour Bien Jouer

### 🏆 Stratégie Débutant (100 💰)
1. Achetez **Bouclier en bois** (25💰) pour la défense
2. Achetez **Épée en fer** (30💰) pour l'attaque
3. Gardez 45💰 pour 3 potions d'urgence

### 🏆 Stratégie Équilibrée (100 💰)
1. Achetez **Armure légère** (40💰)
2. Achetez **Épée en fer** (30💰)
3. Gardez 30💰 pour 2 potions

### 🏆 Stratégie Agressive (100 💰)
1. Achetez **Épée en acier** (50💰) directement
2. Achetez **Bouclier en bois** (25💰)
3. Gardez 25💰 de réserve

---

## 👥 Auteurs

**Créé par:**
- **Ray Ague** 🎮
- **Maha Sabbar** 💻

---

## 📜 Licence

Projet éducatif - Libre d'utilisation et de modification

---

## 🎉 Amusez-vous bien !

**⚔️ Que le meilleur guerrier gagne ! 🏆**

---

**Version:** 1.0  
**Date:** Décembre 2025  
**Langage:** Java 11+

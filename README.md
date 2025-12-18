Polymorphia — Guide complet du projet
====================================

Ce fichier décrit l'architecture du projet, le fonctionnement du jeu (mode solo), les interactions possibles
et comment démarrer/étendre le projet. Tout est rédigé en français pour le TP.

**Archi :**
- **But du projet :** un petit RPG heroic-fantasy, jouable en console, avec exploration, marchand, combat au tour-par-tour,
	loot, montée en niveau et une base pour PvP multijoueur.
- **Langage :** Java (code situé sous `src/main/java/com/polymorphia/game`).
- **Structure fichiers importants :**
	- `src/main/java/com/polymorphia/game/Jeu.java` : point d'entrée (menu console, boucle principale).
	- `src/main/java/com/polymorphia/game/Joueur.java` : modèle du joueur (PV, attaque, défense, niveau, inventaire).
	- `src/main/java/com/polymorphia/game/Inventaire.java` : gestion des objets du joueur (armes, armures, potions, sorts, materia, monnaie).
	- `src/main/java/com/polymorphia/game/Objet.java` : classe de base pour tous les objets.
	- `src/main/java/com/polymorphia/game/Equipement.java` : armes/armures (bonus attaque/défense, amélioration).
	- `src/main/java/com/polymorphia/game/Potion.java` : potions (soin).
	- `src/main/java/com/polymorphia/game/Sort.java` : sorts (attaque/défense selon type).
	- `src/main/java/com/polymorphia/game/Materia.java` : materia, améliore un équipement.
	- `src/main/java/com/polymorphia/game/Monstre.java` : modèle de monstre (PV, attaque, défense, récompense).
	- `src/main/java/com/polymorphia/game/MonstreFactory.java` : usine pour créer des monstres aléatoires.
	- `src/main/java/com/polymorphia/game/Marchand.java` : stock et ventes d'objets.
	- `src/main/java/com/polymorphia/game/ServeurJeu.java` : serveur multijoueur réseau (TCP, port 5555).
	- `src/main/java/com/polymorphia/game/ClientJeu.java` : client multijoueur pour connexion réseau.
	- `src/main/java/com/polymorphia/game/Serveur.java` et `Client.java` : squelettes pour la partie multijoueur local.

Architecture logique
- Le code utilise des objets simples (POJO) pour représenter le domaine (joueur, monstres, objets).
- L'inventaire contient des listes typées (armes, armures, potions, sorts, materias) et la monnaie (intcoins).
- `Jeu.java` orchestre l'interface console : menu, commerce, exploration, combat PvE et PvP local.
- **`ServeurJeu.java` et `ClientJeu.java`** : architecture client-serveur TCP pour le multijoueur en réseau (LAN).

Modes de Jeu
------------
### **1. Mode Solo (PvE)**
Combat contre des monstres, commerce, exploration, progression.

### **2. Mode PvP Local**
Combat entre 2 joueurs sur le même PC (option 7 du menu).

### **3. Mode Multijoueur Réseau (NOUVEAU!)**
Combat entre 2 joueurs sur des PC différents via réseau local.
- **Serveur:** `ServeurJeu.java` (écoute port 5555)
- **Clients:** `ClientJeu.java` (se connectent au serveur)
- **Voir:** [GUIDE_RAPIDE.md](GUIDE_RAPIDE.md) et [ARCHITECTURE_RESEAU.md](ARCHITECTURE_RESEAU.md)

Comment démarrer (Windows — `cmd.exe`)
------------------------------------

### Mode Solo / PvP Local

1) Ouvrez un terminal et placez-vous dans le dossier du projet (contenant `src` et ce `README.md`) :

```cmd
cd /d "c:\Users\hp\OneDrive\Desktop\polymorphia"
```

2) Compiler toutes les classes :

```cmd
javac -d out src\main\java\com\polymorphia\game\*.java
```

3) Lancer le jeu (classe principale) :

```cmd
java -cp out com.polymorphia.game.Jeu
```

### Mode Multijoueur Réseau (3 PC requis)

**PC 1 (Serveur):**
```cmd
demarrer-serveur.bat
OU
java -cp out com.polymorphia.game.ServeurJeu
```

**PC 2 et PC 3 (Clients):**
```cmd
demarrer-client.bat
OU
java -cp out com.polymorphia.game.ClientJeu
```

**Documentation complète:** Voir [GUIDE_RAPIDE.md](GUIDE_RAPIDE.md)

Remarques : si vous utilisez VS Code, ouvrez le dossier `Polymorphia` et installez l'extension Java pour lancer `Jeu` directement.

Gameplay (description complète)
--------------------------------
Le jeu propose un mode solo console complet et une base pour PvP.

1) État du joueur
- `Joueur` a : `nom`, `pv` (points de vie), `attaque`, `defense`, `niveau`, `experience` et un `Inventaire`.
- Monter de niveau augmente automatiquement l'attaque, la défense et les PV (logique simple dans `ajouterExperience`).

2) Inventaire et objets
- `Inventaire` contient : listes d'`Equipement` (armes et armures), `Potion`, `Sort`, `Materia` et un entier `monnaie`.
- `Equipement` : possède `bonusAttaque` et `bonusDefense` et une méthode `ameliorer(int points)`.
- `Potion` : possède une valeur de soin ; `utiliser` applique le soin au joueur.
- `Sort` : peut être de type `ATTAQUE` ou `DEFENSE`; `lancer` applique son effet.
- `Materia` : applique `appliquer(Equipement e)` pour améliorer un équipement.

3) Marchand
- `Marchand` possède un `stock` (liste d'`Objet`).
- Méthodes importantes : `afficherStock()`, `chercherParNom(String)`, `vendreParNom(String, Joueur)`.
- Pour acheter : le joueur choisit exactement le nom de l'objet affiché; le marchand vérifie la monnaie et déplace l'objet dans l'inventaire du joueur.

4) Exploration et bestiaire
- `MonstreFactory.creeMonstreAleatoire(int zoneNiveau)` génère un monstre adapté à la zone.
- Lors d'une exploration (menu), le jeu crée un monstre aléatoire et déclenche le combat.

5) Combat (solo — tour par tour)
- Boucle de combat : tant que `joueur.estVivant()` et `!monstre.estMort()` :
	- Le joueur choisit une action : attaquer, utiliser potion ou fuir.
	- Attaque : `Joueur.attaquer(Monstre)` calcule des dégâts simples = max(0, attaqueJoueur - defenseMonstre) et appelle `Monstre.recevoirDegats(int)`.
	- Potion : prend la première potion de l'inventaire (`consommerPremierePotion`) et ajoute les PV.
	- Fuite : chance 50% de réussir; si échec, le combat continue.
	- Si le monstre est encore vivant, il attaque : `Monstre.attaquer(Joueur)` applique des dégâts au joueur.
- Après la victoire : le joueur reçoit `getRecompenseCoins()` intcoins et un drop aléatoire possible (potion/materia).
- XP fixe (par exemple `+5`) et montée de niveau automatique si seuil atteint.

6) Équipement et materia
- Pour équiper un `Equipement`, on appelle `Joueur.equiper(Equipement)` qui applique les bonus immédiatement aux stats du joueur.
- `Materia.utiliser` appliquera `appliquer(Equipement)` si la cible est un équipement (amélioration permanente dans ce modèle).

7) PvP (concept / état actuel)
- L'objectif du TP est d'ajouter un mode multijoueur PvP : deux joueurs connectés à un `Serveur` s'affrontent en combat tour-par-tour.
- `Serveur.java` et `Client.java` sont présents comme squelettes. Ils devront être complétés pour utiliser des sockets (ou RMI) :
	- Serveur : accepter deux connexions, synchroniser tours, relayer actions.
	- Client : envoyer actions (attaquer, utiliser potion, équiper) et recevoir états mis à jour.
- PvP est pour l'instant notifié dans le menu, mais non implémenté.

Interaction détaillée (scénarios pas-à-pas)
---------------------------------------
1) Commerce (achat d'un objet)
- Le joueur choisit `1) Commercer` dans le menu.
- Le marchand affiche tout son `stock` avec le nom et le prix.
- Le joueur entre le nom exact de l'objet affiché.
- `Marchand.vendreParNom(nom, joueur)` est appelé :
	- vérifie existence, vérifie `Inventaire.despenderMonnaie(prix)`;
	- enlève l'objet du stock et appelle `Inventaire.ajouter(objet)`.
	- renvoie succès/échec (manque d'intcoins ou objet introuvable).

2) Équiper un objet
- Le menu propose d'équiper la première arme disponible (fonction simplifiée actuelle).
- `Joueur.equiper(Equipement)` applique `bonusAttaque` et `bonusDefense` aux caractéristiques du joueur.

3) Utiliser une potion
- Le joueur choisit `Utiliser une potion` ou l'action en combat.
- `Inventaire.consommerPremierePotion()` retire et renvoie la potion ; `Joueur.gagnerPV(soin)` applique le soin.

4) Utiliser une materia
- Le joueur applique la materia sur un équipement via `Materia.appliquer(Equipement)`.
- La materia augmente la valeur `bonusAttaque` / `bonusDefense` de l'équipement.

5) Combat PvE (déroulé)
- Début : affiche PV joueur / PV monstre.
- Joueur joue (action), puis monstre joue s'il est vivant.
- Fin : récompenses, loot, XP.

Exécution et exemple de session
--------------------------------
- Après compilation (`javac ...`), lancer `java -cp out com.polymorphia.game.Jeu`.
- Exemple d'actions typiques :
	- `1` pour commercer → tapez `Epée basique` pour acheter (si vous avez assez de monnaie).
	- `2` pour explorer → combat aléatoire contre un monstre.
	- Pendant le combat : `1` attaquer, `2` utiliser potion, `3` tenter de fuir.

Conseils pour le développement / extension
----------------------------------------
- Rendre le marchand capable de sérialiser/désérialiser son stock, ou charger depuis un fichier JSON/CSV.
- Remplacer les prints par un logger pour mieux tester.
- Implémenter PvP : utiliser `ServerSocket` / `Socket` pour la communication basique ; sérialiser les messages (JSON simple).
- Ajouter un `pom.xml` (Maven) ou `build.gradle` pour gérer la compilation et l'exécution plus proprement.
- Ajouter des tests unitaires (JUnit) pour la logique de combat (`Joueur.attacker`, `Monstre.recevoirDegats`, `Inventaire`).

Fichiers UML et rendu
---------------------
- Joignez vos diagrammes UML (photo/PDF) au dépôt racine. Nommez le fichier `UML.pdf` (ou `UML.png`).
- Ajoutez un fichier `CONTRIBUTORS.md` listant les noms du binôme et la répartition du travail.

Contact / Suite
----------------
- Si vous voulez que j'implémente immédiatement :
	- le PvP réseau (serveur + client en sockets), ou
	- un `pom.xml` et tests JUnit,
	dites-moi lequel et je l'ajoute.

Bonne continuation — si vous voulez, je complète `Serveur`/`Client` pour faire un vrai PvP local maintenant.

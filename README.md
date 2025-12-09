Polymorphia - Architecture du projet
===================================

Ce dépôt contient un squelette Java pour le projet de jeu "Polymorphia".
Les classes suivent le diagramme UML fourni (joueur, inventaire, objets, monstres, marchand, serveur, client, jeu).

Comment démarrer (Windows, `cmd.exe`):

1. Compiler les sources :

```cmd
javac -d out src\main\java\com\polymorphia\game\*.java
```

2. Lancer le runner principal :

```cmd
java -cp out com.polymorphia.game.Jeu
```

Les classes sont des squelettes destinés à fournir l'architecture et des points d'extension. N'hésitez pas à demander que j'implémente des comportements détaillés (combat, inventaire, réseau, etc.).

@echo off
REM Script de démarrage du CLIENT Polymorphia
REM À exécuter sur chaque PC joueur (2 clients max)

echo ╔═══════════════════════════════════════════════╗
echo ║   POLYMORPHIA - DEMARRAGE DU CLIENT          ║
echo ╚═══════════════════════════════════════════════╝
echo.
echo IMPORTANT: Assurez-vous que le serveur est démarré!
echo.

java -cp out com.polymorphia.game.ClientJeu

pause

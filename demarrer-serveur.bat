@echo off
REM Script de démarrage du SERVEUR Polymorphia
REM À exécuter sur le PC qui hébergera le serveur

echo ╔═══════════════════════════════════════════════╗
echo ║   POLYMORPHIA - DEMARRAGE DU SERVEUR         ║
echo ╚═══════════════════════════════════════════════╝
echo.

REM Afficher l'adresse IP locale
echo Votre adresse IP locale:
echo.
ipconfig | findstr /i "IPv4"
echo.
echo Les joueurs devront se connecter à cette adresse IP.
echo.

pause
echo.
echo Démarrage du serveur...
echo.

java -cp out com.polymorphia.game.ServeurJeu

pause

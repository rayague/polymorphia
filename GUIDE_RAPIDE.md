# 🎮 Guide Rapide - Jeu Multijoueur Réseau

## ⚡ Démarrage Rapide (3 minutes)

### 📋 Ce dont vous avez besoin:
- ✅ 2 ou 3 PC sur le **même réseau WiFi/Ethernet**
- ✅ Java installé sur chaque PC
- ✅ Le jeu compilé (`out/` doit exister)

---

## 🚀 Étapes Simples

### **PC SERVEUR (PC 1)**

1. **Double-cliquez** sur `demarrer-serveur.bat`
2. **Notez l'adresse IP** affichée (ex: `192.168.1.100`)
3. Attendez que 2 joueurs se connectent

```
🎮 SERVEUR POLYMORPHIA DÉMARRÉ
📡 Port: 5555
👥 En attente de 2 joueurs...
```

### **PC JOUEUR 1 (PC 2)**

1. **Double-cliquez** sur `demarrer-client.bat`
2. **Tapez l'IP du serveur** (ex: `192.168.1.100`)
3. **Entrez votre nom** (ex: `Javalt`)
4. Attendez le joueur 2

### **PC JOUEUR 2 (PC 3)**

1. **Double-cliquez** sur `demarrer-client.bat`
2. **Tapez l'IP du serveur** (ex: `192.168.1.100`)
3. **Entrez votre nom** (ex: `Ray`)
4. Le combat démarre automatiquement! ⚔️

---

## 🎯 Pendant le Combat

Quand c'est votre tour:
```
┌─────────────────────────────────────┐
│         C'EST VOTRE TOUR!           │
└─────────────────────────────────────┘
💚 Vous (Javalt): 50 PV
💔 Ray: 44 PV

Actions disponibles:
  1) Attaquer
  2) Utiliser une potion (+20 PV)
  3) Abandonner
> 
```

**Tapez 1, 2 ou 3 puis Entrée**

---

## ❌ Problèmes Courants

### "Connection refused"
➡️ Le serveur n'est pas démarré  
✅ **Solution:** Démarrez d'abord le serveur

### "Timeout"
➡️ Mauvaise adresse IP ou pare-feu bloqué  
✅ **Solution:** Vérifiez l'IP et désactivez temporairement le pare-feu

### "Déconnexion"
➡️ Réseau instable  
✅ **Solution:** Utilisez un câble Ethernet plutôt que WiFi

---

## 🏆 C'est Parti!

Le premier à 0 PV perd! Bonne chance! 🎮

---

**Pour plus de détails techniques, voir:** [ARCHITECTURE_RESEAU.md](ARCHITECTURE_RESEAU.md)

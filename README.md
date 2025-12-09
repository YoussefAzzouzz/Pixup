# Pixup

Pixup est une plateforme de commerce électronique complète dédiée à la vente de jeux vidéo. Ce projet full-stack combine la robustesse de Spring Boot pour le backend et la modernité d'Angular pour le frontend.

## 🚀 Fonctionnalités

*   **Catalogue de Jeux** : Parcourez une liste de jeux vidéo avec leurs détails, prix et images.
*   **Authentification Utilisateur** : Système d'inscription et de connexion personnalisé.
*   **Panier d'Achat** : Ajoutez des jeux à votre panier, visualisez le total, et conservez votre sélection même après rafraîchissement (persistance locale).
*   **Profil Utilisateur** :
    *   Gestion du profil (Bio, photo de profil).
    *   Upload d'image pour l'avatar.
    *   Historique des commandes passées.
*   **Commande** : Processus de checkout simple pour valider le panier.

## 🛠 Technologies Utilisées

### Backend
*   **Java 17**
*   **Spring Boot 3.3.0**
*   **Spring Data JPA** (Hibernate)
*   **MySQL** (Base de données)
*   **Spring Security** (Configuration de base pour les endpoints API)
*   **Maven** (Gestion de dépendances)

### Frontend
*   **Angular 18** (Standalone components)
*   **TypeScript**
*   **RxJS**
*   **HTML5 / CSS3**

## 📋 Prérequis

Avant de commencer, assurez-vous d'avoir installé :
*   [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
*   [Node.js](https://nodejs.org/) (et npm)
*   [MySQL Server](https://dev.mysql.com/downloads/mysql/)

## ⚙️ Installation et Démarrage

### 1. Configuration de la Base de Données
Le backend est configuré pour se connecter à une base de données MySQL locale nommée `pixup_db` sur le port `3306` avec l'utilisateur `root` et sans mot de passe (configuration par défaut).

Si nécessaire, modifiez `backend/src/main/resources/application.properties` pour correspondre à votre configuration MySQL.

### 2. Démarrage du Backend (API)
Ouvrez un terminal dans le dossier `backend` :

```bash
cd backend
# Sur Windows
.\mvnw spring-boot:run
# Sur Mac/Linux
./mvnw spring-boot:run
```

Le serveur démarrera sur **http://localhost:8080**.

### 3. Démarrage du Frontend (Client)
Ouvrez un nouveau terminal dans le dossier `frontend` :

```bash
cd frontend
npm install  # Installe les dépendances (première fois uniquement)
npm start
```

L'application sera accessible sur **http://localhost:4200**.

## 📝 Utilisation

1.  Ouvrez votre navigateur sur `http://localhost:4200`.
2.  Inscrivez-vous ou connectez-vous (ex: utilisateur de test créé lors du développement).
3.  Ajoutez des jeux comme "Cyberpunk 2077" ou "God of War" à votre panier.
4.  Validez votre commande via le bouton "Checkout".
5.  Consultez votre historique de commande sur la page de profil.

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request pour améliorer le projet.

## 📄 Licence

Ce projet est sous licence MIT.

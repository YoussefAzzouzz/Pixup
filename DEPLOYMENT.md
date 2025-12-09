# Déploiement du Projet Pixup

Ce projet est composé d'un frontend Angular et d'un backend Spring Boot. Ils doivent être déployés séparément.

## 1. Frontend (Netlify)

Le frontend peut être hébergé gratuitement sur Netlify.

### Configuration
Le fichier `netlify.toml` a déjà été créé à la racine du projet pour configurer le déploiement.

### Étapes
1.  Poussez votre code sur GitHub.
2.  Connectez-vous à [Netlify](https://www.netlify.com/).
3.  Cliquez sur "Add new site" > "Import an existing project".
4.  Sélectionnez GitHub et votre dépôt `pixup`.
5.  Netlify détectera automatiquement la configuration grâce au fichier `netlify.toml`.
    *   **Build command**: `npm install && npm run build`
    *   **Publish directory**: `dist/frontend/browser`
6.  Cliquez sur "Deploy".

## 2. Backend (Railway / Render)

Le backend (Java/Spring Boot) et la base de données (MySQL) ne peuvent pas être hébergés sur Netlify. Vous devez utiliser un autre service. **Railway** est recommandé pour sa simplicité.

### Étapes (Exemple avec Railway)
1.  Créez un compte sur [Railway.app](https://railway.app/).
2.  Créez un nouveau projet > "Deploy from GitHub repo".
3.  Sélectionnez votre repo `pixup`.
4.  Railway va essayer de construire le projet. Vous devrez peut-être configurer le dossier racine (`Root Directory`) sur `backend`.
5.  Ajoutez une base de données MySQL au projet Railway.
6.  Configurez les variables d'environnement dans Railway pour connecter le backend à la base de données (URL, USERNAME, PASSWORD).

## 3. Connexion Frontend <-> Backend

Une fois le backend déployé, il aura une URL publique (ex: `https://pixup-backend.up.railway.app`).

1.  Ouvrez le fichier `frontend/src/environments/environment.development.ts` (qui sert de prod dans notre config actuelle, ou créez un `environment.prod.ts`).
2.  Remplacez `https://YOUR-BACKEND-URL.com/api` par l'URL réelle de votre backend déployé.
3.  Committez et poussez ce changement. Netlify redéploiera automatiquement le frontend avec la nouvelle configuration.

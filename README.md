# Marché Energie

## Présentation

Dépôt contenant une API de gestion d'un marché de l'énergie.

## Installation en local

- Exécuter la commande `docker compose up -d db` pour lancer le conteneur de la base de données PostgreSQL.
- Au lancement de l'application, les scripts de migration Liquibase génèrent les schémas des tables et insèrent un jeu de données de test.
- Le Swagger OpenAPI de l'API est disponible à l'URL suivante: http://localhost:8080/swagger-ui/index.html
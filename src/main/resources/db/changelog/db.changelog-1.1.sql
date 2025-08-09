--liquibase formatted sql

-- ChangeSet MWE:1.1 Création de la table parc_producteur
CREATE TABLE parc_producteur (
    id UUID PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL
);

--liquibase formatted sql

-- ChangeSet MWE:1.1 Création des tables parc_producteur, parc_producteur_production, offre et offre_allocation_production
CREATE TABLE parc_producteur (
    id                      UUID PRIMARY KEY,
    nom                     VARCHAR(255) NOT NULL,
    type                    VARCHAR(255) NOT NULL,
    date_heure_creation     TIMESTAMP    NOT NULL DEFAULT now(),
    date_heure_modification TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE parc_producteur_production (
    parc_producteur_id    UUID         NOT NULL,
    tranche_horaire       VARCHAR(255) NOT NULL,
    production            INTEGER      NOT NULL,
    PRIMARY KEY (parc_producteur_id, tranche_horaire),
    FOREIGN KEY (parc_producteur_id) REFERENCES parc_producteur(id) ON DELETE CASCADE
);

CREATE TABLE offre (
    id                      UUID PRIMARY KEY,
    marche                  VARCHAR(255) NOT NULL,
    date_heure_creation     TIMESTAMP    NOT NULL DEFAULT now(),
    date_heure_modification TIMESTAMP    NOT NULL DEFAULT now()
);
CREATE UNIQUE INDEX offre_marche_idx ON offre (marche);

CREATE TABLE offre_bloc_horaire (
    offre_id                UUID         NOT NULL,
    tranche_horaire         VARCHAR(255) NOT NULL,
    prix_plancher           NUMERIC      NOT NULL,
    PRIMARY KEY (offre_id, tranche_horaire),
    FOREIGN KEY (offre_id) REFERENCES offre(id) ON DELETE CASCADE
);

CREATE TABLE offre_bloc_horaire_allocation (
    offre_id                UUID          NOT NULL,
    parc_producteur_id      UUID          NOT NULL,
    tranche_horaire         VARCHAR(255)  NOT NULL,
    production              INTEGER       NOT NULL,
    PRIMARY KEY (offre_id, tranche_horaire, parc_producteur_id),
    FOREIGN KEY (offre_id) REFERENCES offre(id) ON DELETE CASCADE,
    FOREIGN KEY (parc_producteur_id) REFERENCES parc_producteur(id)
);

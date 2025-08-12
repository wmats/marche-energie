--liquibase formatted sql

-- ChangeSet MWE:1.2 context:"local" Insertion de données de dev

-- Parcs producteurs
INSERT INTO parc_producteur VALUES ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'Parc solaire #1', 'SOLAIRE');
INSERT INTO parc_producteur VALUES ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'Parc hydraulique #1', 'HYDRAULIQUE');
INSERT INTO parc_producteur VALUES ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'Parc éloien #1', 'EOLIEN');

-- Production des parcs producteurs par tranche horaire
INSERT INTO parc_producteur_production
VALUES
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_1', 0),
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_2', 100),
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_3', 200),
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_4', 200),
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_5', 200),
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_6', 200),
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_7', 100),
    ('b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_8', 0);
INSERT INTO parc_producteur_production
VALUES
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_1', 100),
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_2', 100),
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_3', 100),
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_4', 100),
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_5', 100),
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_6', 100),
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_7', 100),
    ('2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_8', 100);
INSERT INTO parc_producteur_production
VALUES
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_1', 10),
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_2', 80),
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_3', 70),
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_4', 70),
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_5', 60),
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_6', 50),
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_7', 20),
    ('a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_8', 10);

-- Offre
INSERT INTO offre VALUES ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'RESERVE_PRIMAIRE');

-- Blocs horaires des offres
INSERT INTO offre_bloc_horaire
VALUES
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_1', 1000),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_2', 1200),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_3', 1800),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_4', 800),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_5', 2000),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_6', 970),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_7', 670),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'TRANCHE_8', 500);

-- Allocations en production sur les blocs horaires des offres
INSERT INTO offre_bloc_horaire_allocation
VALUES
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_1', 8),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_2', 10),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, '2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_3', 30),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_4', 20),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'a974cfb4-10f4-4973-99d7-529a93b70e95'::uuid, 'TRANCHE_5', 25),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, '2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_6', 15),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, 'b4560cfc-3b1e-45b6-94f8-086004696d60'::uuid, 'TRANCHE_7', 40),
    ('12c8bbe5-3fe4-4873-b866-ff6d2216a413'::uuid, '2a22fadc-1a09-4634-b3aa-9b12b8b4c772'::uuid, 'TRANCHE_8', 50);
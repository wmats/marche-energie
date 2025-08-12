package fr.test.marche_energie.fixtures.parc_producteur;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ParcProducteurTestBuilder {
  private UUID id = UUID.fromString("e1affe8f-07f8-41b0-819f-cb86fcb535be");
  private String nom = "Parc producteur #1";
  private TypeParcProducteur type = TypeParcProducteur.SOLAIRE;
  private Map<TrancheHoraire, Integer> production =
      Map.of(
          TrancheHoraire.TRANCHE_1, 0,
          TrancheHoraire.TRANCHE_2, 1,
          TrancheHoraire.TRANCHE_3, 2,
          TrancheHoraire.TRANCHE_4, 2,
          TrancheHoraire.TRANCHE_5, 2,
          TrancheHoraire.TRANCHE_6, 2,
          TrancheHoraire.TRANCHE_7, 1,
          TrancheHoraire.TRANCHE_8, 0);
  private LocalDateTime dateHeureCreation = LocalDateTime.of(2025, 8, 9, 12, 30);
  private LocalDateTime dateHeureModification = LocalDateTime.of(2025, 8, 9, 12, 30);

  public static ParcProducteurTestBuilder unParcProducteur() {
    return new ParcProducteurTestBuilder();
  }

  public ParcProducteur build() {
    return ParcProducteur.builder()
        .id(id)
        .nom(nom)
        .type(type)
        .productionParTrancheHoraire(production)
        .dateHeureCreation(dateHeureCreation)
        .dateHeureModification(dateHeureModification)
        .build();
  }

  public ParcProducteurTestBuilder avecId(UUID id) {
    this.id = id;
    return this;
  }

  public ParcProducteurTestBuilder avecNom(String nom) {
    this.nom = nom;
    return this;
  }

  public ParcProducteurTestBuilder avecType(TypeParcProducteur type) {
    this.type = type;
    return this;
  }

  public ParcProducteurTestBuilder avecProductionParTrancheMap(
      Map<TrancheHoraire, Integer> production) {
    this.production = production;
    return this;
  }

  public ParcProducteurTestBuilder avecDateHeureCreation(LocalDateTime dateHeureCreation) {
    this.dateHeureCreation = dateHeureCreation;
    return this;
  }

  public ParcProducteurTestBuilder avecDateHeureModification(LocalDateTime dateHeureModification) {
    this.dateHeureModification = dateHeureModification;
    return this;
  }
}

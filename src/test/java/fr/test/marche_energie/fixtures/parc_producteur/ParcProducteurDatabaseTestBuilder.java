package fr.test.marche_energie.fixtures.parc_producteur;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabase;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ParcProducteurDatabaseTestBuilder {
  private UUID id = UUID.fromString("e1affe8f-07f8-41b0-819f-cb86fcb535be");
  private final TypeParcProducteur type = TypeParcProducteur.SOLAIRE;
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

  public static ParcProducteurDatabaseTestBuilder unParcProducteurDatabase() {
    return new ParcProducteurDatabaseTestBuilder();
  }

  public ParcProducteurDatabase build() {
    return ParcProducteurDatabase.builder()
        .id(id)
        .nom("Parc producteur #1")
        .type(type)
        .productionParTrancheHoraire(production)
        .dateHeureCreation(dateHeureCreation)
        .dateHeureModification(dateHeureModification)
        .build();
  }

  public ParcProducteurDatabaseTestBuilder avecId(UUID id) {
    this.id = id;
    return this;
  }

  public ParcProducteurDatabaseTestBuilder avecProductionParTrancheHoraire(
      Map<TrancheHoraire, Integer> production) {
    this.production = production;
    return this;
  }

  public ParcProducteurDatabaseTestBuilder avecDateHeureCreation(LocalDateTime dateHeureCreation) {
    this.dateHeureCreation = dateHeureCreation;
    return this;
  }

  public ParcProducteurDatabaseTestBuilder avecDateHeureModification(
      LocalDateTime dateHeureModification) {
    this.dateHeureModification = dateHeureModification;
    return this;
  }
}

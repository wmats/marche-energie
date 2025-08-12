package fr.test.marche_energie.fixtures.offre;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreAllocationDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabaseId;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OffreBlocHoraireDatabaseTestBuilder {
  private UUID id = UUID.fromString("a976ffd0-db12-4e11-a64c-8b06e7464de1");
  private BigDecimal prixPlancher = BigDecimal.valueOf(1000);

  public static OffreBlocHoraireDatabaseTestBuilder unOffreBlocHoraireDatabase() {
    return new OffreBlocHoraireDatabaseTestBuilder();
  }

  public OffreBlocHoraireDatabase build() {
    return OffreBlocHoraireDatabase.builder()
        .id(
            new OffreBlocHoraireDatabaseId(
                UUID.fromString("839e011f-fe0e-4241-b4d1-63a17e021833"), TrancheHoraire.TRANCHE_1))
        .prixPlancher(prixPlancher)
        .allocations(List.of(uneOffreAllocationProductionDatabase()))
        .build();
  }

  public static OffreAllocationDatabase uneOffreAllocationProductionDatabase() {
    return OffreAllocationDatabase.builder()
        .parcProducteur(unParcProducteurDatabase().build())
        .production(1)
        .build();
  }
}

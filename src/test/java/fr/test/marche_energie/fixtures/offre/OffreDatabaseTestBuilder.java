package fr.test.marche_energie.fixtures.offre;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.infrastructure.database.offre.OffreDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreAllocationDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabaseId;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OffreDatabaseTestBuilder {
  private final UUID id = UUID.fromString("839e011f-fe0e-4241-b4d1-63a17e021833");
  private final TypeMarche marche = TypeMarche.RESERVE_PRIMAIRE;
  private final OffreBlocHoraireDatabase offreBlocHoraireDatabase = uneOffreBlocHoraireDatabase();
  private Map<TrancheHoraire, OffreBlocHoraireDatabase> offreBlocsHorairesDatabase =
      Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraireDatabase);
  private final LocalDateTime dateHeureCreation = LocalDateTime.of(2025, 8, 11, 17, 24);
  private final LocalDateTime dateHeureModification = LocalDateTime.of(2025, 8, 11, 17, 24);

  public static OffreDatabaseTestBuilder uneOffreDatabase() {
    return new OffreDatabaseTestBuilder();
  }

  public OffreDatabaseTestBuilder avecBlocsHoraires(
      Map<TrancheHoraire, OffreBlocHoraireDatabase> offreBlocsHorairesDatabase) {
    this.offreBlocsHorairesDatabase = offreBlocsHorairesDatabase;
    return this;
  }

  public OffreDatabase build() {
    return OffreDatabase.builder()
        .id(id)
        .marche(marche)
        .blocsHoraires(offreBlocsHorairesDatabase)
        .dateHeureCreation(dateHeureCreation)
        .dateHeureModification(dateHeureModification)
        .build();
  }

  public OffreBlocHoraireDatabase uneOffreBlocHoraireDatabase() {
    return OffreBlocHoraireDatabase.builder()
        .id(new OffreBlocHoraireDatabaseId(id, TrancheHoraire.TRANCHE_1))
        .prixPlancher(BigDecimal.valueOf(1000))
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

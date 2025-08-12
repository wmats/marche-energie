package fr.test.marche_energie.fixtures.offre;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraireId;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OffreTestBuilder {
  private UUID id = UUID.fromString("839e011f-fe0e-4241-b4d1-63a17e021833");
  private TypeMarche marche = TypeMarche.RESERVE_PRIMAIRE;
  private OffreBlocHoraire offreBlocHoraire = uneOffreBlocHoraire();
  private Map<TrancheHoraire, OffreBlocHoraire> offreBlocsHoraires =
      Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraire);
  private LocalDateTime dateHeureCreation = LocalDateTime.of(2025, 8, 11, 17, 24);
  private LocalDateTime dateHeureModification = LocalDateTime.of(2025, 8, 11, 17, 24);

  public static OffreTestBuilder uneOffre() {
    return new OffreTestBuilder();
  }

  public Offre build() {
    return Offre.builder()
        .id(id)
        .marche(marche)
        .blocsHoraires(offreBlocsHoraires)
        .dateHeureCreation(dateHeureCreation)
        .dateHeureModification(dateHeureModification)
        .build();
  }

  public OffreTestBuilder avecId(UUID id) {
    this.id = id;
    return this;
  }

  public OffreTestBuilder avecBlocsHoraires(
      Map<TrancheHoraire, OffreBlocHoraire> offreBlocsHoraires) {
    this.offreBlocsHoraires = offreBlocsHoraires;
    return this;
  }

  public OffreTestBuilder avecDateHeureCreation(LocalDateTime dateHeureCreation) {
    this.dateHeureCreation = dateHeureCreation;
    return this;
  }

  public OffreTestBuilder avecDateHeureModification(LocalDateTime dateHeureModification) {
    this.dateHeureModification = dateHeureModification;
    return this;
  }

  public OffreBlocHoraire uneOffreBlocHoraire() {
    return OffreBlocHoraire.builder()
        .id(new OffreBlocHoraireId(id, TrancheHoraire.TRANCHE_1))
        .allocations(List.of(uneOffreAllocation()))
        .prixPlancher(BigDecimal.valueOf(1000))
        .build();
  }

  public OffreAllocation uneOffreAllocation() {
    return OffreAllocation.builder()
        .parcProducteur(unParcProducteur().build())
        .production(1)
        .build();
  }
}

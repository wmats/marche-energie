package fr.test.marche_energie.fixtures.offre;

import fr.test.marche_energie.application.rest.offre.entities.OffreAllocationInputApi;
import fr.test.marche_energie.application.rest.offre.entities.OffreBlocHoraireInputApi;
import fr.test.marche_energie.application.rest.offre.entities.OffreInputApi;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OffreInputApiTestBuilder {
  private UUID parcProducteurId = UUID.fromString("e1affe8f-07f8-41b0-819f-cb86fcb535be");
  private TypeMarche marche = TypeMarche.RESERVE_PRIMAIRE;
  private OffreBlocHoraireInputApi offreBlocHoraire = uneOffreBlocHoraireInputApi();
  private Map<TrancheHoraire, OffreBlocHoraireInputApi> offreBlocsHoraires =
      Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraire);

  public static OffreInputApiTestBuilder uneOffreInputApi() {
    return new OffreInputApiTestBuilder();
  }

  public OffreInputApi build() {
    return OffreInputApi.builder().marche(marche.name()).blocsHoraires(offreBlocsHoraires).build();
  }

  private OffreBlocHoraireInputApi uneOffreBlocHoraireInputApi() {
    return OffreBlocHoraireInputApi.builder()
        .prixPlancher(BigDecimal.valueOf(1000))
        .allocations(List.of(uneOffreAllocationInputApi()))
        .build();
  }

  private OffreAllocationInputApi uneOffreAllocationInputApi() {
    return OffreAllocationInputApi.builder()
        .parcProducteurId(parcProducteurId)
        .production(3)
        .build();
  }
}

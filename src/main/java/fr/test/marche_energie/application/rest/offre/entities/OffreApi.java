package fr.test.marche_energie.application.rest.offre.entities;

import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurLegerApi;
import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OffreApi {

  @Schema(description = "L'ID de l'offre", example = "839e011f-fe0e-4241-b4d1-63a17e021833")
  private UUID id;

  @Schema(description = "Le type de marché où l'offre est placée", example = "RESERVE_PRIMAIRE")
  private TypeMarche marche;

  private Map<TrancheHoraire, OffreBlocHoraireApi> blocsHoraires;

  public OffreApi(Offre offre) {
    this.id = offre.getId();
    this.marche = offre.getMarche();
    this.blocsHoraires =
        offre.getBlocsHoraires().entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, entry -> new OffreBlocHoraireApi(entry.getValue())));
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OffreBlocHoraireApi {

    @Schema(description = "Prix plancher de l'offre sur ce bloc horaire", example = "1000")
    private BigDecimal prixPlancher;

    private List<OffreAllocationApi> allocations;

    public OffreBlocHoraireApi(OffreBlocHoraire offreBlocHoraire) {
      this.prixPlancher = offreBlocHoraire.getPrixPlancher();
      this.allocations =
          offreBlocHoraire.getAllocations().stream().map(OffreAllocationApi::new).toList();
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OffreAllocationApi {

    @Schema(description = "La production allouée du parc en MW", example = "2")
    private Integer production;

    private ParcProducteurLegerApi parcProducteur;

    public OffreAllocationApi(OffreAllocation offreAllocation) {
      this.production = offreAllocation.getProduction();
      this.parcProducteur = new ParcProducteurLegerApi(offreAllocation.getParcProducteur());
    }
  }
}

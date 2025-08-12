package fr.test.marche_energie.application.rest.parc_producteur.entities;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParcProducteurApi extends ParcProducteurLegerApi {

  @Schema(
      description = "La production du parc par tranche horaire",
      example =
          """
                    {
                        "TRANCHE_1": 0,
                        "TRANCHE_2": 1,
                        "TRANCHE_3": 2,
                        "TRANCHE_4": 2,
                        "TRANCHE_5": 2,
                        "TRANCHE_6": 2,
                        "TRANCHE_7": 1,
                        "TRANCHE_8": 0
                    }""")
  private Map<TrancheHoraire, Integer> productionParTrancheHoraire;

  public ParcProducteurApi(ParcProducteur parcProducteur) {
    super(parcProducteur);
    this.productionParTrancheHoraire = parcProducteur.getProductionParTrancheHoraire();
  }
}

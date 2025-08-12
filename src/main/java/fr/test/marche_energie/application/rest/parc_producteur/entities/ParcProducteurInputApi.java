package fr.test.marche_energie.application.rest.parc_producteur.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.test.marche_energie.application.rest.DeserialiseurTrancheHoraire;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParcProducteurInputApi {

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Nom du parc producteur",
      example = "Parc solaire #1")
  @NotBlank(message = "Le nom est obligatoire")
  private String nom;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Type de parc producteur",
      example = "SOLAIRE")
  @NotBlank(message = "Le type est obligatoire")
  private String type;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "La production disponible du parc par tranche horaire en MW",
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
  @NotEmpty(message = "La production disponible du parc par tranche horaire est obligatoire")
  @JsonDeserialize(keyUsing = DeserialiseurTrancheHoraire.class)
  private Map<TrancheHoraire, Integer> productionParTrancheHoraire;
}

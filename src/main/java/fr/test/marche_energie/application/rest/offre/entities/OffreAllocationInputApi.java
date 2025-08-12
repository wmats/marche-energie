package fr.test.marche_energie.application.rest.offre.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OffreAllocationInputApi {

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Id du parc producteur à associer à l'allocation",
      example = "b2bc1fa1-fd04-41ba-bddd-03755ea8b4b5")
  @NotNull(message = "L'id du parc producteur doit être renseigné")
  private UUID parcProducteurId;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Production du parc en MW à allouer sur ce bloc horaire",
      example = "3")
  @Positive
  private Integer production;
}

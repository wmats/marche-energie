package fr.test.marche_energie.application.rest.offre.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OffreBlocHoraireInputApi {

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Prix plancher autorisé pour le bloc horaire d'une offre",
      example = "1000")
  @Positive(message = "Le prix plancher doit être supérieur à 0")
  private BigDecimal prixPlancher;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Les allocations de production d'énergie par parc")
  @NotEmpty(message = "La liste des allocations ne peut pas être vide")
  @Valid
  private List<OffreAllocationInputApi> allocations;
}

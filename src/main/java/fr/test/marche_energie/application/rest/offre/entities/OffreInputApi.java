package fr.test.marche_energie.application.rest.offre.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.test.marche_energie.application.rest.DeserialiseurTrancheHoraire;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
public class OffreInputApi {

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Type du marché",
      example = "RESERVE_PRIMAIRE")
  @NotBlank(message = "Le type du marché est obligatoire")
  private String marche;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Les blocs horaires de l'offre ventilés par tranche horaire")
  @NotEmpty(message = "La liste des blocs horaires de l'offre ne peut pas être vide")
  @JsonDeserialize(keyUsing = DeserialiseurTrancheHoraire.class)
  @Valid
  private Map<TrancheHoraire, OffreBlocHoraireInputApi> blocsHoraires;
}

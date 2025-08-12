package fr.test.marche_energie.application.rest.parc_producteur.entities;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParcProducteurLegerApi {

  @Schema(description = "L'ID du parc producteur", example = "e1affe8f-07f8-41b0-819f-cb86fcb535be")
  protected UUID id;

  @Schema(description = "Le nom du parc producteur", example = "Parc solaire #1")
  protected String nom;

  @Schema(description = "Le type de source d'énergie du parc", example = "SOLAIRE")
  protected TypeParcProducteur type;

  public ParcProducteurLegerApi(ParcProducteur parcProducteur) {
    this.id = parcProducteur.getId();
    this.nom = parcProducteur.getNom();
    this.type = parcProducteur.getType();
  }
}

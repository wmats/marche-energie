package fr.test.marche_energie.application.rest.parc_producteur;

import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParcProducteurApiMapper {

  public static ParcProducteur toParcProducteur(ParcProducteurInputApi parcProducteurInputApi) {
    return ParcProducteur.builder()
        .nom(parcProducteurInputApi.getNom())
        .type(TypeParcProducteur.valueOf(parcProducteurInputApi.getType()))
        .productionParTrancheHoraire(parcProducteurInputApi.getProductionParTrancheHoraire())
        .build();
  }
}

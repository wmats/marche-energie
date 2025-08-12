package fr.test.marche_energie.application.rest.offre.mapper;

import fr.test.marche_energie.application.rest.offre.entities.OffreAllocationInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OffreAllocationInputApiMapper {

  public static OffreAllocation toOffreAllocation(OffreAllocationInputApi offreAllocationInputApi) {
    return OffreAllocation.builder()
        .parcProducteur(
            ParcProducteur.builder().id(offreAllocationInputApi.getParcProducteurId()).build())
        .production(offreAllocationInputApi.getProduction())
        .build();
  }
}

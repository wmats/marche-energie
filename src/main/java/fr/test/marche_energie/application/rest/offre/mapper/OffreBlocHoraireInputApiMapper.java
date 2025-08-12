package fr.test.marche_energie.application.rest.offre.mapper;

import fr.test.marche_energie.application.rest.offre.entities.OffreBlocHoraireInputApi;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OffreBlocHoraireInputApiMapper {

  public static OffreBlocHoraire toOffreBlocHoraire(OffreBlocHoraireInputApi blocHoraireInputApi) {
    return OffreBlocHoraire.builder()
        .prixPlancher(blocHoraireInputApi.getPrixPlancher())
        .allocations(
            blocHoraireInputApi.getAllocations().stream()
                .map(OffreAllocationInputApiMapper::toOffreAllocation)
                .toList())
        .build();
  }
}

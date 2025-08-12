package fr.test.marche_energie.infrastructure.database.offre.mapper;

import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraireId;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabaseId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OffreBlocHoraireDatabaseMapper {

  public static OffreBlocHoraireDatabase toOffreBlocHoraireDatabase(
      OffreBlocHoraire offreBlocHoraire) {
    return OffreBlocHoraireDatabase.builder()
        .id(
            new OffreBlocHoraireDatabaseId(
                offreBlocHoraire.getId().getOffreId(),
                offreBlocHoraire.getId().getTrancheHoraire()))
        .prixPlancher(offreBlocHoraire.getPrixPlancher())
        .allocations(
            offreBlocHoraire.getAllocations().stream()
                .map(OffreAllocationDatabaseMapper::toOffreAllocationDatabase)
                .toList())
        .build();
  }

  public static OffreBlocHoraire toOffreBlocHoraire(
      OffreBlocHoraireDatabase offreBlocHoraireDatabase) {
    return OffreBlocHoraire.builder()
        .id(
            new OffreBlocHoraireId(
                offreBlocHoraireDatabase.getId().getOffreDatabaseId(),
                offreBlocHoraireDatabase.getId().getTrancheHoraire()))
        .prixPlancher(offreBlocHoraireDatabase.getPrixPlancher())
        .allocations(
            offreBlocHoraireDatabase.getAllocations().stream()
                .map(OffreAllocationDatabaseMapper::toOffreAllocation)
                .toList())
        .build();
  }
}

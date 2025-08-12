package fr.test.marche_energie.infrastructure.database.offre.mapper;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.infrastructure.database.offre.OffreDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OffreDatabaseMapper {

  public static OffreDatabase toOffreDatabase(Offre offre) {
    OffreDatabase offreDatabase =
        OffreDatabase.builder()
            .id(offre.getId())
            .marche(offre.getMarche())
            .blocsHoraires(toOffreBlocsHorairesDatabaseMap(offre.getBlocsHoraires()))
            .dateHeureCreation(offre.getDateHeureCreation())
            .dateHeureModification(offre.getDateHeureModification())
            .build();

    Map<TrancheHoraire, OffreBlocHoraireDatabase> offreBlocHoraireDatabaseMap =
        toOffreBlocsHorairesDatabaseMap(offre.getBlocsHoraires());
    offreDatabase.ajouterBlocsHoraires(offreBlocHoraireDatabaseMap);

    return offreDatabase;
  }

  public static Offre toOffre(OffreDatabase offreDatabase) {
    return Offre.builder()
        .id(offreDatabase.getId())
        .marche(offreDatabase.getMarche())
        .blocsHoraires(toOffreBlocsHorairesMap(offreDatabase.getBlocsHoraires()))
        .dateHeureCreation(offreDatabase.getDateHeureCreation())
        .dateHeureModification(offreDatabase.getDateHeureModification())
        .build();
  }

  private Map<TrancheHoraire, OffreBlocHoraireDatabase> toOffreBlocsHorairesDatabaseMap(
      Map<TrancheHoraire, OffreBlocHoraire> offreBlocHoraireMap) {
    if (offreBlocHoraireMap.isEmpty()) {
      return Map.of();
    }
    return offreBlocHoraireMap.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry ->
                    OffreBlocHoraireDatabaseMapper.toOffreBlocHoraireDatabase(entry.getValue())));
  }

  private Map<TrancheHoraire, OffreBlocHoraire> toOffreBlocsHorairesMap(
      Map<TrancheHoraire, OffreBlocHoraireDatabase> offreBlocHoraireDatabaseMap) {
    return offreBlocHoraireDatabaseMap.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> OffreBlocHoraireDatabaseMapper.toOffreBlocHoraire(entry.getValue())));
  }
}

package fr.test.marche_energie.application.rest.offre.mapper;

import fr.test.marche_energie.application.rest.offre.entities.OffreInputApi;
import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OffreInputApiMapper {

  public static Offre toOffre(OffreInputApi offreInputApi) {
    return Offre.builder()
        .marche(TypeMarche.valueOf(offreInputApi.getMarche()))
        .blocsHoraires(
            offreInputApi.getBlocsHoraires().entrySet().stream()
                .collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        entry ->
                            OffreBlocHoraireInputApiMapper.toOffreBlocHoraire(entry.getValue()))))
        .build();
  }
}

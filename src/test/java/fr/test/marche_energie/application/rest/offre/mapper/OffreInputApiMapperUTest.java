package fr.test.marche_energie.application.rest.offre.mapper;

import static fr.test.marche_energie.application.rest.offre.mapper.OffreInputApiMapper.toOffre;
import static fr.test.marche_energie.fixtures.offre.OffreInputApiTestBuilder.uneOffreInputApi;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.application.rest.offre.entities.OffreInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OffreInputApiMapperUTest {

  @Test
  void toOffre_devrait_mapper_OffreInputApi_en_Offre() {
    // Given
    OffreInputApi offreInputApi = uneOffreInputApi().build();

    // When
    Offre offre = toOffre(offreInputApi);

    // Then
    assertThat(offre).isNotNull();
    OffreAllocation offreAllocationAttendue =
        OffreAllocation.builder()
            .parcProducteur(
                ParcProducteur.builder()
                    .id(UUID.fromString("e1affe8f-07f8-41b0-819f-cb86fcb535be"))
                    .build())
            .production(3)
            .build();
    OffreBlocHoraire offreBlocHoraire =
        OffreBlocHoraire.builder()
            .prixPlancher(BigDecimal.valueOf(1000))
            .allocations(List.of(offreAllocationAttendue))
            .build();
    Offre offreAttendue =
        Offre.builder()
            .marche(TypeMarche.RESERVE_PRIMAIRE)
            .blocsHoraires(Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraire))
            .build();
    assertThat(offre).usingRecursiveComparison().isEqualTo(offreAttendue);
  }
}

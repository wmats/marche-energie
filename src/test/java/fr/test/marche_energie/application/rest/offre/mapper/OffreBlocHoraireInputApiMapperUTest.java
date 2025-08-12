package fr.test.marche_energie.application.rest.offre.mapper;

import static fr.test.marche_energie.application.rest.offre.mapper.OffreBlocHoraireInputApiMapper.toOffreBlocHoraire;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.application.rest.offre.entities.OffreAllocationInputApi;
import fr.test.marche_energie.application.rest.offre.entities.OffreBlocHoraireInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OffreBlocHoraireInputApiMapperUTest {

  @Test
  void toOffreBlocHoraire_devrait_mapper_OffreBlocHoraireInputApi_en_OffreBlocHoraire() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    Integer production = 2;
    BigDecimal prixPlancher = BigDecimal.valueOf(1000);
    OffreAllocationInputApi offreAllocationInputApi =
        OffreAllocationInputApi.builder()
            .parcProducteurId(parcProducteurId)
            .production(production)
            .build();
    OffreBlocHoraireInputApi offreBlocHoraireInputApi =
        OffreBlocHoraireInputApi.builder()
            .prixPlancher(prixPlancher)
            .allocations(List.of(offreAllocationInputApi))
            .build();

    // When
    OffreBlocHoraire offreBlocHoraire = toOffreBlocHoraire(offreBlocHoraireInputApi);

    // Then
    assertThat(offreBlocHoraire).isNotNull();
    OffreBlocHoraire offreBlocHoraireAttendu =
        OffreBlocHoraire.builder()
            .prixPlancher(prixPlancher)
            .allocations(
                List.of(
                    OffreAllocation.builder()
                        .production(production)
                        .parcProducteur(ParcProducteur.builder().id(parcProducteurId).build())
                        .build()))
            .build();
    assertThat(offreBlocHoraire).usingRecursiveComparison().isEqualTo(offreBlocHoraireAttendu);
  }
}

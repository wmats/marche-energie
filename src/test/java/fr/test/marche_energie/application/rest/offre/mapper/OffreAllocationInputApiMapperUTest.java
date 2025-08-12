package fr.test.marche_energie.application.rest.offre.mapper;

import static fr.test.marche_energie.application.rest.offre.mapper.OffreAllocationInputApiMapper.toOffreAllocation;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.application.rest.offre.entities.OffreAllocationInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OffreAllocationInputApiMapperUTest {

  @Test
  void toOffreAllocation_devrait_mapper_OffreAllocationInputApi_en_OffreAllocation() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    Integer production = 2;
    OffreAllocationInputApi offreAllocationInputApi =
        OffreAllocationInputApi.builder()
            .parcProducteurId(parcProducteurId)
            .production(production)
            .build();

    // When
    OffreAllocation offreAllocation = toOffreAllocation(offreAllocationInputApi);

    // Then
    assertThat(offreAllocation).isNotNull();
    OffreAllocation offreAllocationAttendue =
        OffreAllocation.builder()
            .production(production)
            .parcProducteur(ParcProducteur.builder().id(parcProducteurId).build())
            .build();
    assertThat(offreAllocation).usingRecursiveComparison().isEqualTo(offreAllocationAttendue);
  }
}

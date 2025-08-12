package fr.test.marche_energie.application.rest.parc_producteur;

import static fr.test.marche_energie.application.rest.parc_producteur.ParcProducteurApiMapper.toParcProducteur;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurInputApiTestBuilder.unParcProducteurInputApi;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import org.junit.jupiter.api.Test;

public class ParcProducteurLegerApiMapperUTest {

  @Test
  void toParcProducteur_devrait_mapper_ParcProducteurInputApi_en_ParcProducteur() {
    // Given
    ParcProducteurInputApi parcProducteurInputApi = unParcProducteurInputApi().build();

    // When
    ParcProducteur parcProducteur = toParcProducteur(parcProducteurInputApi);

    // Then
    ParcProducteur parcProducteurAttendu =
        unParcProducteur()
            .avecId(null)
            .avecDateHeureCreation(null)
            .avecDateHeureModification(null)
            .build();
    assertThat(parcProducteur).usingRecursiveComparison().isEqualTo(parcProducteurAttendu);
  }
}

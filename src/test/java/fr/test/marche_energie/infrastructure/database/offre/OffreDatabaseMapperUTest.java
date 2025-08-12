package fr.test.marche_energie.infrastructure.database.offre;

import static fr.test.marche_energie.fixtures.offre.OffreDatabaseTestBuilder.uneOffreDatabase;
import static fr.test.marche_energie.fixtures.offre.OffreTestBuilder.uneOffre;
import static fr.test.marche_energie.infrastructure.database.offre.mapper.OffreDatabaseMapper.toOffre;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.infrastructure.database.offre.mapper.OffreDatabaseMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OffreDatabaseMapperUTest {

  @Test
  void toOffreDatabase_devrait_mapper_Offre_en_OffreDatabase() {
    // Given
    Offre offre = uneOffre().build();

    // When
    OffreDatabase offreDatabase = OffreDatabaseMapper.toOffreDatabase(offre);

    // Then
    OffreDatabase offreDatabaseAttendue = uneOffreDatabase().build();
    assertThat(offreDatabase)
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(OffreDatabase.class)
        .isEqualTo(offreDatabaseAttendue);
  }

  @Test
  void
      toOffreDatabase_devrait_renvoyer_une_map_vide_si_la_map_OffreBlocHoraire_originale_est_vide() {
    // Given
    Offre offre = uneOffre().avecBlocsHoraires(Map.of()).build();

    // When
    OffreDatabase offreDatabase = OffreDatabaseMapper.toOffreDatabase(offre);

    // Then
    OffreDatabase offreDatabaseAttendue = uneOffreDatabase().avecBlocsHoraires(Map.of()).build();
    assertThat(offreDatabase).usingRecursiveComparison().isEqualTo(offreDatabaseAttendue);
  }

  @Test
  void toOffre_devrait_mapper_OffreDatabase_en_Offre() {
    // Given
    OffreDatabase offreDatabase = uneOffreDatabase().build();

    // When
    Offre offre = toOffre(offreDatabase);

    // Then
    Offre offreAttendue = uneOffre().build();
    assertThat(offre).usingRecursiveComparison().isEqualTo(offreAttendue);
  }
}

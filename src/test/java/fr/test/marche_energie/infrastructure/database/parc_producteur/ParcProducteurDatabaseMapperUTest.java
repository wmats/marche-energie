package fr.test.marche_energie.infrastructure.database.parc_producteur;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabaseMapper.toParcProducteur;
import static fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabaseMapper.toParcProducteurDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import org.junit.jupiter.api.Test;

class ParcProducteurDatabaseMapperUTest {

  @Test
  void toParcProducteurDatabase_devrait_mapper_ParcProducteur_en_ParcProducteurDatabase() {
    // Given
    ParcProducteur parcProducteur = unParcProducteur().build();

    // When
    ParcProducteurDatabase parcProducteurDatabase = toParcProducteurDatabase(parcProducteur);

    // Then
    ParcProducteurDatabase parcProducteurDatabaseAttendu =
        ParcProducteurDatabase.builder()
            .id(parcProducteur.getId())
            .nom(parcProducteur.getNom())
            .type(parcProducteur.getType())
            .productionParTrancheHoraire(parcProducteur.getProductionParTrancheHoraire())
            .dateHeureCreation(parcProducteur.getDateHeureCreation())
            .dateHeureModification(parcProducteur.getDateHeureModification())
            .build();
    assertThat(parcProducteurDatabase)
        .usingRecursiveComparison()
        .isEqualTo(parcProducteurDatabaseAttendu);
  }

  @Test
  void toParcProducteur_devrait_mapper_ParcProducteurDatabase_en_ParcProducteur() {
    // Given
    ParcProducteurDatabase parcProducteurDatabase = unParcProducteurDatabase().build();

    // When
    ParcProducteur parcProducteur = toParcProducteur(parcProducteurDatabase);

    // Then
    ParcProducteur parcProducteurAttendu =
        ParcProducteur.builder()
            .id(parcProducteurDatabase.getId())
            .nom(parcProducteurDatabase.getNom())
            .type(parcProducteurDatabase.getType())
            .productionParTrancheHoraire(parcProducteurDatabase.getProductionParTrancheHoraire())
            .dateHeureCreation(parcProducteurDatabase.getDateHeureCreation())
            .dateHeureModification(parcProducteurDatabase.getDateHeureModification())
            .build();
    assertThat(parcProducteur).usingRecursiveComparison().isEqualTo(parcProducteurAttendu);
  }
}

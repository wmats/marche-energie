package fr.test.marche_energie.infrastructure.database.offre.mapper;

import static fr.test.marche_energie.infrastructure.database.offre.mapper.OffreAllocationDatabaseMapper.toOffreAllocation;
import static fr.test.marche_energie.infrastructure.database.offre.mapper.OffreAllocationDatabaseMapper.toOffreAllocationDatabase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreAllocationDatabase;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabase;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OffreAllocationDatabaseMapperUTest {

  @Test
  void toOffreAllocationDatabase_devrait_mapper_OffreAllocation_en_OffreAllocationDatabase() {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(2025, 8, 12, 11, 21);
    UUID parcProducteurId = UUID.randomUUID();
    Map<TrancheHoraire, Integer> productionParcMap = Map.of(TrancheHoraire.TRANCHE_1, 1);
    String nomParc = "Parc solaire #1";
    TypeParcProducteur typeParcProducteur = TypeParcProducteur.SOLAIRE;
    ParcProducteur parcProducteur =
        ParcProducteur.builder()
            .id(parcProducteurId)
            .nom(nomParc)
            .type(typeParcProducteur)
            .productionParTrancheHoraire(productionParcMap)
            .dateHeureCreation(horodatage)
            .dateHeureModification(horodatage)
            .build();
    OffreAllocation offreAllocation =
        OffreAllocation.builder().production(1).parcProducteur(parcProducteur).build();

    // When
    OffreAllocationDatabase offreAllocationDatabase = toOffreAllocationDatabase(offreAllocation);

    // Then
    assertThat(offreAllocationDatabase).isNotNull();
    OffreAllocationDatabase offreAllocationDatabaseAttendue =
        OffreAllocationDatabase.builder()
            .production(1)
            .parcProducteur(
                ParcProducteurDatabase.builder()
                    .id(parcProducteurId)
                    .nom(nomParc)
                    .type(typeParcProducteur)
                    .productionParTrancheHoraire(productionParcMap)
                    .dateHeureCreation(horodatage)
                    .dateHeureModification(horodatage)
                    .build())
            .build();
    assertThat(offreAllocationDatabase)
        .usingRecursiveComparison()
        .isEqualTo(offreAllocationDatabaseAttendue);
  }

  @Test
  void toOffreAllocation_devrait_mapper_OffreAllocationDatabase_en_OffreAllocation() {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(2025, 8, 12, 11, 21);
    UUID parcProducteurId = UUID.randomUUID();
    Map<TrancheHoraire, Integer> productionParcMap = Map.of(TrancheHoraire.TRANCHE_1, 1);
    String nomParc = "Parc solaire #1";
    TypeParcProducteur typeParcProducteur = TypeParcProducteur.SOLAIRE;
    OffreAllocationDatabase offreAllocationDatabase =
        OffreAllocationDatabase.builder()
            .production(1)
            .parcProducteur(
                ParcProducteurDatabase.builder()
                    .id(parcProducteurId)
                    .nom(nomParc)
                    .type(typeParcProducteur)
                    .productionParTrancheHoraire(productionParcMap)
                    .dateHeureCreation(horodatage)
                    .dateHeureModification(horodatage)
                    .build())
            .build();

    // When
    OffreAllocation offreAllocation = toOffreAllocation(offreAllocationDatabase);

    // Then
    assertThat(offreAllocation).isNotNull();
    ParcProducteur parcProducteurAttendu =
        ParcProducteur.builder()
            .id(parcProducteurId)
            .nom(nomParc)
            .type(typeParcProducteur)
            .productionParTrancheHoraire(productionParcMap)
            .dateHeureCreation(horodatage)
            .dateHeureModification(horodatage)
            .build();
    OffreAllocation offreAllocationAttendue =
        OffreAllocation.builder().production(1).parcProducteur(parcProducteurAttendu).build();
    assertThat(offreAllocation).usingRecursiveComparison().isEqualTo(offreAllocationAttendue);
  }
}

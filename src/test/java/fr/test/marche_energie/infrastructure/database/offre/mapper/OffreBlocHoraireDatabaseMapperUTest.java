package fr.test.marche_energie.infrastructure.database.offre.mapper;

import static fr.test.marche_energie.infrastructure.database.offre.mapper.OffreBlocHoraireDatabaseMapper.toOffreBlocHoraire;
import static fr.test.marche_energie.infrastructure.database.offre.mapper.OffreBlocHoraireDatabaseMapper.toOffreBlocHoraireDatabase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraireId;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreAllocationDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabaseId;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OffreBlocHoraireDatabaseMapperUTest {

  @Test
  void toOffreBlocHoraireDatabase_devrait_mapper_OffreBlocHoraire_en_OffreBlocHoraireDatabase() {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(2025, 8, 12, 11, 21);
    BigDecimal prixPlancher = BigDecimal.valueOf(1000);
    UUID offreId = UUID.randomUUID();
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
    OffreBlocHoraire offreBlocHoraire =
        OffreBlocHoraire.builder()
            .id(new OffreBlocHoraireId(offreId, TrancheHoraire.TRANCHE_1))
            .prixPlancher(prixPlancher)
            .allocations(List.of(offreAllocation))
            .build();

    // When
    OffreBlocHoraireDatabase offreBlocHoraireDatabase =
        toOffreBlocHoraireDatabase(offreBlocHoraire);

    // Then
    assertThat(offreBlocHoraireDatabase).isNotNull();
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
    OffreBlocHoraireDatabase offreBlocHoraireDatabaseAttendue =
        OffreBlocHoraireDatabase.builder()
            .id(new OffreBlocHoraireDatabaseId(offreId, TrancheHoraire.TRANCHE_1))
            .prixPlancher(prixPlancher)
            .allocations(List.of(offreAllocationDatabaseAttendue))
            .build();
    assertThat(offreBlocHoraireDatabase)
        .usingRecursiveComparison()
        .isEqualTo(offreBlocHoraireDatabaseAttendue);
  }

  @Test
  void toOffreBlocHoraire_devrait_mapper_OffreBlocHoraireDatabase_en_OffreBlocHoraire() {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(2025, 8, 12, 11, 21);
    BigDecimal prixPlancher = BigDecimal.valueOf(1000);
    UUID offreId = UUID.randomUUID();
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
    OffreBlocHoraireDatabase offreBlocHoraireDatabase =
        OffreBlocHoraireDatabase.builder()
            .id(new OffreBlocHoraireDatabaseId(offreId, TrancheHoraire.TRANCHE_1))
            .prixPlancher(prixPlancher)
            .allocations(List.of(offreAllocationDatabase))
            .build();

    // When
    OffreBlocHoraire offreBlocHoraire = toOffreBlocHoraire(offreBlocHoraireDatabase);

    // Then
    assertThat(offreBlocHoraire).isNotNull();
    ParcProducteur parcProducteurAttendu =
        ParcProducteur.builder()
            .id(parcProducteurId)
            .nom(nomParc)
            .type(typeParcProducteur)
            .productionParTrancheHoraire(productionParcMap)
            .dateHeureCreation(horodatage)
            .dateHeureModification(horodatage)
            .build();
    OffreAllocation offreAllocationAttendu =
        OffreAllocation.builder().production(1).parcProducteur(parcProducteurAttendu).build();
    OffreBlocHoraire offreBlocHoraireAttendu =
        OffreBlocHoraire.builder()
            .id(new OffreBlocHoraireId(offreId, TrancheHoraire.TRANCHE_1))
            .prixPlancher(prixPlancher)
            .allocations(List.of(offreAllocationAttendu))
            .build();
    assertThat(offreBlocHoraire).usingRecursiveComparison().isEqualTo(offreBlocHoraireAttendu);
  }
}

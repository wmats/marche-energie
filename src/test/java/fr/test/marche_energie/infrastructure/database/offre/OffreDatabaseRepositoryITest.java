package fr.test.marche_energie.infrastructure.database.offre;

import static fr.test.marche_energie.fixtures.offre.OffreBlocHoraireDatabaseTestBuilder.unOffreBlocHoraireDatabase;
import static fr.test.marche_energie.fixtures.offre.OffreDatabaseTestBuilder.uneOffreDatabase;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.infrastructure.database.RepositoryTest;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabase;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OffreDatabaseRepositoryITest extends RepositoryTest {

  @BeforeEach
  void setUp() {
    ParcProducteurDatabase parcProducteurDatabase = unParcProducteurDatabase().build();
    parcProducteurDatabaseRepository.save(parcProducteurDatabase);
  }

  @Test
  void
      calculerProductionTotaleParParcEtTrancheHoraire_devrait_renvoyer_toute_la_production_d_un_parc_sur_une_tranche_horaire() {
    // Given
    UUID parcProducteurId = UUID.fromString("e1affe8f-07f8-41b0-819f-cb86fcb535be");
    OffreBlocHoraireDatabase offreBlocHoraireDatabase = unOffreBlocHoraireDatabase().build();
    OffreDatabase offreDatabase = uneOffreDatabase().build();
    offreDatabase.ajouterBlocsHoraires(Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraireDatabase));

    offreDatabaseRepository.save(offreDatabase);

    // When
    Integer productionTotale =
        offreDatabaseRepository.calculerProductionTotaleParParcEtTrancheHoraire(
            parcProducteurId, TrancheHoraire.TRANCHE_1);

    // Then
    assertThat(productionTotale).isNotNull();
    assertThat(productionTotale).isEqualTo(1);
  }

  @Test
  void findAllByMarche_devrait_renvoyer_les_offres_placees_sur_le_marche_correspondant() {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    OffreBlocHoraireDatabase offreBlocHoraireDatabase = unOffreBlocHoraireDatabase().build();
    OffreDatabase offreDatabase = uneOffreDatabase().build();
    offreDatabase.ajouterBlocsHoraires(Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraireDatabase));

    offreDatabaseRepository.save(offreDatabase);

    // When
    List<OffreDatabase> offresDatabase = offreDatabaseRepository.findAllByMarche(typeMarche);

    // Then
    assertThat(offresDatabase.isEmpty()).isFalse();
    assertThat(offresDatabase.size()).isEqualTo(1);
    assertThat(offresDatabase.getFirst()).usingRecursiveComparison().isEqualTo(offreDatabase);
  }
}

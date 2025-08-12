package fr.test.marche_energie.infrastructure.database.parc_producteur;

import static fr.test.marche_energie.fixtures.offre.OffreBlocHoraireDatabaseTestBuilder.unOffreBlocHoraireDatabase;
import static fr.test.marche_energie.fixtures.offre.OffreDatabaseTestBuilder.uneOffreDatabase;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.infrastructure.database.RepositoryTest;
import fr.test.marche_energie.infrastructure.database.offre.OffreDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ParcProducteurDatabaseRepositoryITest extends RepositoryTest {

  @Test
  void findParcsProducteursByTypeMarche_devrait_renvoyer_les_parcs() {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    OffreBlocHoraireDatabase offreBlocHoraireDatabase = unOffreBlocHoraireDatabase().build();
    OffreDatabase offreDatabase = uneOffreDatabase().build();
    offreDatabase.ajouterBlocsHoraires(Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraireDatabase));

    ParcProducteurDatabase parcProducteurDatabase = unParcProducteurDatabase().build();
    parcProducteurDatabaseRepository.save(parcProducteurDatabase);

    offreDatabaseRepository.save(offreDatabase);

    // When
    List<ParcProducteurDatabase> parcProducteurDatabases =
        parcProducteurDatabaseRepository.findParcsProducteursByTypeMarche(typeMarche);

    // Then
    assertThat(parcProducteurDatabases.isEmpty()).isFalse();
    assertThat(parcProducteurDatabases.size()).isEqualTo(1);
    assertThat(parcProducteurDatabases.getFirst())
        .usingRecursiveComparison()
        .isEqualTo(parcProducteurDatabase);
  }
}

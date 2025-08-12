package fr.test.marche_energie.infrastructure.database;

import fr.test.marche_energie.infrastructure.database.offre.OffreDatabaseRepository;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class RepositoryTest {

  @Autowired protected ParcProducteurDatabaseRepository parcProducteurDatabaseRepository;
  @Autowired protected OffreDatabaseRepository offreDatabaseRepository;

  @BeforeEach
  void setUp() {
    offreDatabaseRepository.deleteAllInBatch();
    parcProducteurDatabaseRepository.deleteAllInBatch();
  }
}

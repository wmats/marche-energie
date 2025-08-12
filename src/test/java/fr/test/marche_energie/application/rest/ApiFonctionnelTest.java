package fr.test.marche_energie.application.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.test.marche_energie.infrastructure.database.offre.OffreDatabaseRepository;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class ApiFonctionnelTest {
  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;

  @Autowired protected ParcProducteurDatabaseRepository parcProducteurDatabaseRepository;
  @Autowired protected OffreDatabaseRepository offreDatabaseRepository;

  @BeforeEach
  void setUp() {
    supprimerToutesLesOffres();
    supprimerTousLesParcsProducteurs();
  }

  protected String convertirEnJson(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  private void supprimerTousLesParcsProducteurs() {
    parcProducteurDatabaseRepository.deleteAll();
  }

  private void supprimerToutesLesOffres() {
    offreDatabaseRepository.deleteAll();
  }
}

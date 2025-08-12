package fr.test.marche_energie.application.rest.offre;

import static fr.test.marche_energie.fixtures.offre.OffreBlocHoraireDatabaseTestBuilder.unOffreBlocHoraireDatabase;
import static fr.test.marche_energie.fixtures.offre.OffreDatabaseTestBuilder.uneOffreDatabase;
import static fr.test.marche_energie.fixtures.offre.OffreInputApiTestBuilder.uneOffreInputApi;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.test.marche_energie.application.rest.ApiFonctionnelTest;
import fr.test.marche_energie.application.rest.offre.entities.OffreInputApi;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.port.HorlogePort;
import fr.test.marche_energie.infrastructure.database.offre.OffreDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreAllocationDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;

public class OffreControllerFTest extends ApiFonctionnelTest {

  @MockitoBean private HorlogePort horlogePort;

  @Test
  void creerUneOffre_devrait_creer_l_offre_et_renvoyer_un_statut_201() throws Exception {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(2025, 8, 10, 13, 8);

    OffreInputApi offreInputApi = uneOffreInputApi().build();
    ParcProducteurDatabase parcProducteurDatabase =
        unParcProducteurDatabase()
            .avecProductionParTrancheHoraire(Map.of(TrancheHoraire.TRANCHE_1, 4))
            .build();
    parcProducteurDatabaseRepository.save(parcProducteurDatabase);

    given(horlogePort.recupererLocalDateTimeUTC()).willReturn(horodatage);

    // When - Then
    mockMvc
        .perform(
            post("/api/offre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertirEnJson(offreInputApi)))
        .andExpect(status().isCreated());

    OffreBlocHoraireDatabase offreBlocHoraireDatabaseAttendue =
        OffreBlocHoraireDatabase.builder()
            .prixPlancher(BigDecimal.valueOf(1000))
            .allocations(
                List.of(
                    OffreAllocationDatabase.builder()
                        .production(3)
                        .parcProducteur(parcProducteurDatabase)
                        .build()))
            .build();
    OffreDatabase offreDatabaseAttendue =
        OffreDatabase.builder()
            .marche(TypeMarche.RESERVE_PRIMAIRE)
            .blocsHoraires(Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraireDatabaseAttendue))
            .dateHeureCreation(horodatage)
            .dateHeureModification(horodatage)
            .build();
    OffreDatabase offreDatabaseCreee = offreDatabaseRepository.findAll().getFirst();
    assertThat(offreDatabaseCreee)
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(UUID.class)
        .ignoringFieldsOfTypes(OffreDatabase.class)
        .isEqualTo(offreDatabaseAttendue);
  }

  @Test
  void
      recupererLesOffresDUnMarche_devrait_renvoyer_la_liste_d_offres_du_marche_choisi_avec_un_statut_200()
          throws Exception {
    // Given
    ParcProducteurDatabase parcProducteurDatabase = unParcProducteurDatabase().build();
    parcProducteurDatabaseRepository.save(parcProducteurDatabase);

    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    OffreBlocHoraireDatabase offreBlocHoraireDatabase = unOffreBlocHoraireDatabase().build();
    OffreDatabase offreDatabase = uneOffreDatabase().build();
    offreDatabase.ajouterBlocsHoraires(Map.of(TrancheHoraire.TRANCHE_1, offreBlocHoraireDatabase));
    offreDatabaseRepository.save(offreDatabase);

    // When - Then
    String jsonAttendu =
        """

                      [
                        {
                          "id": "839e011f-fe0e-4241-b4d1-63a17e021833",
                          "marche": "RESERVE_PRIMAIRE",
                          "blocsHoraires": {
                            "TRANCHE_1": {
                              "prixPlancher": 1000,
                              "allocations": [
                                {
                                  "production": 1,
                                  "parcProducteur": {
                                    "id": "e1affe8f-07f8-41b0-819f-cb86fcb535be",
                                    "nom": "Parc producteur #1",
                                    "type": "SOLAIRE"
                                  }
                                }
                              ]
                            }
                          }
                        }
                      ]
                      """;

    mockMvc
        .perform(get("/api/offre/marche/" + typeMarche))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonAttendu, JsonCompareMode.STRICT));
  }
}

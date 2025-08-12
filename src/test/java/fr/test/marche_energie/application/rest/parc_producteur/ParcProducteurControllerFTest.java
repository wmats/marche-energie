package fr.test.marche_energie.application.rest.parc_producteur;

import static fr.test.marche_energie.fixtures.offre.OffreBlocHoraireDatabaseTestBuilder.unOffreBlocHoraireDatabase;
import static fr.test.marche_energie.fixtures.offre.OffreDatabaseTestBuilder.uneOffreDatabase;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurInputApiTestBuilder.unParcProducteurInputApi;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.test.marche_energie.application.rest.ApiFonctionnelTest;
import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurInputApi;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.port.HorlogePort;
import fr.test.marche_energie.infrastructure.database.offre.OffreDatabase;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabase;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;

public class ParcProducteurControllerFTest extends ApiFonctionnelTest {

  @MockitoBean private HorlogePort horlogePort;

  @Test
  void creerUnParcProducteur_devrait_creer_le_parc_et_renvoyer_un_statut_201() throws Exception {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(2025, 8, 10, 13, 8);
    ParcProducteurInputApi parcProducteurInputApi = unParcProducteurInputApi().build();
    given(horlogePort.recupererLocalDateTimeUTC()).willReturn(horodatage);

    // When - Then
    ParcProducteurDatabase parcProducteurDatabaseAttendu =
        unParcProducteurDatabase()
            .avecDateHeureCreation(horodatage)
            .avecDateHeureModification(horodatage)
            .build();
    mockMvc
        .perform(
            post("/api/parc-producteur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertirEnJson(parcProducteurInputApi)))
        .andExpect(status().isCreated());
    ParcProducteurDatabase parcProducteurDatabaseEnregistre =
        parcProducteurDatabaseRepository.findAll().getFirst();
    assertThat(parcProducteurDatabaseEnregistre).isNotNull();
    assertThat(parcProducteurDatabaseEnregistre)
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(UUID.class)
        .isEqualTo(parcProducteurDatabaseAttendu);
  }

  @Test
  void
      recupererParcsProducteursSurUnTypeMarche_devrait_renvoyer_la_liste_des_parcs_sur_le_marche_considere_avec_un_statut_200()
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
                        "id": "e1affe8f-07f8-41b0-819f-cb86fcb535be",
                        "nom": "Parc producteur #1",
                        "type": "SOLAIRE",
                        "productionParTrancheHoraire": {
                              "TRANCHE_1": 0,
                              "TRANCHE_2": 1,
                              "TRANCHE_3": 2,
                              "TRANCHE_4": 2,
                              "TRANCHE_5": 2,
                              "TRANCHE_6": 2,
                              "TRANCHE_7": 1,
                              "TRANCHE_8": 0
                        }
                      }
                    ]
                    """;
    mockMvc
        .perform(get("/api/parc-producteur/marche/" + typeMarche))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonAttendu, JsonCompareMode.STRICT));
  }
}

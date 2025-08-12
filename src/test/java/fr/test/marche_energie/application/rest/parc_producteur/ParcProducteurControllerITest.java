package fr.test.marche_energie.application.rest.parc_producteur;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurInputApiTestBuilder.unParcProducteurInputApi;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.test.marche_energie.application.rest.ConfigurationControllerITest;
import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.parc_producteur.EnregistrerParcProducteur;
import fr.test.marche_energie.domain.use_cases.parc_producteur.RecupererParcsParTypeMarche;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;

@WebMvcTest(controllers = ParcProducteurController.class)
public class ParcProducteurControllerITest extends ConfigurationControllerITest {

  @MockitoBean private EnregistrerParcProducteur enregistrerParcProducteur;
  @MockitoBean private RecupererParcsParTypeMarche recupererParcsParTypeMarche;

  @Test
  void creerUnParcProducteur_devrait_creer_le_parc_et_renvoyer_un_statut_201() throws Exception {
    // Given
    ParcProducteurInputApi parcProducteurInputApi = unParcProducteurInputApi().build();
    doNothing().when(enregistrerParcProducteur).executer(any(ParcProducteur.class));

    // When - Then
    mockMvc
        .perform(
            post("/api/parc-producteur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertirEnJson(parcProducteurInputApi)))
        .andExpect(status().isCreated());
  }

  @Test
  void creerUnParcProducteur_devrait_renvoyer_un_statut_400_si_le_nom_ou_le_type_est_absent()
      throws Exception {
    // Given
    ParcProducteurInputApi parcProducteurInputApi =
        unParcProducteurInputApi().avecNom(null).avecType("").build();
    doNothing().when(enregistrerParcProducteur).executer(any(ParcProducteur.class));

    // When - Then
    mockMvc
        .perform(
            post("/api/parc-producteur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertirEnJson(parcProducteurInputApi)))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void
      creerUnParcProducteur_devrait_renvoyer_un_statut_400_si_la_liste_des_productions_disponibles_par_tranche_horaire_est_vide()
          throws Exception {
    // Given
    ParcProducteurInputApi parcProducteurInputApi =
        unParcProducteurInputApi().avecProduction(Map.of()).build();
    doNothing().when(enregistrerParcProducteur).executer(any(ParcProducteur.class));

    // When - Then
    mockMvc
        .perform(
            post("/api/parc-producteur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertirEnJson(parcProducteurInputApi)))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void
      recupererParcsProducteursSurUnTypeMarche_devrait_renvoyer_la_liste_des_parcs_sur_le_marche_considere_avec_un_statut_200()
          throws Exception {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    given(recupererParcsParTypeMarche.executer(typeMarche))
        .willReturn(List.of(unParcProducteur().build()));

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

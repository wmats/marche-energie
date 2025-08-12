package fr.test.marche_energie.application.rest.offre;

import static fr.test.marche_energie.fixtures.offre.OffreInputApiTestBuilder.uneOffreInputApi;
import static fr.test.marche_energie.fixtures.offre.OffreTestBuilder.uneOffre;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.test.marche_energie.application.rest.ConfigurationControllerITest;
import fr.test.marche_energie.application.rest.offre.entities.OffreInputApi;
import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.offre.EnregistrerOffre;
import fr.test.marche_energie.domain.use_cases.offre.RecupererOffresDUnMarche;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;

@WebMvcTest(controllers = OffreController.class)
public class OffreControllerITest extends ConfigurationControllerITest {

  @MockitoBean private EnregistrerOffre enregistrerOffre;
  @MockitoBean private RecupererOffresDUnMarche recupererOffresDUnMarche;

  @Test
  void creerUneOffre_devrait_creer_l_offre_et_renvoyer_un_statut_201() throws Exception {
    // Given
    OffreInputApi offreInputApi = uneOffreInputApi().build();
    doNothing().when(enregistrerOffre).executer(any(Offre.class));

    // When - Then
    mockMvc
        .perform(
            post("/api/offre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertirEnJson(offreInputApi)))
        .andExpect(status().isCreated());
  }

  @Test
  void
      recupererLesOffresDUnMarche_devrait_renvoyer_la_liste_d_offres_du_marche_choisi_avec_un_statut_200()
          throws Exception {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    given(recupererOffresDUnMarche.executer(typeMarche)).willReturn(List.of(uneOffre().build()));

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

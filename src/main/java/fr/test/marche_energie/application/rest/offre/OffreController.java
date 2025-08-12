package fr.test.marche_energie.application.rest.offre;

import static fr.test.marche_energie.application.rest.offre.mapper.OffreInputApiMapper.toOffre;

import fr.test.marche_energie.application.rest.offre.entities.OffreApi;
import fr.test.marche_energie.application.rest.offre.entities.OffreInputApi;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.offre.EnregistrerOffre;
import fr.test.marche_energie.domain.use_cases.offre.RecupererOffresDUnMarche;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/offre")
@Tag(name = "Offre", description = "API pour la gestion des offres")
public class OffreController {

  private EnregistrerOffre enregistrerOffre;
  private RecupererOffresDUnMarche recupererOffresDUnMarche;

  @PostMapping
  @Operation(description = "Crée une offre")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "L'offre a bien été créée"),
        @ApiResponse(
            responseCode = "400",
            description = "Les champs obligatoires de l'offre doivent être renseignés"),
      })
  @ResponseStatus(HttpStatus.CREATED)
  public void creerUneOffre(@RequestBody @Valid OffreInputApi offreInputApi) {
    enregistrerOffre.executer(toOffre(offreInputApi));
  }

  @GetMapping("/marche/{typeMarche}")
  @Operation(description = "Récupère les offres placées sur un marché")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Les offres du marché choisi ont bien été récupérées")
      })
  @ResponseStatus(HttpStatus.OK)
  public List<OffreApi> recupererLesOffresDUnMarche(
      @Parameter(description = "Le type de marché", required = true) @PathVariable
          TypeMarche typeMarche) {
    return recupererOffresDUnMarche.executer(typeMarche).stream().map(OffreApi::new).toList();
  }
}

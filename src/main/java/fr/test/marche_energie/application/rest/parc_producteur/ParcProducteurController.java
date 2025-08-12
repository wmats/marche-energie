package fr.test.marche_energie.application.rest.parc_producteur;

import static fr.test.marche_energie.application.rest.parc_producteur.ParcProducteurApiMapper.toParcProducteur;

import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurApi;
import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurInputApi;
import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.parc_producteur.EnregistrerParcProducteur;
import fr.test.marche_energie.domain.use_cases.parc_producteur.RecupererParcsParTypeMarche;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/parc-producteur")
@Tag(name = "Parc Producteur", description = "API pour la gestion des parcs producteurs")
public class ParcProducteurController {

  private EnregistrerParcProducteur enregistrerParcProducteur;
  private RecupererParcsParTypeMarche recupererParcsParTypeMarche;

  @PostMapping
  @Operation(summary = "Crée un parc producteur")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Le parc producteur a bien été créé"),
        @ApiResponse(
            responseCode = "400",
            description = "Le nom et le type du parc producteur doivent être renseignés")
      })
  @ResponseStatus(HttpStatus.CREATED)
  public void creerUnParcProducteur(
      @RequestBody @Valid ParcProducteurInputApi parcProducteurInputApi) {
    enregistrerParcProducteur.executer(toParcProducteur(parcProducteurInputApi));
  }

  @GetMapping("/marche/{typeMarche}")
  @Operation(description = "Récupère les parcs producteurs sur un marché")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Les parcs producteurs du marché choisi ont bien été récupérées")
      })
  @ResponseStatus(HttpStatus.OK)
  public List<ParcProducteurApi> recupererParcsProducteursSurUnTypeMarche(
      @Parameter(description = "Le type de marché", required = true) @PathVariable
          TypeMarche typeMarche) {
    List<ParcProducteur> parcsProducteurs = recupererParcsParTypeMarche.executer(typeMarche);
    return parcsProducteurs.stream().map(ParcProducteurApi::new).toList();
  }
}

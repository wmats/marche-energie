package fr.test.marche_energie.domain.use_cases.offre.interactors;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.exceptions.ProductionMaximaleDepasseeException;
import fr.test.marche_energie.domain.use_cases.port.OffrePort;
import fr.test.marche_energie.domain.use_cases.port.ParcProducteurPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VerifierEtConstruireOffreAllocation {

  private ParcProducteurPort parcProducteurPort;

  private OffrePort offrePort;

  public OffreAllocation executer(OffreAllocation offreAllocation, TrancheHoraire trancheHoraire) {
    ParcProducteur parcProducteur =
        parcProducteurPort.recupererParcProducteur(offreAllocation.getParcProducteur().getId());
    offreAllocation.setParcProducteur(parcProducteur);

    verifierSiLaProductionDuParcEstSuffisante(offreAllocation, trancheHoraire);

    return offreAllocation;
  }

  private void verifierSiLaProductionDuParcEstSuffisante(
      OffreAllocation offreAllocation, TrancheHoraire trancheHoraire) {
    Integer productionDejaAllouee =
        offrePort.calculerProductionTotaleDUnParcSurUneTrancheHoraire(
            offreAllocation.getParcProducteur().getId(), trancheHoraire);
    Integer productionMaximaleSurLaTrancheHoraire =
        offreAllocation
            .getParcProducteur()
            .getProductionParTrancheHoraire()
            .getOrDefault(trancheHoraire, 0);
    Integer productionSupplementaireAAllouer = offreAllocation.getProduction();

    if (productionMaximaleSurLaTrancheHoraire
            - productionDejaAllouee
            - productionSupplementaireAAllouer
        < 0) {
      throw new ProductionMaximaleDepasseeException(
          offreAllocation.getParcProducteur().getId(), trancheHoraire);
    }
    ;
  }
}

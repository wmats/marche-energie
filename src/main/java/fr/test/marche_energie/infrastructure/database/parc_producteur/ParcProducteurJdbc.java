package fr.test.marche_energie.infrastructure.database.parc_producteur;

import static fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabaseMapper.toParcProducteurDatabase;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.exceptions.NonTrouveException;
import fr.test.marche_energie.domain.use_cases.port.ParcProducteurPort;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ParcProducteurJdbc implements ParcProducteurPort {

  private ParcProducteurDatabaseRepository parcProducteurDatabaseRepository;

  @Override
  public void enregistrerParcProducteur(ParcProducteur parcProducteur) {
    parcProducteurDatabaseRepository.save(toParcProducteurDatabase(parcProducteur));
  }

  @Override
  public ParcProducteur recupererParcProducteur(UUID parcProducteurId) {
    return parcProducteurDatabaseRepository
        .findById(parcProducteurId)
        .map(ParcProducteurDatabaseMapper::toParcProducteur)
        .orElseThrow(
            () ->
                new NonTrouveException(
                    "Aucun parc producteur n'a été trouvé pour l'id: '%s'"
                        .formatted(parcProducteurId)));
  }

  @Override
  public List<ParcProducteur> recupererParcsProducteursParTypeMarche(TypeMarche typeMarche) {
    List<ParcProducteurDatabase> parcsProducteursDatabase =
        parcProducteurDatabaseRepository.findParcsProducteursByTypeMarche(typeMarche);
    return parcsProducteursDatabase.stream()
        .map(ParcProducteurDatabaseMapper::toParcProducteur)
        .toList();
  }
}

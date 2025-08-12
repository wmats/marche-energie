package fr.test.marche_energie.infrastructure.database.offre;

import static fr.test.marche_energie.infrastructure.database.offre.mapper.OffreDatabaseMapper.toOffreDatabase;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.port.OffrePort;
import fr.test.marche_energie.infrastructure.database.offre.mapper.OffreDatabaseMapper;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OffreJdbc implements OffrePort {

  private OffreDatabaseRepository offreDatabaseRepository;

  @Override
  public void enregistrerOffre(Offre offre) {
    offreDatabaseRepository.save(toOffreDatabase(offre));
  }

  @Override
  public Integer calculerProductionTotaleDUnParcSurUneTrancheHoraire(
      UUID parcProducteurId, TrancheHoraire trancheHoraire) {
    return offreDatabaseRepository.calculerProductionTotaleParParcEtTrancheHoraire(
        parcProducteurId, trancheHoraire);
  }

  @Override
  public List<Offre> recupererOffresDUnMarche(TypeMarche typeMarche) {
    List<OffreDatabase> offresDatabase = offreDatabaseRepository.findAllByMarche(typeMarche);
    return offresDatabase.stream().map(OffreDatabaseMapper::toOffre).toList();
  }
}

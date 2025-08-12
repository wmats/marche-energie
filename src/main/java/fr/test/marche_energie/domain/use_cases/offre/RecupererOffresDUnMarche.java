package fr.test.marche_energie.domain.use_cases.offre;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.port.OffrePort;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecupererOffresDUnMarche {

  private OffrePort offrePort;

  public List<Offre> executer(TypeMarche typeMarche) {
    return offrePort.recupererOffresDUnMarche(typeMarche);
  }
}

package fr.test.marche_energie.domain.use_cases.parc_producteur;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.port.ParcProducteurPort;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecupererParcsParTypeMarche {

  private ParcProducteurPort parcProducteurPort;

  public List<ParcProducteur> executer(TypeMarche typeMarche) {
    return parcProducteurPort.recupererParcsProducteursParTypeMarche(typeMarche);
  }
}

package fr.test.marche_energie.domain.entities.offre;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OffreBlocHoraireId {
  private UUID offreId;

  private TrancheHoraire trancheHoraire;
}

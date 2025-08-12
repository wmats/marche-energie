package fr.test.marche_energie.domain.entities.offre;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Offre {

  private UUID id;

  private TypeMarche marche;

  private Map<TrancheHoraire, OffreBlocHoraire> blocsHoraires;

  private LocalDateTime dateHeureCreation;

  private LocalDateTime dateHeureModification;
}

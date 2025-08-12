package fr.test.marche_energie.domain.entities.offre;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OffreAllocation {

  private ParcProducteur parcProducteur;

  private Integer production;
}

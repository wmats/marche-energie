package fr.test.marche_energie.domain.entities.offre;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OffreBlocHoraire {

  private OffreBlocHoraireId id;

  private BigDecimal prixPlancher;

  private List<OffreAllocation> allocations;
}

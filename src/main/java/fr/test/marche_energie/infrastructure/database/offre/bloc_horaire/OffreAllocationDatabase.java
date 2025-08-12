package fr.test.marche_energie.infrastructure.database.offre.bloc_horaire;

import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabase;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OffreAllocationDatabase {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parc_producteur_id", nullable = false)
  private ParcProducteurDatabase parcProducteur;

  @NotNull private Integer production;
}

package fr.test.marche_energie.infrastructure.database.offre.bloc_horaire;

import fr.test.marche_energie.infrastructure.database.offre.OffreDatabase;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "offre_bloc_horaire")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class OffreBlocHoraireDatabase {

  @EmbeddedId private OffreBlocHoraireDatabaseId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("offreDatabaseId")
  @JoinColumn(name = "offre_id", nullable = false)
  private OffreDatabase offreDatabase;

  @NotNull private BigDecimal prixPlancher;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "offre_bloc_horaire_allocation",
      joinColumns = {@JoinColumn(name = "offre_id"), @JoinColumn(name = "tranche_horaire")})
  private List<OffreAllocationDatabase> allocations;
}

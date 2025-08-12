package fr.test.marche_energie.infrastructure.database.offre.bloc_horaire;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OffreBlocHoraireDatabaseId implements Serializable {

  @Column(name = "offre_id", nullable = false, columnDefinition = "UUID")
  private UUID offreDatabaseId;

  @Enumerated(EnumType.STRING)
  @Column(name = "tranche_horaire", nullable = false)
  private TrancheHoraire trancheHoraire;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OffreBlocHoraireDatabaseId)) return false;
    OffreBlocHoraireDatabaseId that = (OffreBlocHoraireDatabaseId) o;
    return Objects.equals(offreDatabaseId, that.offreDatabaseId)
        && trancheHoraire == that.trancheHoraire;
  }

  @Override
  public int hashCode() {
    return Objects.hash(offreDatabaseId, trancheHoraire);
  }
}

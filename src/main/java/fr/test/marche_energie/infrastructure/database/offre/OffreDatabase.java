package fr.test.marche_energie.infrastructure.database.offre;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreBlocHoraireDatabase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "offre")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OffreDatabase {

  @Id private UUID id;

  @Enumerated(EnumType.STRING)
  @NotNull
  private TypeMarche marche;

  @OneToMany(
      mappedBy = "offreDatabase",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  @MapKey(name = "id.trancheHoraire")
  private Map<TrancheHoraire, OffreBlocHoraireDatabase> blocsHoraires;

  private LocalDateTime dateHeureCreation;

  private LocalDateTime dateHeureModification;

  public void ajouterBlocsHoraires(
      Map<TrancheHoraire, OffreBlocHoraireDatabase> blocHoraireDatabaseMap) {
    this.blocsHoraires = blocHoraireDatabaseMap;
    this.blocsHoraires.forEach((key, value) -> value.setOffreDatabase(this));
  }
}

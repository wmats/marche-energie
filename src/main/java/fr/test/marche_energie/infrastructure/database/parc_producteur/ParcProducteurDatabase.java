package fr.test.marche_energie.infrastructure.database.parc_producteur;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parc_producteur")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ParcProducteurDatabase {

  @Id private UUID id;

  @NotNull private String nom;

  @Enumerated(EnumType.STRING)
  @NotNull
  private TypeParcProducteur type;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "parc_producteur_production",
      joinColumns = @JoinColumn(name = "parc_producteur_id"))
  @MapKeyColumn(name = "tranche_horaire")
  @Column(name = "production")
  @MapKeyEnumerated(EnumType.STRING)
  private Map<TrancheHoraire, Integer> productionParTrancheHoraire;

  private LocalDateTime dateHeureCreation;

  private LocalDateTime dateHeureModification;
}

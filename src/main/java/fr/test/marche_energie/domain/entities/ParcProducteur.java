package fr.test.marche_energie.domain.entities;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeParcProducteur;
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
public class ParcProducteur {

  private UUID id;

  private String nom;

  private TypeParcProducteur type;

  private Map<TrancheHoraire, Integer> productionParTrancheHoraire;

  private LocalDateTime dateHeureCreation;

  private LocalDateTime dateHeureModification;
}

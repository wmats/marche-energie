package fr.test.marche_energie.domain.exceptions;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import java.util.UUID;

public class ProductionMaximaleDepasseeException extends RuntimeException {
  public ProductionMaximaleDepasseeException(UUID parcProducteurId, TrancheHoraire trancheHoraire) {
    super(
        "Le parc d'ID '%s' ne dispose pas d'une production suffisante sur la tranche horaire '%s'"
            .formatted(parcProducteurId, trancheHoraire.name()));
  }
}

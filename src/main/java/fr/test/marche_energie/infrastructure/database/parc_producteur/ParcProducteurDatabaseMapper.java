package fr.test.marche_energie.infrastructure.database.parc_producteur;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParcProducteurDatabaseMapper {

  public static ParcProducteurDatabase toParcProducteurDatabase(ParcProducteur parcProducteur) {
    return ParcProducteurDatabase.builder()
        .id(parcProducteur.getId())
        .nom(parcProducteur.getNom())
        .type(parcProducteur.getType())
        .productionParTrancheHoraire(parcProducteur.getProductionParTrancheHoraire())
        .dateHeureCreation(parcProducteur.getDateHeureCreation())
        .dateHeureModification(parcProducteur.getDateHeureModification())
        .build();
  }

  public static ParcProducteur toParcProducteur(ParcProducteurDatabase parcProducteurDatabase) {
    return ParcProducteur.builder()
        .id(parcProducteurDatabase.getId())
        .nom(parcProducteurDatabase.getNom())
        .type(parcProducteurDatabase.getType())
        .productionParTrancheHoraire(parcProducteurDatabase.getProductionParTrancheHoraire())
        .dateHeureCreation(parcProducteurDatabase.getDateHeureCreation())
        .dateHeureModification(parcProducteurDatabase.getDateHeureModification())
        .build();
  }
}

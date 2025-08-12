package fr.test.marche_energie.infrastructure.database.offre.mapper;

import static fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabaseMapper.toParcProducteurDatabase;

import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.infrastructure.database.offre.bloc_horaire.OffreAllocationDatabase;
import fr.test.marche_energie.infrastructure.database.parc_producteur.ParcProducteurDatabaseMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OffreAllocationDatabaseMapper {

  public static OffreAllocationDatabase toOffreAllocationDatabase(OffreAllocation offreAllocation) {
    return OffreAllocationDatabase.builder()
        .production(offreAllocation.getProduction())
        .parcProducteur(toParcProducteurDatabase(offreAllocation.getParcProducteur()))
        .build();
  }

  public static OffreAllocation toOffreAllocation(OffreAllocationDatabase offreAllocationDatabase) {
    return OffreAllocation.builder()
        .production(offreAllocationDatabase.getProduction())
        .parcProducteur(
            ParcProducteurDatabaseMapper.toParcProducteur(
                offreAllocationDatabase.getParcProducteur()))
        .build();
  }
}

package fr.test.marche_energie.infrastructure.database.offre;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OffreDatabaseRepository extends JpaRepository<OffreDatabase, UUID> {

  @Query(
      """
        SELECT COALESCE(SUM(a.production), 0)
        FROM OffreDatabase o
        JOIN o.blocsHoraires b
        JOIN b.allocations a
        WHERE a.parcProducteur.id = :parcProducteurId
           AND KEY(b) = :trancheHoraire
    """)
  Integer calculerProductionTotaleParParcEtTrancheHoraire(
      UUID parcProducteurId, TrancheHoraire trancheHoraire);

  List<OffreDatabase> findAllByMarche(TypeMarche marche);
}

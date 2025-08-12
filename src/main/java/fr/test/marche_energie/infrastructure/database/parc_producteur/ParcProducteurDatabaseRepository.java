package fr.test.marche_energie.infrastructure.database.parc_producteur;

import fr.test.marche_energie.domain.enums.TypeMarche;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParcProducteurDatabaseRepository
    extends JpaRepository<ParcProducteurDatabase, UUID> {

  @Query(
      """
    SELECT DISTINCT a.parcProducteur
    FROM OffreDatabase o
    JOIN o.blocsHoraires obh
    JOIN obh.allocations a
    WHERE o.marche = :typeMarche
""")
  List<ParcProducteurDatabase> findParcsProducteursByTypeMarche(
      @Param("typeMarche") TypeMarche typeMarche);
}

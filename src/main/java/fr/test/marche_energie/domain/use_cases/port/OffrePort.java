package fr.test.marche_energie.domain.use_cases.port;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.util.List;
import java.util.UUID;

public interface OffrePort {

  void enregistrerOffre(Offre offre);

  Integer calculerProductionTotaleDUnParcSurUneTrancheHoraire(
      UUID parcProducteurId, TrancheHoraire trancheHoraire);

  List<Offre> recupererOffresDUnMarche(TypeMarche typeMarche);
}

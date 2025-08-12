package fr.test.marche_energie.domain.use_cases.port;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.util.List;
import java.util.UUID;

public interface ParcProducteurPort {

  void enregistrerParcProducteur(ParcProducteur parcProducteur);

  ParcProducteur recupererParcProducteur(UUID parcProducteurId);

  List<ParcProducteur> recupererParcsProducteursParTypeMarche(TypeMarche typeMarche);
}

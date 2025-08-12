package fr.test.marche_energie.fixtures.parc_producteur;

import fr.test.marche_energie.application.rest.parc_producteur.entities.ParcProducteurInputApi;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import java.util.Map;

public class ParcProducteurInputApiTestBuilder {
  private String nom = "Parc producteur #1";
  private String type = "SOLAIRE";
  private Map<TrancheHoraire, Integer> production =
      Map.of(
          TrancheHoraire.TRANCHE_1, 0,
          TrancheHoraire.TRANCHE_2, 1,
          TrancheHoraire.TRANCHE_3, 2,
          TrancheHoraire.TRANCHE_4, 2,
          TrancheHoraire.TRANCHE_5, 2,
          TrancheHoraire.TRANCHE_6, 2,
          TrancheHoraire.TRANCHE_7, 1,
          TrancheHoraire.TRANCHE_8, 0);

  public static ParcProducteurInputApiTestBuilder unParcProducteurInputApi() {
    return new ParcProducteurInputApiTestBuilder();
  }

  public ParcProducteurInputApi build() {
    return ParcProducteurInputApi.builder()
        .nom(nom)
        .type(type)
        .productionParTrancheHoraire(production)
        .build();
  }

  public ParcProducteurInputApiTestBuilder avecNom(String nom) {
    this.nom = nom;
    return this;
  }

  public ParcProducteurInputApiTestBuilder avecType(String type) {
    this.type = type;
    return this;
  }

  public ParcProducteurInputApiTestBuilder avecProduction(Map<TrancheHoraire, Integer> production) {
    this.production = production;
    return this;
  }
}

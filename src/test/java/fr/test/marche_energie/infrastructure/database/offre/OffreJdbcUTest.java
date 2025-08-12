package fr.test.marche_energie.infrastructure.database.offre;

import static fr.test.marche_energie.fixtures.offre.OffreDatabaseTestBuilder.uneOffreDatabase;
import static fr.test.marche_energie.fixtures.offre.OffreTestBuilder.uneOffre;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.enums.TypeMarche;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OffreJdbcUTest {

  @InjectMocks private OffreJdbc offreJdbc;

  @Mock private OffreDatabaseRepository offreDatabaseRepository;

  @Captor private ArgumentCaptor<OffreDatabase> offreDatabaseArgumentCaptor;

  @Test
  void enregistrerOffre_devrait_mapper_et_enregistrer_la_nouvelle_offre() {
    // Given
    Offre offre = uneOffre().build();

    // When
    offreJdbc.enregistrerOffre(offre);

    // Then
    OffreDatabase offreDatabaseAEnregistrer = uneOffreDatabase().build();
    verify(offreDatabaseRepository, times(1)).save(offreDatabaseArgumentCaptor.capture());
    OffreDatabase offreDatabaseEnregistre = offreDatabaseArgumentCaptor.getValue();
    assertThat(offreDatabaseEnregistre)
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(OffreDatabase.class)
        .isEqualTo(offreDatabaseAEnregistrer);
  }

  @Test
  void
      calculerProductionTotaleDUnParcSurUneTrancheHoraire_devrait_renvoyer_la_production_totale_du_parc_choisi_sur_une_tranche_horaire() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    TrancheHoraire trancheHoraire = TrancheHoraire.TRANCHE_1;
    given(
            offreDatabaseRepository.calculerProductionTotaleParParcEtTrancheHoraire(
                parcProducteurId, trancheHoraire))
        .willReturn(1);

    // When
    Integer productionTotale =
        offreDatabaseRepository.calculerProductionTotaleParParcEtTrancheHoraire(
            parcProducteurId, trancheHoraire);

    // Then
    verify(offreDatabaseRepository, times(1))
        .calculerProductionTotaleParParcEtTrancheHoraire(parcProducteurId, trancheHoraire);
    assertThat(productionTotale).isEqualTo(1);
  }

  @Test
  void
      recupererOffresDUnMarche_devrait_renvoyer_la_liste_des_offres_placees_sur_le_marche_considere() {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    OffreDatabase offreDatabaseRenvoyee = uneOffreDatabase().build();
    given(offreDatabaseRepository.findAllByMarche(typeMarche))
        .willReturn(List.of(offreDatabaseRenvoyee));

    // When
    List<Offre> offres = offreJdbc.recupererOffresDUnMarche(typeMarche);

    // Then
    verify(offreDatabaseRepository, times(1)).findAllByMarche(typeMarche);
    assertThat(offres.isEmpty()).isFalse();
    assertThat(offres.size()).isEqualTo(1);
    Offre offreAttendue = uneOffre().build();
    assertThat(offres.getFirst()).usingRecursiveComparison().isEqualTo(offreAttendue);
  }
}

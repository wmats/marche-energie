package fr.test.marche_energie.domain.use_cases.offre.interactors;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.exceptions.NonTrouveException;
import fr.test.marche_energie.domain.exceptions.ProductionMaximaleDepasseeException;
import fr.test.marche_energie.domain.use_cases.port.OffrePort;
import fr.test.marche_energie.domain.use_cases.port.ParcProducteurPort;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VerifierEtConstruireOffreAllocationUTest {

  @InjectMocks private VerifierEtConstruireOffreAllocation verifierEtConstruireOffreAllocation;

  @Mock private ParcProducteurPort parcProducteurPort;

  @Mock private OffrePort offrePort;

  @Test
  void executer_devrait_renvoyer_une_exception_si_le_parc_producteur_n_existe_pas() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    OffreAllocation offreAllocation =
        OffreAllocation.builder()
            .parcProducteur(ParcProducteur.builder().id(parcProducteurId).build())
            .build();
    given(parcProducteurPort.recupererParcProducteur(parcProducteurId))
        .willThrow(
            new NonTrouveException(
                "Aucun parc producteur n'a été trouvé pour l'id: '%s'"
                    .formatted(parcProducteurId)));

    // When
    assertThrows(
        NonTrouveException.class,
        () ->
            verifierEtConstruireOffreAllocation.executer(
                offreAllocation, TrancheHoraire.TRANCHE_1));
    verifyNoInteractions(offrePort);
  }

  @Test
  void
      executer_devrait_renvoyer_une_exception_si_la_production_allouee_au_parc_depasse_sa_production_disponible() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    TrancheHoraire trancheHoraire = TrancheHoraire.TRANCHE_1;
    OffreAllocation offreAllocation =
        OffreAllocation.builder()
            .production(1)
            .parcProducteur(ParcProducteur.builder().id(parcProducteurId).build())
            .build();
    given(parcProducteurPort.recupererParcProducteur(parcProducteurId))
        .willReturn(unParcProducteur().avecId(parcProducteurId).build());
    given(
            offrePort.calculerProductionTotaleDUnParcSurUneTrancheHoraire(
                parcProducteurId, trancheHoraire))
        .willReturn(3);

    // When - Then
    String messageExceptionAttendu =
        "Le parc d'ID '%s' ne dispose pas d'une production suffisante sur la tranche horaire '%s'"
            .formatted(parcProducteurId, trancheHoraire);
    Exception exception =
        assertThrows(
            ProductionMaximaleDepasseeException.class,
            () -> verifierEtConstruireOffreAllocation.executer(offreAllocation, trancheHoraire));
    assertThat(exception.getMessage()).isEqualTo(messageExceptionAttendu);
    verify(parcProducteurPort, times(1)).recupererParcProducteur(parcProducteurId);
    verify(offrePort, times(1))
        .calculerProductionTotaleDUnParcSurUneTrancheHoraire(parcProducteurId, trancheHoraire);
  }

  @Test
  void
      executer_devrait_renvoyer_une_allocation_complete_si_le_parc_producteur_existe_et_dispose_d_une_production_suffisante() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    TrancheHoraire trancheHoraire = TrancheHoraire.TRANCHE_1;
    OffreAllocation offreAllocation =
        OffreAllocation.builder()
            .production(3)
            .parcProducteur(ParcProducteur.builder().id(parcProducteurId).build())
            .build();
    ParcProducteur parcProducteurRecupere =
        unParcProducteur()
            .avecId(parcProducteurId)
            .avecProductionParTrancheMap(Map.of(trancheHoraire, 6))
            .build();
    given(parcProducteurPort.recupererParcProducteur(parcProducteurId))
        .willReturn(parcProducteurRecupere);
    given(
            offrePort.calculerProductionTotaleDUnParcSurUneTrancheHoraire(
                parcProducteurId, trancheHoraire))
        .willReturn(1);

    // When
    OffreAllocation offreAllocationObtenue =
        verifierEtConstruireOffreAllocation.executer(offreAllocation, trancheHoraire);

    // Then
    OffreAllocation offreAllocationAttendue =
        OffreAllocation.builder().production(3).parcProducteur(parcProducteurRecupere).build();
    verify(parcProducteurPort, times(1)).recupererParcProducteur(parcProducteurId);
    verify(offrePort, times(1))
        .calculerProductionTotaleDUnParcSurUneTrancheHoraire(parcProducteurId, trancheHoraire);
    assertThat(offreAllocationObtenue).isNotNull();
    assertThat(offreAllocationObtenue)
        .usingRecursiveComparison()
        .isEqualTo(offreAllocationAttendue);
  }
}

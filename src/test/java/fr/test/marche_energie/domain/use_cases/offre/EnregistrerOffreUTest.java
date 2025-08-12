package fr.test.marche_energie.domain.use_cases.offre;

import static fr.test.marche_energie.fixtures.offre.OffreTestBuilder.uneOffre;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.use_cases.offre.interactors.VerifierEtConstruireOffreAllocation;
import fr.test.marche_energie.domain.use_cases.port.HorlogePort;
import fr.test.marche_energie.domain.use_cases.port.OffrePort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnregistrerOffreUTest {

  @InjectMocks private EnregistrerOffre enregistrerOffre;

  @Mock private OffrePort offrePort;

  @Mock private VerifierEtConstruireOffreAllocation verifierEtConstruireOffreAllocation;

  @Mock private HorlogePort horlogePort;

  @Captor private ArgumentCaptor<Offre> offreArgumentCaptor;

  @Test
  void
      executer_devrait_enregistrer_la_nouvelle_offre_si_elle_repond_a_tous_les_criteres_d_allocation_de_production() {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(2025, 8, 11, 15, 32);
    ParcProducteur parcProducteur = unParcProducteur().build();
    TrancheHoraire trancheHoraire = TrancheHoraire.TRANCHE_1;
    OffreAllocation offreAllocationOriginale =
        OffreAllocation.builder()
            .production(1)
            .parcProducteur(ParcProducteur.builder().id(parcProducteur.getId()).build())
            .build();
    OffreBlocHoraire offreBlocHoraire =
        OffreBlocHoraire.builder()
            .allocations(List.of(offreAllocationOriginale))
            .prixPlancher(BigDecimal.valueOf(1000))
            .id(null)
            .build();
    OffreAllocation offreAllocationVerifiee =
        OffreAllocation.builder().production(1).parcProducteur(parcProducteur).build();
    Offre offre = uneOffre().avecBlocsHoraires(Map.of(trancheHoraire, offreBlocHoraire)).build();

    given(verifierEtConstruireOffreAllocation.executer(offreAllocationOriginale, trancheHoraire))
        .willReturn(offreAllocationVerifiee);
    given(horlogePort.recupererLocalDateTimeUTC()).willReturn(horodatage);

    // When
    enregistrerOffre.executer(offre);

    // Then
    verify(offrePort, times(1)).enregistrerOffre(offreArgumentCaptor.capture());
    Offre offreEnregistree = offreArgumentCaptor.getValue();
    Offre offreEnregistreeAttendue =
        uneOffre().avecDateHeureCreation(horodatage).avecDateHeureModification(horodatage).build();
    assertThat(offreEnregistree)
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(UUID.class)
        .isEqualTo(offreEnregistreeAttendue);
  }
}

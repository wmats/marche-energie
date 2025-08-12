package fr.test.marche_energie.domain.use_cases.parc_producteur;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.use_cases.port.HorlogePort;
import fr.test.marche_energie.domain.use_cases.port.ParcProducteurPort;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EnregistrerParcProducteurUTest {

  @InjectMocks private EnregistrerParcProducteur enregistrerParcProducteur;

  @Mock private HorlogePort horlogePort;

  @Mock ParcProducteurPort parcProducteurPort;

  @Captor private ArgumentCaptor<ParcProducteur> parcProducteurArgumentCaptor;

  @Test
  void executer_devrait_creer_un_nouveau_parc_producteur_a_la_date_du_jour() {
    // Given
    LocalDateTime horodatage = LocalDateTime.of(25, 8, 10, 11, 38);
    ParcProducteur parcProducteur = unParcProducteur().build();
    given(horlogePort.recupererLocalDateTimeUTC()).willReturn(horodatage);
    doNothing().when(parcProducteurPort).enregistrerParcProducteur(any(ParcProducteur.class));

    // When
    enregistrerParcProducteur.executer(parcProducteur);

    // Then
    ParcProducteur parcProducteurAEnregistrer =
        parcProducteur.toBuilder()
            .dateHeureCreation(horodatage)
            .dateHeureModification(horodatage)
            .build();
    verify(parcProducteurPort, times(1))
        .enregistrerParcProducteur(parcProducteurArgumentCaptor.capture());
    ParcProducteur parcProducteurEnregistre = parcProducteurArgumentCaptor.getValue();
    assertThat(parcProducteurEnregistre)
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(UUID.class)
        .isEqualTo(parcProducteurAEnregistrer);
  }
}

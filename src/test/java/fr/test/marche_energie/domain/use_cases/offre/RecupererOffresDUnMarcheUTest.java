package fr.test.marche_energie.domain.use_cases.offre;

import static fr.test.marche_energie.fixtures.offre.OffreTestBuilder.uneOffre;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.port.OffrePort;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecupererOffresDUnMarcheUTest {

  @InjectMocks private RecupererOffresDUnMarche recupererOffresDUnMarche;

  @Mock private OffrePort offrePort;

  @Test
  void executer_devrait_appeler_le_port_pour_recuperer_les_offres_placees_sur_un_marche() {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    Offre offreRenvoyee = uneOffre().build();
    given(offrePort.recupererOffresDUnMarche(typeMarche)).willReturn(List.of(offreRenvoyee));

    // When
    List<Offre> offres = recupererOffresDUnMarche.executer(typeMarche);

    // Then
    verify(offrePort, times(1)).recupererOffresDUnMarche(typeMarche);

    assertThat(offres.isEmpty()).isFalse();
    assertThat(offres.size()).isEqualTo(1);
    assertThat(offres.getFirst()).usingRecursiveComparison().isEqualTo(offreRenvoyee);
  }
}

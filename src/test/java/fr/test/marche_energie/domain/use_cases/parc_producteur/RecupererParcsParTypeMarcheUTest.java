package fr.test.marche_energie.domain.use_cases.parc_producteur;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.use_cases.port.ParcProducteurPort;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecupererParcsParTypeMarcheUTest {

  @InjectMocks private RecupererParcsParTypeMarche recupererParcsParTypeMarche;

  @Mock private ParcProducteurPort parcProducteurPort;

  @Test
  void
      executer_devrait_appeler_le_port_pour_recuperer_tous_les_parcs_producteur_sur_le_marche_choisi() {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    ParcProducteur parcProducteurRenvoye = unParcProducteur().build();
    given(parcProducteurPort.recupererParcsProducteursParTypeMarche(typeMarche))
        .willReturn(List.of(parcProducteurRenvoye));

    // When
    List<ParcProducteur> parcsProducteurs = recupererParcsParTypeMarche.executer(typeMarche);

    // Then
    verify(parcProducteurPort, times(1)).recupererParcsProducteursParTypeMarche(typeMarche);
    assertThat(parcsProducteurs.isEmpty()).isFalse();
    assertThat(parcsProducteurs.size()).isEqualTo(1);
    assertThat(parcsProducteurs.getFirst())
        .usingRecursiveComparison()
        .isEqualTo(parcProducteurRenvoye);
  }
}

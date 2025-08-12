package fr.test.marche_energie.infrastructure.database.parc_producteur;

import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurDatabaseTestBuilder.unParcProducteurDatabase;
import static fr.test.marche_energie.fixtures.parc_producteur.ParcProducteurTestBuilder.unParcProducteur;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.enums.TypeMarche;
import fr.test.marche_energie.domain.exceptions.NonTrouveException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ParcProducteurJdbcUTest {

  @InjectMocks private ParcProducteurJdbc parcProducteurJdbc;

  @Mock private ParcProducteurDatabaseRepository parcProducteurDatabaseRepository;

  @Captor private ArgumentCaptor<ParcProducteurDatabase> parcProducteurDatabaseArgumentCaptor;

  @Test
  void enregistrerParcProducteur_devrait_mapper_et_enregistrer_le_nouveau_parc_producteur() {
    // Given
    ParcProducteur parcProducteur = unParcProducteur().build();

    // When
    parcProducteurJdbc.enregistrerParcProducteur(parcProducteur);

    // Then
    ParcProducteurDatabase parcProducteurDatabaseAEnregistrer = unParcProducteurDatabase().build();
    verify(parcProducteurDatabaseRepository, times(1))
        .save(parcProducteurDatabaseArgumentCaptor.capture());
    ParcProducteurDatabase parcProducteurDatabaseEnregistre =
        parcProducteurDatabaseArgumentCaptor.getValue();
    assertThat(parcProducteurDatabaseEnregistre)
        .usingRecursiveComparison()
        .isEqualTo(parcProducteurDatabaseAEnregistrer);
  }

  @Test
  void recupererParcProducteur_devrait_renvoyer_la_reference_d_un_parc_producteur() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    ParcProducteurDatabase parcProducteurDatabase =
        unParcProducteurDatabase().avecId(parcProducteurId).build();
    given(parcProducteurDatabaseRepository.findById(parcProducteurId))
        .willReturn(Optional.of(parcProducteurDatabase));

    // When
    ParcProducteur parcProducteur = parcProducteurJdbc.recupererParcProducteur(parcProducteurId);

    // Then
    verify(parcProducteurDatabaseRepository, times(1)).findById(parcProducteurId);
    ParcProducteur parcProducteurAttendu = unParcProducteur().build();
    assertThat(parcProducteur)
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(UUID.class)
        .isEqualTo(parcProducteurAttendu);
  }

  @Test
  void recupererParcProducteur_devrait_renvoyer_une_exception_si_le_parc_producteur_n_existe_pas() {
    // Given
    UUID parcProducteurId = UUID.randomUUID();
    given(parcProducteurDatabaseRepository.findById(parcProducteurId)).willReturn(Optional.empty());

    // When - Then
    String messageExceptionAttendu =
        "Aucun parc producteur n'a été trouvé pour l'id: '%s'".formatted(parcProducteurId);
    Exception exception =
        assertThrows(
            NonTrouveException.class,
            () -> parcProducteurJdbc.recupererParcProducteur(parcProducteurId));
    assertThat(exception.getMessage()).isEqualTo(messageExceptionAttendu);
  }

  @Test
  void
      recupererParcsProducteursParTypeMarche_devrait_renvoyer_les_parcs_producteurs_d_un_marche_donne() {
    // Given
    TypeMarche typeMarche = TypeMarche.RESERVE_PRIMAIRE;
    ParcProducteurDatabase parcProducteurDatabaseRenvoye = unParcProducteurDatabase().build();
    given(parcProducteurDatabaseRepository.findParcsProducteursByTypeMarche(typeMarche))
        .willReturn(List.of(parcProducteurDatabaseRenvoye));

    // When
    List<ParcProducteur> parcsProducteurs =
        parcProducteurJdbc.recupererParcsProducteursParTypeMarche(typeMarche);

    // Then
    verify(parcProducteurDatabaseRepository, times(1)).findParcsProducteursByTypeMarche(typeMarche);
    assertThat(parcsProducteurs.isEmpty()).isFalse();
    assertThat(parcsProducteurs.size()).isEqualTo(1);
    ParcProducteur parcProducteurAttendu = unParcProducteur().build();
    assertThat(parcsProducteurs.getFirst())
        .usingRecursiveComparison()
        .isEqualTo(parcProducteurAttendu);
  }
}

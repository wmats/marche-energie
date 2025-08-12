package fr.test.marche_energie.application.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import fr.test.marche_energie.domain.enums.TrancheHoraire;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class DeserialiseurTrancheHoraireUTest {

  private final DeserialiseurTrancheHoraire deserialiseurTrancheHoraire =
      new DeserialiseurTrancheHoraire();

  @ParameterizedTest
  @EnumSource(value = TrancheHoraire.class)
  void deserializeKey_devrait_renvoyer_la_tranche_horaire_si_elle_existe(
      TrancheHoraire trancheHoraire) throws IOException {
    // When
    TrancheHoraire trancheHoraireObtenue =
        deserialiseurTrancheHoraire.deserializeKey(trancheHoraire.name(), null);

    // Then
    assertThat(trancheHoraireObtenue).isEqualTo(trancheHoraire);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "TRANCHE_INCONNUE"})
  void deserializeKey_devrait_renvoyer_une_exception_si_la_tranche_horaire_indiquee_n_existe_pas(
      String cle) {
    // When - Then
    String messageExceptionAttendu =
        "La clé %s ne correspond à aucune tranche horaire".formatted(cle);
    Exception exception =
        assertThrows(
            IOException.class, () -> deserialiseurTrancheHoraire.deserializeKey(cle, null));
    assertThat(exception.getMessage()).isEqualTo(messageExceptionAttendu);
  }
}

package fr.test.marche_energie.domain.enums;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrancheHoraire {
  TRANCHE_1(LocalTime.of(0, 0), LocalTime.of(3, 0)),
  TRANCHE_2(LocalTime.of(3, 0), LocalTime.of(6, 0)),
  TRANCHE_3(LocalTime.of(6, 0), LocalTime.of(9, 0)),
  TRANCHE_4(LocalTime.of(9, 0), LocalTime.of(12, 0)),
  TRANCHE_5(LocalTime.of(12, 0), LocalTime.of(15, 0)),
  TRANCHE_6(LocalTime.of(15, 0), LocalTime.of(18, 0)),
  TRANCHE_7(LocalTime.of(18, 0), LocalTime.of(21, 0)),
  TRANCHE_8(LocalTime.of(21, 0), LocalTime.MIDNIGHT);

  private final LocalTime debut;
  private final LocalTime fin;
}

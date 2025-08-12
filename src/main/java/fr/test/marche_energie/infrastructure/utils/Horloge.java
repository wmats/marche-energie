package fr.test.marche_energie.infrastructure.utils;

import fr.test.marche_energie.domain.use_cases.port.HorlogePort;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Horloge implements HorlogePort {

  private Clock clock;

  @Override
  public LocalDateTime recupererLocalDateTimeUTC() {
    return ZonedDateTime.now(clock).withZoneSameInstant(ZONE_ID_UTC).toLocalDateTime();
  }
}

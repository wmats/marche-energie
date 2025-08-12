package fr.test.marche_energie.domain.use_cases.port;

import java.time.LocalDateTime;
import java.time.ZoneId;

public interface HorlogePort {

  ZoneId ZONE_ID_UTC = ZoneId.of("UTC");

  LocalDateTime recupererLocalDateTimeUTC();
}

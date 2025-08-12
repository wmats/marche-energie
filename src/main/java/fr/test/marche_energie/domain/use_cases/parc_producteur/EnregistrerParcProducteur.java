package fr.test.marche_energie.domain.use_cases.parc_producteur;

import fr.test.marche_energie.domain.entities.ParcProducteur;
import fr.test.marche_energie.domain.use_cases.port.HorlogePort;
import fr.test.marche_energie.domain.use_cases.port.ParcProducteurPort;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnregistrerParcProducteur {

  private HorlogePort horlogePort;
  private ParcProducteurPort parcProducteurPort;

  @Transactional
  public void executer(ParcProducteur parcProducteur) {
    LocalDateTime horodatage = horlogePort.recupererLocalDateTimeUTC();
    parcProducteurPort.enregistrerParcProducteur(
        parcProducteur.toBuilder()
            .id(UUID.randomUUID())
            .dateHeureCreation(horodatage)
            .dateHeureModification(horodatage)
            .build());
  }
}

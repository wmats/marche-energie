package fr.test.marche_energie.domain.use_cases.offre;

import fr.test.marche_energie.domain.entities.offre.Offre;
import fr.test.marche_energie.domain.entities.offre.OffreAllocation;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraire;
import fr.test.marche_energie.domain.entities.offre.OffreBlocHoraireId;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import fr.test.marche_energie.domain.use_cases.offre.interactors.VerifierEtConstruireOffreAllocation;
import fr.test.marche_energie.domain.use_cases.port.HorlogePort;
import fr.test.marche_energie.domain.use_cases.port.OffrePort;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnregistrerOffre {

  private HorlogePort horlogePort;
  private VerifierEtConstruireOffreAllocation verifierEtConstruireOffreAllocation;
  private OffrePort offrePort;

  @Transactional
  public void executer(Offre offre) {

    Map<TrancheHoraire, List<OffreAllocation>> offresAllocationsVerifiees =
        verifierOffresAllocations(offre.getBlocsHoraires());

    Offre offreComplete = assemblerOffre(offre, offresAllocationsVerifiees);

    offrePort.enregistrerOffre(offreComplete);
  }

  private Map<TrancheHoraire, List<OffreAllocation>> verifierOffresAllocations(
      Map<TrancheHoraire, OffreBlocHoraire> blocsHoraires) {
    return blocsHoraires.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                blocHoraireMapEntry ->
                    blocHoraireMapEntry.getValue().getAllocations().stream()
                        .map(
                            offreAllocation ->
                                verifierEtConstruireOffreAllocation.executer(
                                    offreAllocation, blocHoraireMapEntry.getKey()))
                        .toList()));
  }

  private Offre assemblerOffre(
      Offre offre, Map<TrancheHoraire, List<OffreAllocation>> offresAllocationsVerifiees) {
    UUID offreId = UUID.randomUUID();
    LocalDateTime horodatage = horlogePort.recupererLocalDateTimeUTC();

    return offre.toBuilder()
        .id(offreId)
        .dateHeureCreation(horodatage)
        .dateHeureModification(horodatage)
        .blocsHoraires(
            offre.getBlocsHoraires().entrySet().stream()
                .collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        offreBlocHoraireEntry ->
                            offreBlocHoraireEntry.getValue().toBuilder()
                                .id(new OffreBlocHoraireId(offreId, offreBlocHoraireEntry.getKey()))
                                .allocations(
                                    offresAllocationsVerifiees.getOrDefault(
                                        offreBlocHoraireEntry.getKey(), List.of()))
                                .build())))
        .build();
  }
}

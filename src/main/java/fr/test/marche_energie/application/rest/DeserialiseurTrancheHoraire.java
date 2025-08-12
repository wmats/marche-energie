package fr.test.marche_energie.application.rest;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import fr.test.marche_energie.domain.enums.TrancheHoraire;
import java.io.IOException;

public class DeserialiseurTrancheHoraire extends KeyDeserializer {
  @Override
  public TrancheHoraire deserializeKey(String key, DeserializationContext ctxt) throws IOException {
    try {
      return TrancheHoraire.valueOf(key);
    } catch (IllegalArgumentException e) {
      throw new IOException("La clé %s ne correspond à aucune tranche horaire".formatted(key));
    }
  }
}

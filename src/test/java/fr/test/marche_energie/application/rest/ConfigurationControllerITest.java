package fr.test.marche_energie.application.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class ConfigurationControllerITest {
  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;

  protected String convertirEnJson(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }
}

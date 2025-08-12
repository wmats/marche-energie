package fr.test.marche_energie;

import java.time.Clock;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MarcheEnergieApplication {

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    SpringApplication.run(MarcheEnergieApplication.class, args);
  }

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }
}

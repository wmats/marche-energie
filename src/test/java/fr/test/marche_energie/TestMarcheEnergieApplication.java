package fr.test.marche_energie;

import org.springframework.boot.SpringApplication;

public class TestMarcheEnergieApplication {

  public static void main(String[] args) {
    SpringApplication.from(MarcheEnergieApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}

package fr.test.marche_energie.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeMarche {
  RESERVE_PRIMAIRE,
  RESERVE_SECONDAIRE,
  RESERVE_RAPIDE
}

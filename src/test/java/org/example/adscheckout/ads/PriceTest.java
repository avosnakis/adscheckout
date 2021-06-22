package org.example.adscheckout.ads;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

  @Test
  void givenPriceIs3Digits_whenGettingFormattedStr_thenCorrect() {
    String expected = "$1.99";

    String actual = new Price(199).getFormattedPrice();
    assertEquals(expected, actual);
  }

  @Test
  void givenPriceIs2Digits_whenGettingFormattedStr_thenStartsWith0() {
    String expected = "$0.99";

    String actual = new Price(99).getFormattedPrice();
    assertEquals(expected, actual);
  }

  @Test
  void givenPriceIs1Digit_whenGettingFormattedStr_thenStartsWithTwoZeroes() {
    String expected = "$0.09";

    String actual = new Price(9).getFormattedPrice();
    assertEquals(expected, actual);
  }

  @Test
  void whenPriceIsNegative_thenThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Price(-1));
  }
}
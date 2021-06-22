package org.example.adscheckout.ads;

import java.util.Objects;

public class Price {

  private final int priceInCents;

  public Price(int priceInCents) {
    if (priceInCents < 0) {
      throw new IllegalArgumentException("Price must be positive.");
    }

    this.priceInCents = priceInCents;
  }

  /**
   * @return The price formatted with a dollar sign and decimal point.
   */
  public String getFormattedPrice() {
    int cents = priceInCents % 100;
    int dollars = this.priceInCents / 100;

    return String.format("$%d.%02d", dollars, cents);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Price price = (Price) o;
    return priceInCents == price.priceInCents;
  }

  @Override
  public int hashCode() {
    return Objects.hash(priceInCents);
  }
}

package org.example.adscheckout.ads;

import java.util.Objects;

public class Price {

  private final int cents;

  public Price(int cents) {
    this.cents = cents;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Price price = (Price) o;
    return cents == price.cents;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cents);
  }
}

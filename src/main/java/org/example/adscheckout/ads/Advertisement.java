package org.example.adscheckout.ads;

import java.util.Objects;

public class Advertisement {

  private final String name;
  private final String displayName;
  private final String description;
  private final Price price;

  public Advertisement(String name, String displayName, String description, Price price) {
    this.name = name;
    this.displayName = displayName;
    this.description = description;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public Price getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Advertisement that = (Advertisement) o;
    return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, price);
  }
}

package org.example.adscheckout;

import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.Price;

import java.util.ArrayList;
import java.util.List;

/**
 * A user's cart of ads.
 */
public class Cart {

  private final List<Advertisement> ads = new ArrayList<>();

  /**
   * @param advertisement The ad to put in the cart.
   */
  public void addToCart(Advertisement advertisement) {
    ads.add(advertisement);
  }

  public String finalPrice() {
    int totalPriceInCents = ads.stream()
        .map(Advertisement::getPrice)
        .mapToInt(Price::getPriceInCents)
        .sum();
    return new Price(totalPriceInCents).getFormattedPrice();
  }
}

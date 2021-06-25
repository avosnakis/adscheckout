package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;
import org.example.adscheckout.ads.Advertisement;

import java.util.Objects;

/**
 * Deals which offer a discount on certain ads.
 */
public class DiscountDeal implements Deal {

  private final int newPrice;
  private final Advertisement applicableAd;

  public DiscountDeal(int newPrice, Advertisement applicableAd) {
    if (newPrice < 0) {
      throw new IllegalArgumentException("Price must be a positive integer.");
    }
    this.newPrice = newPrice;
    this.applicableAd = applicableAd;
  }

  @Override
  public int applyDeal(Cart cart) {
    int numApplicableAds = (int) cart.getAds()
        .stream()
        .filter(ad -> ad.getName().equals(applicableAd.getName()))
        .count();
    return numApplicableAds * newPrice;
  }

  @Override
  public Advertisement getApplicableAd() {
    return applicableAd;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiscountDeal that = (DiscountDeal) o;
    return newPrice == that.newPrice && Objects.equals(applicableAd, that.applicableAd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(newPrice, applicableAd);
  }
}

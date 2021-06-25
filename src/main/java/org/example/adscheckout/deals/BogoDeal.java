package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;
import org.example.adscheckout.ads.Advertisement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class for 'Buy x get y' deals
 */
public class BogoDeal implements Deal {

  private final int priceOfN;

  private final int nToGet;

  private final Advertisement applicableAd;

  public BogoDeal(int priceOfN, int nToGet, Advertisement applicableAd) {
    if (priceOfN < 0 || nToGet < 0) {
      throw new IllegalArgumentException("Deal values must be positive");
    }

    this.priceOfN = priceOfN;
    this.nToGet = nToGet;
    this.applicableAd = applicableAd;
  }

  @Override
  public int applyDeal(Cart cart) {
    List<Advertisement> applicableAds = cart.getAds()
        .stream()
        .filter(ad -> ad.getName().equals(applicableAd.getName()))
        .collect(Collectors.toList());

    int nGroups = applicableAds.size() / nToGet;
    int remainder = applicableAds.size() % nToGet;

    int groupsPrice = priceOfN * nGroups * applicableAd.getPrice().getPriceInCents();
    int remainderPrice = remainder * applicableAd.getPrice().getPriceInCents();

    return groupsPrice + remainderPrice;
  }

  @Override
  public Advertisement getApplicableAd() {
    return applicableAd;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BogoDeal bogoDeal = (BogoDeal) o;
    return priceOfN == bogoDeal.priceOfN && nToGet == bogoDeal.nToGet && Objects.equals(applicableAd, bogoDeal.applicableAd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(priceOfN, nToGet, applicableAd);
  }
}

package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;
import org.example.adscheckout.ads.Advertisement;

import java.util.List;
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
}

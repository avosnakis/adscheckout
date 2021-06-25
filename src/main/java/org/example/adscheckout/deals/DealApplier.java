package org.example.adscheckout.deals;

import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.Price;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DealApplier {

  /**
   * Applies the deals to the list of ads.
   *
   * @param adsToApply The ads to apply deals to.
   * @param deals      The deals to apply.
   * @return The total price taking into account the deals.
   */
  public Price applyDeals(List<Advertisement> adsToApply, List<Deal> deals) {
    Map<String, List<Advertisement>> adTypes = adsToApply.stream()
        .collect(Collectors.groupingBy(Advertisement::getName));

    Map<String, List<Deal>> dealTypes = deals.stream()
        .collect(Collectors.groupingBy(deal -> deal.getApplicableAd().getName()));

    int totalPrice = 0;
    for (Map.Entry<String, List<Advertisement>> adEntry : adTypes.entrySet()) {
      String name = adEntry.getKey();
      List<Deal> dealsToApply = dealTypes.get(name);
      List<Advertisement> validAds = adEntry.getValue();

      if (dealsToApply == null) {
        // there are no deals.
        totalPrice += sumAds(adEntry.getValue());
      } else {
        // there will only be one deal.
        totalPrice += dealsToApply.stream()
            .findFirst()
            .map(deal -> deal.applyDeal(validAds))
            .orElse(sumAds(validAds));
      }
    }

    return new Price(totalPrice);
  }

  private static int sumAds(List<Advertisement> ads) {
    return ads.stream()
        .map(Advertisement::getPrice)
        .mapToInt(Price::getPriceInCents)
        .sum();
  }
}


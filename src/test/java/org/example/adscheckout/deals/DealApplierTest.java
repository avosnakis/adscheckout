package org.example.adscheckout.deals;

import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.Price;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DealApplierTest {

  private final Advertisement testAd = new Advertisement("TEST", "Test", "TEST", new Price(199));
  private final Advertisement test2Ad = new Advertisement("TEST2", "Test", "TEST", new Price(100));

  @Test
  void givenDeal_whenApplied_appliesDiscount() {
    List<Advertisement> ads = List.of(testAd);
    List<Deal> deals = List.of(new DiscountDeal(99, testAd));

    assertEquals(new Price(99), new DealApplier().applyDeals(ads, deals));
  }

  @Test
  void givenNoDeals_whenApplied_priceOfAds() {
    List<Advertisement> ads = List.of(testAd, testAd);
    List<Deal> deals = Collections.emptyList();

    assertEquals(new Price(199 + 199), new DealApplier().applyDeals(ads, deals));
  }

  @Test
  void givenDifferentDealsOnDifferentAds_whenApplied_appliesDeals() {
    List<Advertisement> ads = List.of(testAd, test2Ad, test2Ad);
    List<Deal> deals = List.of(new DiscountDeal(99, testAd), new BogoDeal(1, 2, test2Ad));

    assertEquals(new Price(199), new DealApplier().applyDeals(ads, deals));
  }

}
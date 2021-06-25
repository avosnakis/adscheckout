package org.example.adscheckout.deals;

import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.Price;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountDealTest {

  @Test
  void givenDiscountPrice_whenApplied_thenNewPrice() {
    Advertisement advertisement = new Advertisement("classic", "", "", new Price(100));

    DiscountDeal discountDeal = new DiscountDeal(50, advertisement);

    assertEquals(50, discountDeal.applyDeal(List.of(advertisement)));
  }

  @Test
  void givenDiscountPrice_whenAppliedToMultipleAds_thenNewPrice() {
    Advertisement advertisement = new Advertisement("classic", "", "", new Price(100));

    DiscountDeal discountDeal = new DiscountDeal(50, advertisement);

    assertEquals(100, discountDeal.applyDeal(List.of(advertisement, advertisement)));
  }
}
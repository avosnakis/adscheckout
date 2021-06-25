package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;
import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountDealTest {

  @Test
  void givenDiscountPrice_whenApplied_thenNewPrice() {
    Advertisement advertisement = new Advertisement("classic", "", "", new Price(100));

    DiscountDeal discountDeal = new DiscountDeal(50, advertisement);
    Cart cart = new Cart();
    cart.addToCart(advertisement);

    assertEquals(50, discountDeal.applyDeal(cart));
  }

  @Test
  void givenDiscountPrice_whenAppliedToMultipleAds_thenNewPrice() {
    Advertisement advertisement = new Advertisement("classic", "", "", new Price(100));

    DiscountDeal discountDeal = new DiscountDeal(50, advertisement);
    Cart cart = new Cart();
    cart.addToCart(advertisement);
    cart.addToCart(advertisement);

    assertEquals(100, discountDeal.applyDeal(cart));
  }

}
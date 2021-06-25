package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;
import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BogoDealTest {

  @Test
  void when2ForPriceOf1_givesCorrectPrice() {
    Advertisement ad = new Advertisement("classic", "", "", new Price(100));
    BogoDeal bogoDeal = new BogoDeal(1, 2, ad);

    Cart cart = new Cart();
    cart.addToCart(ad);
    cart.addToCart(ad);

    assertEquals(100, bogoDeal.applyDeal(cart));
  }

  @Test
  void when3ForPriceOf1_givesCorrectPrice() {
    Advertisement ad = new Advertisement("classic", "", "", new Price(100));
    BogoDeal bogoDeal = new BogoDeal(1, 3, ad);

    Cart cart = new Cart();
    cart.addToCart(ad);
    cart.addToCart(ad);
    cart.addToCart(ad);

    assertEquals(100, bogoDeal.applyDeal(cart));
  }

  @Test
  void given2ForPriceOf1_when3InCart_chargedFor2() {
    Advertisement ad = new Advertisement("classic", "", "", new Price(100));
    BogoDeal bogoDeal = new BogoDeal(1, 2, ad);

    Cart cart = new Cart();
    cart.addToCart(ad);
    cart.addToCart(ad);
    cart.addToCart(ad);

    assertEquals(200, bogoDeal.applyDeal(cart));
  }

  @Test
  void given3ForPriceOf2_when3InCart_chargedFor2() {
    Advertisement ad = new Advertisement("classic", "", "", new Price(100));
    BogoDeal bogoDeal = new BogoDeal(2, 3, ad);

    Cart cart = new Cart();
    cart.addToCart(ad);
    cart.addToCart(ad);
    cart.addToCart(ad);

    assertEquals(200, bogoDeal.applyDeal(cart));
  }

  @Test
  void given5ForPriceOf4_when5InCart_chargedFor4() {
    Advertisement ad = new Advertisement("classic", "", "", new Price(100));
    BogoDeal bogoDeal = new BogoDeal(4, 5, ad);

    Cart cart = new Cart();
    cart.addToCart(ad);
    cart.addToCart(ad);
    cart.addToCart(ad);
    cart.addToCart(ad);
    cart.addToCart(ad);

    assertEquals(400, bogoDeal.applyDeal(cart));
  }

  @Test
  void givenDifferentAdsInCart_when2ForPriceOf1_regularCharge() {
    Advertisement ad = new Advertisement("classic", "", "", new Price(100));
    Advertisement otherAd = new Advertisement("notclassic", "", "", new Price(200));
    BogoDeal bogoDeal = new BogoDeal(1, 2, ad);

    Cart cart = new Cart();
    cart.addToCart(ad);
    cart.addToCart(otherAd);

    assertEquals(100, bogoDeal.applyDeal(cart));
  }
}
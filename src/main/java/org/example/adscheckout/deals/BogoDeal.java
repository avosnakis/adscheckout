package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;

/**
 * Class for 'Buy x get y' deals
 */
public class BogoDeal implements Deal {

  private final int priceOfN;

  private final int nToGet;

  public BogoDeal(int priceOfN, int nToGet) {
    if (priceOfN < 0 || nToGet < 0) {
      throw new IllegalArgumentException("Deal values must be positive");
    }

    this.priceOfN = priceOfN;
    this.nToGet = nToGet;
  }

  @Override
  public void applyDeal(Cart cart) {

  }
}

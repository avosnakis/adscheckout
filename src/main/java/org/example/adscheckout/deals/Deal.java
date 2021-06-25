package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;
import org.example.adscheckout.ads.Advertisement;

/**
 * Interface for different types of deals.
 */
public interface Deal {

  int applyDeal(Cart cart);

  Advertisement getApplicableAd();
}

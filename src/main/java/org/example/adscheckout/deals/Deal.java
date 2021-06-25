package org.example.adscheckout.deals;

import org.example.adscheckout.Cart;

/**
 * Interface for different types of deals.
 */
public interface Deal {

  int applyDeal(Cart cart);
}

package org.example.adscheckout.deals;

import org.example.adscheckout.ads.Advertisement;

import java.util.List;

/**
 * Interface for different types of deals.
 */
public interface Deal {

  int applyDeal(List<Advertisement> ads);

  Advertisement getApplicableAd();
}

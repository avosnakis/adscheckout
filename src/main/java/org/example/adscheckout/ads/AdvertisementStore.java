package org.example.adscheckout.ads;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Store for advertisements.
 */
public class AdvertisementStore {

  private final Map<String, Advertisement> advertisements = new HashMap<>();

  /**
   * Puts an ad in the store, allowing it to be retrieved by the ads' name.
   *
   * @param advertisement The ad.
   */
  public void putAd(Advertisement advertisement) {
    advertisements.put(advertisement.getName(), advertisement);
  }

  /**
   * @param name The name of the ad to retrieve.
   * @return The ad, or an empty result if no such ad exists.
   */
  public Optional<Advertisement> retrieveAd(String name) {
    return Optional.ofNullable(advertisements.get(name));
  }

  /**
   * @return All ads in the store.
   */
  public Set<Advertisement> retrieveAllAds() {
    return new HashSet<>(advertisements.values());
  }
}

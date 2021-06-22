package org.example.adscheckout.ads;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdvertisementStore {

  private final Map<String, Advertisement> advertisements = new HashMap<>();

  void putAd(Advertisement advertisement) {
    advertisements.put(advertisement.getName(), advertisement);
  }

  Optional<Advertisement> retrieveAd(String name) {
    return Optional.ofNullable(advertisements.get(name));
  }
}

package org.example.adscheckout.ads;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvertisementStoreTest {

  @Test
  void givenAdInStore_whenRetrieving_getsAd() {
    Advertisement ad = new Advertisement("TEST", "Test", "TEST", new Price(199));
    AdvertisementStore store = new AdvertisementStore();
    store.putAd(ad);

    assertEquals(Optional.of(ad), store.retrieveAd("TEST"));
    assertEquals(Collections.singleton(ad), store.retrieveAllAds());
  }

  @Test
  void givenAdInStore_whenRetrievingWithNonexistentId_getsEmpty() {
    Advertisement ad = new Advertisement("TEST", "Test", "TEST",
        new Price(199));
    AdvertisementStore store = new AdvertisementStore();
    store.putAd(ad);

    assertEquals(Optional.empty(), store.retrieveAd("NOT_TEST"));
    assertEquals(Collections.singleton(ad), store.retrieveAllAds());
  }
}
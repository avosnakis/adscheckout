package org.example.adscheckout.ads;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class AdvertisementStoreFactory {

  private static final ObjectMapper mapper = new ObjectMapper();

  public AdvertisementStore fromConfig(File file) {
    return new AdvertisementStore();
  }
}

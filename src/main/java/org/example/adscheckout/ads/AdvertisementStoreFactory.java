package org.example.adscheckout.ads;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class AdvertisementStoreFactory {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static AdvertisementStore fromConfig(File file) throws InvalidConfigurationException {
    return new AdvertisementStore();
  }
}

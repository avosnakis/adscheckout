package org.example.adscheckout.ads;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdvertisementStoreFactoryTest {

  private static final String TEST_DIR = "src/test/resources/config";

  @Test
  void givenValidConfig_whenBuilding_thenCorrect() throws InvalidConfigurationException, IOException {
    File file = confFile("good_config.json");

    AdvertisementStore store = AdvertisementStoreFactory.fromConfig(file);

    assertEquals(Optional.of(new Advertisement(
            "classic",
            "Classic Ad",
            "Offers the most basic level of advertisement",
            new Price(9999))),
        store.retrieveAd("classic")
    );
  }

  @Test
  void givenConfigMissingAds_whenBuilding_thenThrowsException() {
    File file = confFile("config_missing_ads.json");
    assertThrows(InvalidConfigurationException.class, () -> AdvertisementStoreFactory.fromConfig(file));
  }

  @Test
  void givenConfigHasMisconfiguredAd_whenBuilding_thenThrowsException() {
    File file = confFile("config_with_invalid_ad.json");
    assertThrows(InvalidConfigurationException.class, () -> AdvertisementStoreFactory.fromConfig(file));
  }

  @Test
  void givenConfigIsInvalidJson_whenBuilding_thenThrowsException() {
    File file = confFile("notjson");
    assertThrows(InvalidConfigurationException.class, () -> AdvertisementStoreFactory.fromConfig(file));
  }

  private static File confFile(String filename) {
    return new File(TEST_DIR, filename);
  }
}
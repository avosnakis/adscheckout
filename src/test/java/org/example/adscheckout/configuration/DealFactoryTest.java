package org.example.adscheckout.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.Price;
import org.example.adscheckout.deals.Deal;
import org.example.adscheckout.deals.DiscountDeal;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DealFactoryTest {

  private final Advertisement classicAd = new Advertisement(
      "classic",
      "Classic Ad",
      "Offers the most basic level of advertisement",
      new Price(9999));

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final String TEST_DIR = "src/test/resources/config";

  @Test
  void givenValidDealConf_whenBuilt_thenCorrectDeal() throws Exception {
    File file = new File(TEST_DIR, "good_config_with_deal.json");

    JsonNode conf = MAPPER.readTree(file);
    DealFactory dealFactory = new DealFactory(AdvertisementStoreFactory.fromConfig(conf));

    Map<String, List<Deal>> deals = dealFactory.buildDeals(conf);

    assertEquals(
        Map.of("testuser", List.of(new DiscountDeal(4999, classicAd))),
        deals
    );
  }

  @Test
  void givenInvalidDealConf_whenBuilt_thenThrowsException() throws Exception {
    File file = new File(TEST_DIR, "good_config_with_broken_deal.json");

    JsonNode conf = MAPPER.readTree(file);
    DealFactory dealFactory = new DealFactory(AdvertisementStoreFactory.fromConfig(conf));

    assertThrows(InvalidConfigurationException.class, () -> dealFactory.buildDeals(conf));
  }

  @Test
  void givenConfMissingDeals_whenBuilt_thenThrowsException() throws Exception {
    File file = new File(TEST_DIR, "config_missing_deals.json");

    JsonNode conf = MAPPER.readTree(file);
    DealFactory dealFactory = new DealFactory(AdvertisementStoreFactory.fromConfig(conf));

    assertThrows(InvalidConfigurationException.class, () -> dealFactory.buildDeals(conf));
  }

  @Test
  void givenDealWithMissingAdConf_whenBuilt_thenThrowsException() throws Exception {
    File file = new File(TEST_DIR, "good_config_with_deal_with_invalid_ad.json");

    JsonNode conf = MAPPER.readTree(file);
    DealFactory dealFactory = new DealFactory(AdvertisementStoreFactory.fromConfig(conf));

    assertThrows(InvalidConfigurationException.class, () -> dealFactory.buildDeals(conf));

  }
}
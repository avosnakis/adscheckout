package org.example.adscheckout.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.AdvertisementStore;
import org.example.adscheckout.ads.Price;

public class AdvertisementStoreFactory {

  private static final String ADS_PATH = "ads";
  private static final String NAME_PATH = "name";
  private static final String DISPLAY_PATH = "display";
  private static final String DESC_PATH = "description";
  private static final String PRICE_PATH = "price";

  /**
   * Builds an advertisement store from a config file.
   *
   * @param configNode The config file.
   * @return The advertisement store with all ads loaded.
   * @throws InvalidConfigurationException In case the configuration cannot be understood.
   */
  public static AdvertisementStore fromConfig(JsonNode configNode) throws InvalidConfigurationException {
    JsonNode adsNode = configNode.path(ADS_PATH);
    if (!adsNode.isArray()) {
      throw new InvalidConfigurationException("Ads must be in an array.");
    }

    AdvertisementStore advertisementStore = new AdvertisementStore();
    for (JsonNode adNode : adsNode) {
      Advertisement ad = buildAd(adNode);
      advertisementStore.putAd(ad);
    }
    return advertisementStore;
  }

  private static Advertisement buildAd(JsonNode adNode) throws InvalidConfigurationException {
    JsonNode name = adNode.path(NAME_PATH);
    JsonNode display = adNode.path(DISPLAY_PATH);
    JsonNode desc = adNode.path(DESC_PATH);
    JsonNode price = adNode.path(PRICE_PATH);

    // Validate that all of the ad's fields exist and are valid.
    ConfigUtil.checkProperty(name, JsonNode::isValueNode, "Ad name must be a string.");
    ConfigUtil.checkProperty(display, JsonNode::isValueNode, "Ad display must be a string.");
    ConfigUtil.checkProperty(desc, JsonNode::isValueNode, "Ad description must be a string.");
    ConfigUtil.checkProperty(price, JsonNode::isInt, "Ad price must be an integer.");

    return new Advertisement(name.asText(), display.asText(), desc.asText(), new Price(price.asInt()));
  }

}

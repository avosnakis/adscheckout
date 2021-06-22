package org.example.adscheckout.ads;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public class AdvertisementStoreFactory {

  private static final String ADS_PATH = "ads";
  private static final String NAME_PATH = "name";
  private static final String DISPLAY_PATH = "display";
  private static final String DESC_PATH = "description";
  private static final String PRICE_PATH = "price";

  private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementStoreFactory.class);

  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * Builds an advertisement store from a config file.
   *
   * @param file The config file.
   * @return The advertisement store with all ads loaded.
   * @throws InvalidConfigurationException In case the configuration cannot be understood.
   * @throws IOException                   If there is an IO error, such as a missing file.
   */
  public static AdvertisementStore fromConfig(File file) throws InvalidConfigurationException, IOException {
    JsonNode jsonNode;
    try {
      jsonNode = MAPPER.readTree(file);
    } catch (JsonProcessingException e) {
      LOGGER.error("Failed to parse configuration file.", e);
      throw new InvalidConfigurationException("Failed to parse configuration file.");
    }

    JsonNode adsNode = jsonNode.path(ADS_PATH);
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
    checkProperty(name, JsonNode::isValueNode, "Ad name must be a string.");
    checkProperty(display, JsonNode::isValueNode, "Ad display must be a string.");
    checkProperty(desc, JsonNode::isValueNode, "Ad description must be a string.");
    checkProperty(price, JsonNode::isInt, "Ad price must be an integer.");

    return new Advertisement(name.asText(), display.asText(), desc.asText(), new Price(price.asInt()));
  }

  private static void checkProperty(
      JsonNode property, Predicate<JsonNode> condition, String message
  ) throws InvalidConfigurationException {
    if (!condition.test(property)) {
      throw new InvalidConfigurationException(message);
    }
  }
}

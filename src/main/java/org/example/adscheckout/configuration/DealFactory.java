package org.example.adscheckout.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.AdvertisementStore;
import org.example.adscheckout.deals.BogoDeal;
import org.example.adscheckout.deals.Deal;
import org.example.adscheckout.deals.DiscountDeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DealFactory {

  private static final String USER = "user";
  private static final String AD = "ad";
  private static final String N_TO_GET = "nToGet";
  private static final String PRICE_OF_N = "priceOfN";
  private static final String NEW_PRICE = "newPrice";
  private static final String DEAL_TYPE = "type";
  private static final String DEALS_PATH = "deals";

  private final AdvertisementStore adStore;

  public DealFactory(AdvertisementStore adStore) {
    this.adStore = adStore;
  }

  /**
   * Builds the user -> deals mappings.
   *
   * @param configNode The configuration.
   * @return The mappings of users to their applicable deals.
   * @throws InvalidConfigurationException In case there is an incorrect configuration.
   */
  public Map<String, List<Deal>> buildDeals(JsonNode configNode) throws InvalidConfigurationException {
    JsonNode adsNode = configNode.path(DEALS_PATH);
    if (!adsNode.isArray()) {
      throw new InvalidConfigurationException("Deals must be in an array.");
    }

    Map<String, List<Deal>> deals = new HashMap<>();
    for (JsonNode dealNode : adsNode) {
      JsonNode user = dealNode.path(USER);
      ConfigUtil.checkProperty(user, JsonNode::isValueNode, "Deal missing user.");

      deals.putIfAbsent(user.asText(), new ArrayList<>());
      deals.get(user.asText()).add(buildDeal(dealNode));
    }

    // Last validation to check that there is only one type of deal per user per ad.
    for (Map.Entry<String, List<Deal>> dealEntries : deals.entrySet()) {
      onlyOneTypeOfDeal(dealEntries.getValue());
    }

    return deals;
  }

  private Deal buildDeal(JsonNode dealNode) throws InvalidConfigurationException {
    JsonNode dealType = dealNode.path(DEAL_TYPE);
    ConfigUtil.checkProperty(dealType, JsonNode::isValueNode, "Missing deal type.");

    if (dealType.asText().equals("bogodeal")) {
      return buildBogoDeal(dealNode);
    } else if (dealType.asText().equals("discountdeal")) {
      return buildDiscountDeal(dealNode);
    } else {
      throw new InvalidConfigurationException("Only valid deal types are bogodeal and discountdeal.");
    }
  }

  private Deal buildBogoDeal(JsonNode dealNode) throws InvalidConfigurationException {
    JsonNode adNode = dealNode.path(AD);
    ConfigUtil.checkProperty(adNode, JsonNode::isValueNode, "Deal missing ad.");
    Advertisement actualAd = adStore.retrieveAd(adNode.asText())
        .orElseThrow(() -> new InvalidConfigurationException("Cannot find ad."));

    JsonNode nToGetNode = dealNode.path(N_TO_GET);
    ConfigUtil.checkProperty(nToGetNode, JsonNode::isNumber, "Deal missing nToGet.");

    JsonNode priceOfNNode = dealNode.path(PRICE_OF_N);
    ConfigUtil.checkProperty(priceOfNNode, JsonNode::isNumber, "Deal missing priceOfN.");

    return new BogoDeal(priceOfNNode.asInt(), nToGetNode.asInt(), actualAd);
  }

  private Deal buildDiscountDeal(JsonNode dealNode) throws InvalidConfigurationException {
    JsonNode adNode = dealNode.path(AD);
    ConfigUtil.checkProperty(adNode, JsonNode::isValueNode, "Deal missing ad.");
    Advertisement actualAd = adStore.retrieveAd(adNode.asText())
        .orElseThrow(() -> new InvalidConfigurationException("Cannot find ad."));

    JsonNode newPriceNode = dealNode.path(NEW_PRICE);
    ConfigUtil.checkProperty(newPriceNode, JsonNode::isNumber, "Deal missing newPrice.");

    return new DiscountDeal(newPriceNode.asInt(), actualAd);
  }

  /**
   * There can only be one deal per ad per user.
   *
   * @param deals The deals to check.
   * @throws InvalidConfigurationException If there are multiple deals on the same ad.
   */
  private void onlyOneTypeOfDeal(List<Deal> deals) throws InvalidConfigurationException {
    Map<String, List<Deal>> dealGroups = deals.stream()
        .collect(Collectors.groupingBy(deal -> deal.getApplicableAd().getName()));

    for (List<Deal> dealGroup : dealGroups.values()) {
      if (dealGroup.size() > 1) {
        throw new InvalidConfigurationException("There can only be one deal per ad.");
      }
    }
  }
}

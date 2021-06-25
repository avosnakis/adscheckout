package org.example.adscheckout.configuration;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.function.Predicate;

public class ConfigUtil {

  /**
   * Checks that a JsonNode property matches a condition.
   *
   * @param property  The property.
   * @param condition The condition.
   * @param message   The message in the exception if the condition is false.
   * @throws InvalidConfigurationException If the condition does not match.
   */
  static void checkProperty(
      JsonNode property, Predicate<JsonNode> condition, String message
  ) throws InvalidConfigurationException {
    if (!condition.test(property)) {
      throw new InvalidConfigurationException(message);
    }
  }
}

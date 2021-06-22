package org.example.adscheckout.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.adscheckout.users.User;
import org.example.adscheckout.users.UserStore;

public class UserStoreFactory {

  private static final String USERS_PATH = "users";
  private static final String NAME_PATH = "username";
  private static final String DISPLAY_PATH = "display";

  /**
   * Builds an advertisement store from a config file.
   *
   * @param configNode The config file.
   * @return The user store with all users loaded.
   * @throws InvalidConfigurationException In case the configuration cannot be understood.
   */
  public static UserStore fromConfig(JsonNode configNode) throws InvalidConfigurationException {
    JsonNode usersNode = configNode.path(USERS_PATH);
    if (!usersNode.isArray()) {
      throw new InvalidConfigurationException("Ads must be in an array.");
    }

    UserStore userStore = new UserStore();
    for (JsonNode userNode : usersNode) {
      User user = buildUser(userNode);
      userStore.putUser(user);
    }
    return userStore;
  }

  private static User buildUser(JsonNode userNode) throws InvalidConfigurationException {
    JsonNode name = userNode.path(NAME_PATH);
    JsonNode display = userNode.path(DISPLAY_PATH);

    // Validate that all of the ad's fields exist and are valid.
    ConfigUtil.checkProperty(name, JsonNode::isValueNode, "Username must be a string.");
    ConfigUtil.checkProperty(display, JsonNode::isValueNode, "User display must be a string.");

    return new User(name.asText(), display.asText());
  }
}

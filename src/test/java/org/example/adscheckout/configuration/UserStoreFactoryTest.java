package org.example.adscheckout.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.adscheckout.users.User;
import org.example.adscheckout.users.UserStore;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserStoreFactoryTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final String TEST_DIR = "src/test/resources/config";

  @Test
  void givenValidConfig_whenBuilding_thenCorrect() throws InvalidConfigurationException, IOException {
    File file = confFile("good_config.json");

    UserStore store = UserStoreFactory.fromConfig(MAPPER.readTree(file));

    User expected = new User("testuser", "Test User");
    assertEquals(Optional.of(expected), store.retrieveUser("testuser"));
  }

  @Test
  void givenConfigMissingUsers_whenBuilding_thenThrowsException() {
    File file = confFile("config_missing_users.json");
    assertThrows(InvalidConfigurationException.class, () -> UserStoreFactory.fromConfig(MAPPER.readTree(file)));
  }

  @Test
  void givenConfigWithInvalidUser_whenBuilding_thenThrowsException() {
    File file = confFile("config_with_invalid_user.json");
    assertThrows(InvalidConfigurationException.class, () -> UserStoreFactory.fromConfig(MAPPER.readTree(file)));
  }

  private static File confFile(String filename) {
    return new File(TEST_DIR, filename);
  }
}
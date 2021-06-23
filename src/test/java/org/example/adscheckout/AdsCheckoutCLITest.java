package org.example.adscheckout;

import org.example.adscheckout.configuration.InvalidConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdsCheckoutCLITest {

  private OutputStream outputStream;
  private PrintStream printStream;

  @BeforeEach
  void setUp() {
    this.outputStream = new ByteArrayOutputStream();
    this.printStream = new PrintStream(outputStream);
  }

  @Test
  void givenLoggedInAsTestuser_whenCheckingOutAd_givesCorrectPrice() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(
        "1\ntestuser\n1\nclassic\n2\nexit".getBytes()
    );
    AdsCheckoutCLI adsCheckoutCLI = new AdsCheckoutCLI(inputStream, printStream);
    adsCheckoutCLI.execute("src/test/resources/config/good_config.json");

    assertTrue(containsStringParts(
        outputStream.toString(),
        "$99.99"
        )
    );
  }

  @Test
  void whenTryingToLoginAsNonexistentUser_givesError() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(
        "1\nnotarealuser\n2".getBytes()
    );
    AdsCheckoutCLI adsCheckoutCLI = new AdsCheckoutCLI(inputStream, printStream);
    adsCheckoutCLI.execute("src/test/resources/config/good_config.json");

    assertTrue(containsStringParts(
        outputStream.toString(),
        "Invalid user."
        )
    );
  }

  @Test
  void whenTryingToCheckoutNonexistentAd_givesError() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(
        "1\ntestuser\n1\nnot_a_real_ad\n2\nexit".getBytes()
    );
    AdsCheckoutCLI adsCheckoutCLI = new AdsCheckoutCLI(inputStream, printStream);
    adsCheckoutCLI.execute("src/test/resources/config/good_config.json");

    assertTrue(containsStringParts(
        outputStream.toString(),
        "not_a_real_ad not found.",
        "$0.00"
        )
    );
  }

  @Test
  void givenInvalidConfig_whenTryingToStart_givesError() {
    AdsCheckoutCLI adsCheckoutCLI = new AdsCheckoutCLI(new ByteArrayInputStream("".getBytes()), printStream);
    assertThrows(InvalidConfigurationException.class,
        () -> adsCheckoutCLI.execute("src/test/resources/config/config_missing_ads.json")
    );
  }

  @Test
  void whenNoConfigEntered_thenGivesError() {
    AdsCheckoutCLI adsCheckoutCLI = new AdsCheckoutCLI(new ByteArrayInputStream("".getBytes()), printStream);
    assertThrows(InvalidConfigurationException.class,
        adsCheckoutCLI::execute
    );
  }

  /**
   * Checks whether a string contains all of the input parts. Used instead of an equality as tbe full JSON files used
   * in the integration tests are sizeable, and checking that the unique IDs of each document are present is more robust.
   *
   * @param output The string.
   * @param parts  The parts to check.
   * @return Whether they are all contained.
   */
  private static boolean containsStringParts(String output, String... parts) {
    for (String part : parts) {
      if (!output.contains(part)) {
        System.out.println("Missing part: " + part);
        return false;
      }
    }

    return true;
  }
}
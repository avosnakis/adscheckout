package org.example.adscheckout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.adscheckout.ads.Advertisement;
import org.example.adscheckout.ads.AdvertisementStore;
import org.example.adscheckout.configuration.AdvertisementStoreFactory;
import org.example.adscheckout.configuration.InvalidConfigurationException;
import org.example.adscheckout.configuration.UserStoreFactory;
import org.example.adscheckout.users.User;
import org.example.adscheckout.users.UserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

/**
 * CLI for the application.
 */
public class AdsCheckoutCLI {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdsCheckoutCLI.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final int ADD_CODE = 1;
  private static final int LIST_CODE = 2;
  private static final int CHECKOUT_CODE = 3;
  private static final int EXIT_CODE = 4;

  private final InputStream inputStream;
  private final PrintStream printStream;

  AdsCheckoutCLI(InputStream inputStream, PrintStream printStream) {
    this.inputStream = inputStream;
    this.printStream = printStream;
  }

  public static void main(String... args) {
    AdsCheckoutCLI adsCheckoutCLI = new AdsCheckoutCLI(System.in, System.out);

    try {
      adsCheckoutCLI.execute(args);
    } catch (InvalidConfigurationException e) {
      System.exit(1);
    }
  }

  /**
   * Starts the CLI.
   *
   * @param args The submitted arguments.
   * @throws InvalidConfigurationException In case the provided configuration is invalid.
   */
  void execute(String... args) throws InvalidConfigurationException {
    if (args.length < 1) {
      throw new InvalidConfigurationException("No configuration file provided.");
    }

    File file = new File(args[0]);

    JsonNode configJson;
    try {
      configJson = MAPPER.readTree(file);
    } catch (JsonProcessingException e) {
      throw new InvalidConfigurationException("Configuration file is not valid JSON.");
    } catch (IOException e) {
      LOGGER.error("Error reading file.", e);
      throw new InvalidConfigurationException("Error reading file.");
    }

    AdvertisementStore advertisementStore = AdvertisementStoreFactory.fromConfig(configJson);
    UserStore userStore = UserStoreFactory.fromConfig(configJson);

    Scanner scanner = new Scanner(inputStream);
    Optional<User> maybeUser = login(userStore, scanner);

    if (maybeUser.isEmpty()) {
      printStream.println("Unknown user.");
      return;
    }

    User user = maybeUser.get();
    repl(advertisementStore, scanner, user);
  }

  private void repl(AdvertisementStore adStore, Scanner scanner, User user) {
    usage();
    Cart cart = new Cart();
    while (scanner.hasNext()) {
      try {
        String input = scanner.next();
        int res = Integer.parseInt(input);
        if (res == ADD_CODE) {
          addToCart(adStore, scanner, cart);
        } else if (res == LIST_CODE) {
          displayAds(adStore);
        } else if (res == CHECKOUT_CODE) {
          checkout(cart);
          return;
        } else if (res == EXIT_CODE) {
          printStream.println("Exiting Ads Shopping system.");
          return;
        } else {
          printStream.println("Unknown command.");
        }
      } catch (NumberFormatException e) {
        printStream.println("Unknown command.");
      }

      usage();
    }
  }

  private void checkout(Cart cart) {
    printStream.printf("Checking out, total price is %s.%n", cart.finalPrice());
    printStream.println("Thank you for using the Ads Shopping system!");
  }

  private void displayAds(AdvertisementStore adStore) {
    printStream.println();
    Set<Advertisement> ads = adStore.retrieveAllAds();
    for (Advertisement ad : ads) {
      printStream.format("%-20s%-20s%-80s%-20s%n",
          ad.getName(), ad.getDisplay(), ad.getDescription(), ad.getPrice().getFormattedPrice());
    }
  }

  private void addToCart(AdvertisementStore adStore, Scanner scanner, Cart cart) {
    printStream.println("Enter which ad you would like to put in your cart:");
    String name = scanner.next();

    Optional<Advertisement> maybeAd = adStore.retrieveAd(name);
    maybeAd.ifPresentOrElse(ad -> {
          cart.addToCart(ad);
          printStream.printf("Added %s to cart.%n", ad.getDisplay());
        },
        () -> printStream.format("Could not find %s.%n", name));
  }


  private void usage() {
    printStream.printf("Type %d to place an ad in your cart," +
        " %d to list available ads," +
        " %d to checkout," +
        " or %d to exit.%n", ADD_CODE, LIST_CODE, CHECKOUT_CODE, EXIT_CODE);
    printStream.println();
  }

  private Optional<User> login(UserStore userStore, Scanner scanner) {
    printStream.println("Please enter your username.");

    String username = scanner.next();
    return userStore.retrieveUser(username);
  }
}

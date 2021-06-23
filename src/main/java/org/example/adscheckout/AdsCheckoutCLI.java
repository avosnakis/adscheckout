package org.example.adscheckout;

import org.example.adscheckout.configuration.InvalidConfigurationException;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * CLI for the application.
 */
public class AdsCheckoutCLI {

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

  void execute(String... args) throws InvalidConfigurationException {

  }
}

package org.example.adscheckout.users;

public class User {

  private final String name;
  private final String display;

  public User(String name, String display) {
    this.name = name;
    this.display = display;
  }

  public String getName() {
    return name;
  }

  public String getDisplay() {
    return display;
  }
}

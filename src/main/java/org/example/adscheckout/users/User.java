package org.example.adscheckout.users;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(name, user.name) && Objects.equals(display, user.display);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, display);
  }
}

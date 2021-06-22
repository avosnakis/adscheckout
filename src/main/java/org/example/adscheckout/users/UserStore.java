package org.example.adscheckout.users;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Store for users.
 */
public class UserStore {

  private final Map<String, User> users = new HashMap<>();

  /**
   * Stores a user in the store, mapped against this user's username.
   *
   * @param user The user to store.
   */
  public void putUser(User user) {
    users.put(user.getName(), user);
  }

  /**
   * @param name The username of the user to retrieve.
   * @return The user, or an empty result if no such user exists.
   */
  public Optional<User> retrieveUser(String name) {
    return Optional.ofNullable(users.get(name));
  }
}

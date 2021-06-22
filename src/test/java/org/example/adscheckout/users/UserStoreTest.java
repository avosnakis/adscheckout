package org.example.adscheckout.users;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserStoreTest {

  @Test
  void givenUserInStore_whenRetrieving_getsUser() {
    User user = new User("TEST", "Test");
    UserStore store = new UserStore();
    store.putUser(user);

    assertEquals(Optional.of(user), store.retrieveUser("TEST"));
  }

  @Test
  void givenUserInStore_whenRetrievingWithNonexistentId_getsEmpty() {
    User user = new User("TEST", "Test");
    UserStore store = new UserStore();
    store.putUser(user);

    assertEquals(Optional.empty(), store.retrieveUser("NOT_TEST"));
  }
}
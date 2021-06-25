ads-checkout
==============

Build and execution
-------------

ads-checkout is a small command line application. It accepts a single JSON file as configuration input as well as one
config file.

It is written in Java 11 and packaged with maven. To build and use:

```
mvn package
java -jar target/adscheckout-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/config.json
```

Tests can be run with:

`mvn test`

The users, ads, and deals as specified in the prompt are located in the aforementioned config.json file in `src/main/resources`.

Usage
-----------

Once successfully logged in, the application will present a REPL where the user can make searches.

The user has five basic commands, `1`, `2`, `3`, `4`, or `5`.

`1` will let the user add an ad to their cart. Ads can only be added one at a time.

`2` displays a list of ads the user can ad to their cart.

`3` allows the user to check out their cart.

`4` allows the user to see the current contents of their cart.

`5` will simply exit the REPL and close the application.

Configuration
----------

The application must be provided with a config file. In the above example, it is config.json.

The configuration file must be of the following format:

```
{
  "ads": [
    {
      "name": string,
      "display": string,
      "description": string,
      "price": integer
    }
  ],
  "users": [
    {
      "username": string,
      "display": string
    }
  ],
  "deals": [
    {
      "user": string,
      "type": "bogodeal",
      "ad": string,
      "nToGet": integer,
      "priceOfN": integer
    },
    {
      "user": string,
      "type": "discountdeal",
      "ad": string,
      "newPrice": integer
    }
  ]
}
```

The `users` array allows you to configure which users can use the application.

The `ads` array allows you to configure what ads are available to purchase.

The `deals` array allows the user to configure what deals are available. Each deal has an associated user it is valid
for, and the ad it applies to. For each user, only one deal can be applied to each type of ad. For example, for a
particular user, if they have deals on the `classic` ad, then it can only be a `bogodeal` or `discountdeal`. Having both
on the same ad will cause an invalid configuration error.

There are two types of deals, `bogodeal` and `discountdeal`.

`bogodeal` is for deals such as 'Buy 2 for the price of 1'. For example:

```
    {
      "user": "default",
      "type": "bogodeal",
      "ad": "classic",
      "nToGet": 4,
      "priceOfN": 3
    }
```

will allow the user `default` to purchase 4 classic ads for the price of 3.

Discount simply replaces the price:

```
    {
      "user": "default",
      "type": "discountdeal",
      "ad": "classic",
      "newPrice": 4999
    }
```

will allow the user `default` to purchase a classic ad for $49.99.

Assumptions and limitations
---------

- All data can be reasonably stored in memory. Very large files will not work.
- A valid configuration file is necessary. The application will not start without one, and will log an error to the user.
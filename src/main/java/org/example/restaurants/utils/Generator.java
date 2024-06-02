package org.example.restaurants.utils;

import com.github.javafaker.Faker;
import org.example.restaurants.entities.OpeningHour;
import org.example.restaurants.entities.Restaurant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Generator {

    Random random;
    Faker faker;

    public Generator() {
        this.faker = new Faker(new Locale.Builder().setLanguage("sv").build());
        this.random = new Random();
    }

    public List<Restaurant> generateRestaurants(int amount) {
        var list = new ArrayList<Restaurant>();
        for (int i = 0; i < amount; i++) {
            list.add(generateRestaurant());
        }
        return list;
    }

    private Restaurant generateRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName());
        restaurant.setDescription(faker.chuckNorris().fact());
        restaurant.setRating(rating());
        restaurant.setCity(city());
        restaurant.setStreet(faker.address().streetAddress());
        restaurant.setZip(faker.address().zipCode());
        restaurant.setPhone(phone());
        restaurant.setEmail(faker.internet().emailAddress());
        restaurant.setOpeningHours(generateOpeningHours(restaurant));
        return restaurant;
    }

    private List<OpeningHour> generateOpeningHours(Restaurant restaurant) {
        List<OpeningHour> openingHours = new ArrayList<>();
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : daysOfWeek) {
            OpeningHour openingHour = new OpeningHour();
            openingHour.setRestaurant(restaurant);
            openingHour.setDayOfWeek(day);

            if (day.equals("Sunday") && random.nextDouble() < 0.25) {
                openingHour.setOpenTime(null);
                openingHour.setCloseTime(null);
            } else if (isWeekday(day) && random.nextDouble() < 0.20) {
                openingHour.setOpenTime(generateRandomTime(15, 16)); // Opens between 3 PM and 4 PM
                openingHour.setCloseTime(generateRandomTime(18, 23)); // Closes between 6 PM and 11 PM
            } else {
                openingHour.setOpenTime(generateRandomTime(6, 10)); // Opens between 6 AM and 10 AM
                openingHour.setCloseTime(generateRandomTime(18, 23)); // Closes between 6 PM and 11 PM
            }

            openingHours.add(openingHour);
        }
        return openingHours;
    }

    private boolean isWeekday(String day) {
        return !day.equals("Saturday") && !day.equals("Sunday");
    }

    private LocalTime generateRandomTime(int startHour, int endHour) {
        int hour = random.nextInt(endHour - startHour + 1) + startHour;
        int minute = random.nextInt(4) * 15;
        return LocalTime.of(hour, minute);
    }

    private String restaurantName() {
        return faker.company().name();
    }

    private Double rating() {
        return BigDecimal.valueOf(random.nextDouble(1, 5.0)).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    private String city() {
        var cities = new String[]{
                "Göteborg",
                "Stockholm",
                "Malmö",
                "Jönköping"
        };
        return cities[random.nextInt(cities.length)];
    }

    private String phone() {
        StringBuilder phone = new StringBuilder();

        phone.append("+467");

        for (int i = 0; i < 8; i++) {
            phone.append(random.nextInt(10));
        }

        return phone.toString();
    }

    /* ----- MISC UTILS ----- */
    private String generateIdentifierByIndex(int index) {
        if (index < 0 || index > 25) {
            throw new IllegalArgumentException("Index must be between 0 and 25");
        }
        char c = (char) ('a' + index);
        return String.valueOf(c).toUpperCase() + c + c;
    }

    private String generateRandomIdentifier() {
        random = new Random();
        return generateIdentifierByIndex(random.nextInt(0,25));
    }

    private String generateRandomLetters(int amount) {
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            int index = random.nextInt(0,25);
            char c = (char) ('A' + index);
            letters.append(c);
        }
        return letters.toString();
    }

    private String generateEmail(String identifier, String domain) {
        return identifier.toLowerCase() + "@" + domain;
    }

    private String generatePhone() {
        random = new Random();
        StringBuilder phone = new StringBuilder();

        phone.append("07");

        for (int i = 0; i < 8; i++) {
            phone.append(random.nextInt(10));
        }

        return phone.toString();
    }

    private String generateAddress(String identifier) {
        random = new Random();
        return identifier.charAt(0) + "'s Street " + random.nextInt(0,100);
    }
    /* --x-- MISC UTILS --x-- */
}

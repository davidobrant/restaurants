package org.example.restaurants.utils;

import com.github.javafaker.Faker;
import org.example.restaurants.entities.*;

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

    /* ----- Restaurant ----- */
    public List<Restaurant> generateRestaurants(int amount) {
        var list = new ArrayList<Restaurant>();
        for (int i = 0; i < amount; i++) {
            list.add(generateRestaurant());
        }
        list.add(tayyabDominos());
        return list;
    }

    private Restaurant tayyabDominos() {
        var restaurant = new Restaurant();
        restaurant.setName("Tayyab's Awesome Dominos");
        restaurant.setDescription(faker.chuckNorris().fact());
        restaurant.setRating(5.0);
        restaurant.setCity("EVERYWHERE");
        restaurant.setStreet(faker.address().streetAddress());
        restaurant.setZip(zip());
        restaurant.setPhone(phone());
        restaurant.setEmail("tayyab@dominos.com");
        restaurant.setOpeningHours(generateOpeningHours(restaurant));
        return restaurant;
    }

    private Restaurant generateRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName());
        restaurant.setDescription(faker.chuckNorris().fact());
        restaurant.setRating(rating());
        restaurant.setCity(city());
        restaurant.setStreet(faker.address().streetAddress());
        restaurant.setZip(zip());
        restaurant.setPhone(phone());
        restaurant.setEmail(faker.internet().emailAddress());
        restaurant.setOpeningHours(generateOpeningHours(restaurant));
        restaurant.setMenu(generateMenu());
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
        String[] suffix = {
                "Restaurant",
                "Pub",
                "Pub & Grill",
                "Gatukök",
                "Restaurant & Pub",
                "Nightclub",
                "Bed & Breakfast",
                "Diner",
                "Pizzeria",
                "Bar",
                "Bar & Grill",
                "Hotel & Bar",
        };

        var name = generateRandomIdentifier() + " " + generateRandomIdentifier();
        return name + "'s " + suffix[random.nextInt(suffix.length)];
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

    private String zip() {
        var zip = faker.address().zipCode();
        return zip.substring(0,3) + " " + zip.substring(3);
    }

    private String phone() {
        StringBuilder phone = new StringBuilder();

        phone.append("+46 (0)7");

        for (int i = 0; i < 8; i++) {
            if (i == 2) {
                phone.append(" - ");
            }
            if (i == 5) {
                phone.append(" ");
            }
            phone.append(random.nextInt(10));
        }

        return phone.toString();
    }


    /* --x-- Restaurant --x-- */
    /* ----- Menu ----- */

    private Menu generateMenu() {
        var menu = new Menu();

        List<MenuSection> sections = new ArrayList<>();

        sections.add(generateMenuSection("Starters"));
        sections.add(generateMenuSection("Main Dishes"));
        sections.add(generateMenuSection("Desserts"));
        sections.add(generateMenuSection("Drinks"));

        menu.setSections(sections);

        return menu;
    }

    private MenuSection generateMenuSection(String sectionName) {
        var section = new MenuSection();
        section.setName(sectionName);

        List<MenuItem> items = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (sectionName.equalsIgnoreCase("Drinks")) {
               items.add(generateMenuItemDrink());
               continue;
            }
            items.add(generateMenuItem());
        }

        section.setItems(items);

        return section;
    }

    private MenuItem generateMenuItem() {
        var item = new MenuItem();
        item.setName(faker.food().dish());
        item.setDescription(faker.lorem().sentence(10));
        item.setPrice(BigDecimal.valueOf(random.nextDouble(5, 30)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return item;
    }

    private MenuItem generateMenuItemDrink() {
        var item = new MenuItem();
        item.setName(drink());
        item.setDescription(null);
        item.setPrice(BigDecimal.valueOf(random.nextDouble(5, 15)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return item;
    }

    private String drink() {
        String[] drinks = {
                "Coca Cola",
                "Fanta",
                "Sprite",
                "Lager",
                "Ale",
                "White wine",
                "Red wine",
                "Coffee",
                "Tea"
        };

        return drinks[random.nextInt(drinks.length)];
    }

    /* --x-- Menu --x-- */
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

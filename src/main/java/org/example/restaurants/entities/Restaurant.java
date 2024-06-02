package org.example.restaurants.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String city;
    private String street;
    private String zip;
    private String phone;
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<OpeningHour> openingHours;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu;

    public boolean isOpen() {
        if (openingHours == null) {
            return false;
        }

        DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();

        OpeningHour todaysOpeningHour = openingHours.stream()
                .filter(hour -> hour.getDayOfWeek().equalsIgnoreCase(currentDayOfWeek.toString()))
                .findFirst()
                .orElse(null);

        if (todaysOpeningHour != null) {
            LocalTime currentTime = LocalTime.now();
            LocalTime openTime = todaysOpeningHour.getOpenTime();
            LocalTime closeTime = todaysOpeningHour.getCloseTime();

            if (openTime != null && closeTime != null) {
                return currentTime.isAfter(openTime) && currentTime.isBefore(closeTime);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getIsOpen() {
        return isOpen();
    }

    public OpeningHour getTodaysOpeningHour() {
        DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();

        return openingHours.stream()
                .filter(hour -> hour.getDayOfWeek().equalsIgnoreCase(currentDayOfWeek.toString()))
                .findFirst()
                .orElse(null);
    }
}

package org.example.restaurants.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean isOpen;

    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu;

}

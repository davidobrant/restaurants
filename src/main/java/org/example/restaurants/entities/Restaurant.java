package org.example.restaurants.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    private Integer rating;
    private String city;
    private String street;
    private Integer zip;
    private String phone;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu;

}

package com.anshumanprajapati.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private Boolean available;

    private String vehicleId;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;

}

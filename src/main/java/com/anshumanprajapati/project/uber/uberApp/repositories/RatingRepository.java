package com.anshumanprajapati.project.uber.uberApp.repositories;

import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.Rating;
import com.anshumanprajapati.project.uber.uberApp.entities.Ride;
import com.anshumanprajapati.project.uber.uberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByRide(Ride ride);

    Collection<Rating> findByDriver(Driver driver);

    Collection<Rating> findByRider(Rider rider);
    // Additional query methods can be defined here if needed
}

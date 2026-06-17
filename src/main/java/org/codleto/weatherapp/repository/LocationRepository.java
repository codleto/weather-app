package org.codleto.weatherapp.repository;

import org.codleto.weatherapp.entity.Location;
import org.codleto.weatherapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByUser(User user);

    Location findByIdAndUser(Long id, User user);
}

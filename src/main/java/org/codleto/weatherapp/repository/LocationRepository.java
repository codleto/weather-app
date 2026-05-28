package org.codleto.weatherapp.repository;

import org.codleto.weatherapp.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}

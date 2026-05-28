package org.codleto.weatherapp.repository;

import org.codleto.weatherapp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionsRepository extends JpaRepository<Session, UUID> {
}

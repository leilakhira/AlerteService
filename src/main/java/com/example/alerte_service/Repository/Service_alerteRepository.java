package com.example.alerte_service.Repository;

import com.example.alerte_service.entite.FraudNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Service_alerteRepository extends JpaRepository<FraudNotification,Long> {
}

package org.example.oms.service.infra.repository;

import org.example.oms.model.Fill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FillRepository extends JpaRepository<Fill, Long> {
}
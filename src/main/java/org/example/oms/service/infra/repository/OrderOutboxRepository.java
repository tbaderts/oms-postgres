package org.example.oms.service.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.oms.model.OrderOutbox;

@Repository
public interface OrderOutboxRepository extends JpaRepository<OrderOutbox, Long> {
  
}

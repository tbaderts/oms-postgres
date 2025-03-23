package org.example.oms.service.infra.repository;

import java.util.Optional;

import org.example.common.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);

    Optional<Order> findByRootOrderId(String rootOrderId);
}
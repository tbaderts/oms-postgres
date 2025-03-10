package org.example.oms.service.processing;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.oms.model.Order;
import org.example.oms.model.ProcessingContext;
import org.example.oms.service.infra.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersistenceService {

    private final OrderRepository orderRepository;

    public PersistenceService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void persist(ProcessingContext context) {
        Order savedOrder = orderRepository.save(context.getOrder());
        log.info("Order persisted: {}", savedOrder);
        context.setOrder(savedOrder);
    }
}
package org.example.oms.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.oms.model.OrderEvent;
import org.example.oms.service.infra.repository.OrderEventRepository;

@RestController
@RequestMapping("/api/order-events")
@Transactional(readOnly = true)
public class OrderEventController {

    private final OrderEventRepository orderEventRepository;

    public OrderEventController(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    @GetMapping("/orderId/{orderId}")
    public List<OrderEvent> getOrderEventsByOrderId(@PathVariable String orderId) {
        return orderEventRepository.findByOrderId(orderId);
    }
}

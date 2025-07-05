package org.example.oms.service.processing;

import java.time.LocalDateTime;

import org.example.oms.model.OrderEvent;
import org.example.oms.model.ProcessingContext;
import org.example.oms.service.infra.repository.OrderEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventProcessor {

    private final OrderEventRepository orderEventRepository;

    public EventProcessor(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    @Transactional
    @Observed(name = "oms.event-processor.process-event")
    public void processEvent(ProcessingContext context) {
        OrderEvent orderEvent =
                OrderEvent.builder()
                        .orderId(context.getOrder().getOrderId())
                        .transaction(context.getTransaction())
                        .event(context.getEvent())
                        .timeStamp(LocalDateTime.now())
                        .build();

        OrderEvent savedOrderEvent = orderEventRepository.save(orderEvent);
        log.info("Order event persisted: {}", savedOrderEvent);
        context.setOrderEvent(savedOrderEvent);
        context.getOrder().setTxNr(savedOrderEvent.getId());
    }
}

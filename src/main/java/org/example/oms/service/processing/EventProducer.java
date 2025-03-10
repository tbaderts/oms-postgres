package org.example.oms.service.processing;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.oms.model.OrderMessage;
import org.example.oms.model.OrderOutbox;
import org.example.oms.model.ProcessingContext;
import org.example.oms.model.ProcessingEvent;
import org.example.oms.service.infra.repository.OrderOutboxRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventProducer{

    private final OrderOutboxRepository orderOutboxRepository;
    private final ApplicationEventPublisher eventPublisher;

    public EventProducer(OrderOutboxRepository orderOutboxRepository, ApplicationEventPublisher eventPublisher) {
        this.orderOutboxRepository = orderOutboxRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void produceEvent(ProcessingContext context) {
        OrderOutbox orderOutbox = OrderOutbox.builder().orderMessage(OrderMessage.builder()
                .order(context.getOrder())
                .build()).build();
        OrderOutbox savedOrderOutbox = orderOutboxRepository.save(orderOutbox);
        eventPublisher.publishEvent(ProcessingEvent.builder().orderOutbox(savedOrderOutbox).build());
        log.info("Order outbox persisted: {}", savedOrderOutbox);
    }
}
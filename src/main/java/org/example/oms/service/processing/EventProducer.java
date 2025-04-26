package org.example.oms.service.processing;

import org.example.common.model.Order;
import org.example.oms.model.ProcessingContext;
import org.example.oms.service.infra.repository.OrderOutboxRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventProducer {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final StreamBridge streamBridge;

    public EventProducer(OrderOutboxRepository orderOutboxRepository, ApplicationEventPublisher eventPublisher,
            StreamBridge streamBridge) {
        this.orderOutboxRepository = orderOutboxRepository;
        this.eventPublisher = eventPublisher;
        this.streamBridge = streamBridge;
    }

    @Transactional
    @Observed(name = "oms.event-producer.produce-event")
    public void produceEvent(ProcessingContext context) {
        Message<Order> msg = MessageBuilder.withPayload(context.getOrder()).build();
        // boolean sent = streamBridge.send("order-out-0", msg);
        // if (sent) {
        //     log.info("Message successfully sent: {}", msg);
        // } else {
        //     log.error("Failed to send message for order: {}", context.getOrder());
        // }

        // throw new RuntimeException("Failed to send message for orderId: " +
        // context.getOrder().getOrderId());

        // OrderOutbox orderOutbox =
        // OrderOutbox.builder().orderMessage(OrderMessage.builder()
        // .order(context.getOrder())
        // .build()).build();
        // OrderOutbox savedOrderOutbox = orderOutboxRepository.save(orderOutbox);
        // eventPublisher.publishEvent(ProcessingEvent.builder().orderOutbox(savedOrderOutbox).build());
        // log.info("Order outbox persisted: {}", savedOrderOutbox);
    }
}
package org.example.oms.service.processing;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.common.model.Order;
import org.example.common.model.msg.CancelState;
import org.example.common.model.msg.ExecInst;
import org.example.common.model.msg.HandlInst;
import org.example.common.model.msg.OrdType;
import org.example.common.model.msg.OrderMessage;
import org.example.common.model.msg.Side;
import org.example.common.model.msg.State;
import org.example.common.model.msg.TimeInForce;
import org.example.common.model.msg.PositionEffect;
import org.example.common.model.msg.PriceType;
import org.example.common.model.msg.Tx;
import org.example.oms.model.ProcessingContext;
import org.example.oms.service.infra.repository.OrderOutboxRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventProducer {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;
    private final String topic;

    public EventProducer(OrderOutboxRepository orderOutboxRepository, ApplicationEventPublisher eventPublisher,
            KafkaTemplate<String, OrderMessage> kafkaTemplate, @Value("${kafka.order-topic}") String topic) {
        this.orderOutboxRepository = orderOutboxRepository;
        this.eventPublisher = eventPublisher;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Transactional
    @Observed(name = "oms.event-producer.produce-event")
    public void produceEvent(ProcessingContext context) {
        OrderMessage orderMessage = convertToOrderMessage(context.getOrder());
        ProducerRecord<String, OrderMessage> producerRecord = new ProducerRecord<>(topic, orderMessage);
        CompletableFuture<SendResult<String, OrderMessage>> completableFuture = kafkaTemplate.send(producerRecord);
        log.info("Sending kafka message on topic {}", topic);

        completableFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Kafka message successfully sent: {}", result.getProducerRecord().value());
            } else {
                log.error("Error while sending kafka message: {}", producerRecord, ex);
            }
        });

        // OrderOutbox orderOutbox =
        // OrderOutbox.builder().orderMessage(OrderMessage.builder()
        // .order(context.getOrder())
        // .build()).build();
        // OrderOutbox savedOrderOutbox = orderOutboxRepository.save(orderOutbox);
        // eventPublisher.publishEvent(ProcessingEvent.builder().orderOutbox(savedOrderOutbox).build());
        // log.info("Order outbox persisted: {}", savedOrderOutbox);
    }

    private OrderMessage convertToOrderMessage(Order order) {
        return OrderMessage.newBuilder()
                .setOrderId(order.getOrderId())
                .setRootOrderId(order.getOrderId())
                .setOrderQty(order.getOrderQty())
                .setPrice(order.getPrice())
                .setSide(mapSide(order.getSide()))
                .setTimeInForce(mapTimeInForce(order.getTimeInForce()))
                .setTransactTime(Instant.now())
                .setAccount(order.getAccount())
                .setSymbol(order.getSymbol())
                .setCancelState(CancelState.PCXL)
                .setState(State.NEW)
                .setOrdType(OrdType.MARKET)
                .setClOrdId(order.getClOrdId())
                .setTx(Tx.NO)
                .setCashOrderQty(order.getOrderQty())
                .setExDestination("X")
                .setExecInst(ExecInst.NO_CROSS)
                .setExpireTime(Instant.now())
                .setHandlInst(HandlInst.MANUAL)
                .setMaturityMonthYear("202510")
                .setOrigClOrdId(order.getClOrdId())
                .setParentOrderId(order.getOrderId())
                .setPositionEffect(PositionEffect.OPEN)
                .setPutOrCall(0)
                .setPriceType(PriceType.PER_UNIT)
                //.setSucurityDesc("")
                .build();
    }

    private Side mapSide(org.example.common.model.Side side) {
        return switch (side) {
            case BUY -> Side.BUY;
            case SELL -> Side.SELL;
            default -> throw new IllegalArgumentException(String.format("Unknown time in force: %s", side));
        };
    }

    private TimeInForce mapTimeInForce(org.example.common.model.TimeInForce timeInForce) {
        return switch (timeInForce) {
            case DAY -> TimeInForce.DAY;
            case GOOD_TILL_CANCEL -> TimeInForce.GOOD_TILL_CANCEL;
            case GOOD_TILL_CROSSING -> TimeInForce.GOOD_TILL_CROSSING;
            case AT_THE_OPENING -> TimeInForce.AT_THE_OPENING;
            case IMMEDIATE_OR_CANCEL -> TimeInForce.IMMEDIATE_OR_CANCEL;
            case FILL_OR_KILL -> TimeInForce.FILL_OR_KILL;
            case GOOD_TILL_DATE -> TimeInForce.GOOD_TILL_DATE;
            case AT_THE_CLOSE -> TimeInForce.AT_THE_CLOSE;
            default -> throw new IllegalArgumentException(String.format("Unknown time in force: %s", timeInForce));
        };
    }
}
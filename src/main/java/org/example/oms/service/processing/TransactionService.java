package org.example.oms.service.processing;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.common.model.Order;
import org.example.common.model.State;
import org.example.common.model.tx.AcceptOrderTx;
import org.example.common.model.tx.NewOrderTx;
import org.example.common.model.tx.Transaction;
import org.example.common.model.tx.Tx;
import org.example.oms.model.Event;
import org.example.oms.model.ProcessingContext;
import org.example.oms.service.business.mapper.NewOrderTxToOrderMapper;
import org.example.oms.service.infra.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

    private final NewOrderTxToOrderMapper newOrderTxToOrderMapper;
    private final OrderRepository orderRepository;

    public TransactionService(NewOrderTxToOrderMapper newOrderTxToOrderMapper, OrderRepository orderRepository) {
        this.newOrderTxToOrderMapper = newOrderTxToOrderMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void executeTransaction(ProcessingContext context) {
        Transaction transaction = context.getTransaction();

        if (transaction instanceof NewOrderTx newOrderTx) {
            Order order = newOrderTxToOrderMapper.map(newOrderTx);
            order.setTx(Tx.NO);
            context.setNewState(State.UNACK);
            log.info("Order created: {}", order);
            context.setOrder(order);
            context.setEvent(Event.NEW_ORDER);
        } else if (transaction instanceof AcceptOrderTx acceptOrderTx) {
            Optional<Order> orderOptional = orderRepository.findByOrderId(acceptOrderTx.getOrderId());
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                order.setTx(Tx.AO);
                context.setNewState(State.LIVE);
                log.info("Order accepted: {}", order);
                context.setOrder(order);
                context.setEvent(Event.ACK);
            } else {
                String errorMessage = "Order not found: " + acceptOrderTx.getOrderId();
                log.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

        } else {
            throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getClass().getName());
        }
    }
}
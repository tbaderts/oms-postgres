package org.example.oms.service.mapper;

import org.example.common.model.Order;
import org.example.oms.model.msg.OrderMessageWrapper.OrderMessage;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProtobufMapper {

    public OrderMessage mapOrderMessageFromOrder(Order order) {
        log.info("Mapping order to order message: {}", order);
        return OrderMessage.newBuilder()
        .setOrderId(order.getOrderId())
        .build();
    }

}

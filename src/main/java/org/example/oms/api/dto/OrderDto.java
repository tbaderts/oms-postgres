package org.example.oms.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "order", collectionRelation = "orders")
public record OrderDto(
        String orderId,
        String rootOrderId,
        String parentOrderId,
        String symbol,
        String side,
        String state,
        String ordType,
        BigDecimal price,
        BigDecimal orderQty,
        LocalDateTime transactTime) {}

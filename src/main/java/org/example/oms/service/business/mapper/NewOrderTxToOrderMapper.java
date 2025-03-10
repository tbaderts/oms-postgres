package org.example.oms.service.business.mapper;

import org.example.oms.model.Order;
import org.example.oms.model.State;
import org.example.oms.model.tx.NewOrderTx;

import io.github.jaspeen.ulid.ULID;

import org.springframework.stereotype.Component;

@Component
public class NewOrderTxToOrderMapper {

    public Order map(NewOrderTx newOrderTx) {
        if (newOrderTx == null) {
            return null;
        }

        return Order.builder()
                .orderId(ULID.random().toString())
                .parentOrderId(newOrderTx.getParentOrderId())
                .sessionId(newOrderTx.getSessionId())
                .clOrdId(newOrderTx.getClOrdId())
                .sendingTime(newOrderTx.getSendingTime())
                .account(newOrderTx.getAccount())
                .execInst(newOrderTx.getExecInst())
                .handlInst(newOrderTx.getHandlInst())
                .securityIDSource(newOrderTx.getSecurityIDSource())
                .orderQty(newOrderTx.getOrderQty())
                .cashOrderQty(newOrderTx.getCashOrderQty())
                .positionEffect(newOrderTx.getPositionEffect())
                .securityDesc(newOrderTx.getSecurityDesc())
                .securityType(newOrderTx.getSecurityType())
                .maturityMonthYear(newOrderTx.getMaturityMonthYear())
                .strikePrice(newOrderTx.getStrikePrice())
                .priceType(newOrderTx.getPriceType())
                .putOrCall(newOrderTx.getPutOrCall())
                .underlyingSecurityType(newOrderTx.getUnderlyingSecurityType())
                .ordType(newOrderTx.getOrdType())
                .price(newOrderTx.getPrice())
                .stopPx(newOrderTx.getStopPx())
                .securityId(newOrderTx.getSecurityId())
                .side(newOrderTx.getSide())
                .symbol(newOrderTx.getSymbol())
                .timeInForce(newOrderTx.getTimeInForce())
                .transactTime(newOrderTx.getTransactTime())
                .exDestination(newOrderTx.getExDestination())
                .settlCurrency(newOrderTx.getSettlCurrency())
                .expireTime(newOrderTx.getExpireTime())
                .securityExchange(newOrderTx.getSecurityExchange())
                .text(newOrderTx.getText())
                .state(State.NEW)
                .build();
    }
}
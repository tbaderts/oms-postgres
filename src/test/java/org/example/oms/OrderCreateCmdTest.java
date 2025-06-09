package org.example.oms;

import org.example.common.model.cmd.Order;
import org.example.common.model.cmd.Order.SideEnum;
import org.example.common.model.cmd.Order.TimeInForceEnum;
import org.example.common.model.cmd.OrderCreateCmd;
import org.example.common.util.JsonHelper;
import org.junit.jupiter.api.Test;

public class OrderCreateCmdTest {

    @Test
    public void testOrderCreateCmd() {
        OrderCreateCmd orderCreateCmd = OrderCreateCmd.builder()
                .version("1.0")
                .order(Order.builder()
                        .symbol("AAPL")
                        .side(SideEnum.BUY)
                        .quantity(new java.math.BigDecimal("100.00"))
                        .price(new java.math.BigDecimal("50.00"))
                        .timeInForce(TimeInForceEnum.GTC)
                        .build())
                .build();
        System.out.println(JsonHelper.toJson(orderCreateCmd));
    }
}

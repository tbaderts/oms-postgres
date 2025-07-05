package org.example.oms.api;

import org.example.common.api.ExecuteApi;
import org.example.common.model.Order;
import org.example.common.model.cmd.Command;
import org.example.common.model.cmd.CommandResult;
import org.example.common.model.cmd.CommandStatus;
import org.example.common.model.cmd.OrderCreateCmd;
import org.example.common.model.mapper.OrderMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Transactional
public class CommandController implements ExecuteApi {

    private final OrderMapper orderMapper;

    public CommandController(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    @Operation(
            summary = "Execute a command",
            requestBody =
                    @RequestBody(
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                @ExampleObject(
                                                        name = "OrderCreateCmd",
                                                        summary = "Order create command",
                                                        value =
                                                                "{\"type\":\"OrderCreateCmd\",\"version\":\"1.0.0\",\"order\":{\"orderId\":null,\"parentOrderId\":null,\"rootOrderId\":null,\"sessionId\":\"test-session\",\"clOrdId\":\"20250621-test-001\",\"sendingTime\":null,\"account\":\"test-account\",\"origClOrdId\":null,\"execInst\":\"NO_CROSS\",\"handlInst\":\"AUTO\",\"securityIdSource\":\"ISIN\",\"orderQty\":100.00,\"cashOrderQty\":null,\"positionEffect\":null,\"securityDesc\":\"desc\",\"securityType\":null,\"maturityMonthYear\":null,\"strikePrice\":null,\"priceType\":\"PER_UNIT\",\"putOrCall\":null,\"underlyingSecurityType\":null,\"ordType\":\"LIMIT\",\"price\":172.06,\"stopPx\":null,\"securityId\":\"US0378331005\",\"side\":\"BUY\",\"symbol\":\"AAPL\",\"timeInForce\":\"DAY\",\"exDestination\":\"XNAS\",\"settlCurrency\":\"USD\",\"expireTime\":null,\"securityExchange\":\"XNAS\",\"text\":\"Test"
                                                                    + " order\"}}"),
                                                @ExampleObject(
                                                        name = "OrderAcceptCmd",
                                                        summary = "Order accept command",
                                                        value =
                                                                "{\"type\":\"OrderAcceptCmd\",\"version\":\"1.0\",\"orderId\":\"12345\"}")
                                            })))
    public ResponseEntity<CommandResult> executeCommand(@Valid Command command) {
        log.info("Received command: {}", command);
        if (command instanceof OrderCreateCmd orderCreateCmd) {
            Order order = orderMapper.toOrder(orderCreateCmd.getOrder());
            log.info("Mapped OrderCreateCmd to Order entity: {}", order);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommandResult.builder().id("").status(CommandStatus.OK).message("").build());
    }
}

package org.example.oms.api;

import org.example.common.api.ExecuteApi;
import org.example.common.model.cmd.Command;
import org.example.common.model.cmd.CommandResult;
import org.example.common.model.cmd.CommandResult.StatusEnum;
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

    @Override
    @Operation(summary = "Execute a command", requestBody = @RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "OrderCreateCmd", summary = "Order create command", value = "{\"type\":\"OrderCreateCmd\",\"version\":\"1.0\",\"order\":{\"symbol\":\"AAPL\",\"side\":\"buy\",\"price\":50.00,\"quantity\":100.00,\"timeInForce\":\"GTC\"}}"),
            @ExampleObject(name = "OrderAcceptCmd", summary = "Order accept command", value = "{\"type\":\"OrderAcceptCmd\",\"version\":\"1.0\",\"orderId\":\"12345\"}")
    })))
    public ResponseEntity<CommandResult> executeCommand(@Valid Command command) {
        log.info("Received command: {}", command);
        return ResponseEntity.status(HttpStatus.OK).body(CommandResult.builder()
                .id("")
                .status(StatusEnum.OK)
                .message("")
                .build());
    }
}

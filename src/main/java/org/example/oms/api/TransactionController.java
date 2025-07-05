package org.example.oms.api;

import org.example.common.model.tx.Transaction;
import org.example.common.model.tx.TxInfo;
import org.example.common.model.tx.TxState;
import org.example.oms.model.ProcessingContext;
import org.example.oms.service.processing.OrchestrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/tx")
@Slf4j
@Transactional
public class TransactionController {

    private final OrchestrationService orchestrationService;

    public TransactionController(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @PostMapping
    public ResponseEntity<TxInfo> execute(@RequestBody Transaction transaction) {
        log.info("Received transaction: {}", transaction);
        TxInfo txInfo =
                orchestrationService.process(
                        ProcessingContext.builder().transaction(transaction).build());
        return ResponseEntity.status(HttpStatus.CREATED).body(txInfo);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TxInfo> handleException(Exception e) {
        log.error("Error processing transaction", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(TxInfo.builder().txState(TxState.FAIL).message(e.getMessage()).build());
    }
}

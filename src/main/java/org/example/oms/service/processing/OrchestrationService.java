package org.example.oms.service.processing;

import org.example.common.model.tx.TxInfo;
import org.example.common.model.tx.TxState;
import org.example.oms.model.ProcessingContext;
import org.springframework.stereotype.Service;

import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrchestrationService {

    private final TransactionService transactionService;
    private final EventProcessor eventProcessor;
    private final ValidationService validationService;
    private final PersistenceService persistenceService;
    private final EventProducer eventProducer;

    public OrchestrationService(
            TransactionService transactionService,
            EventProcessor eventProcessor,
            ValidationService validationService,
            PersistenceService persistenceService,
            EventProducer eventProducer) {
        this.transactionService = transactionService;
        this.eventProcessor = eventProcessor;
        this.validationService = validationService;
        this.persistenceService = persistenceService;
        this.eventProducer = eventProducer;
    }

    @Transactional
    @Observed(name = "oms.orchestration-service.process")
    public TxInfo process(ProcessingContext context) {
        transactionService.executeTransaction(context);
        eventProcessor.processEvent(context);
        validationService.validate(context);
        persistenceService.persist(context);
        eventProducer.produceEvent(context);

        return TxInfo.builder()
                .message("Processing completed")
                .orderId(context.getOrder().getOrderId())
                .txState(TxState.OK)
                .build();
    }
}

package org.example.oms.service.processing;

import java.util.Optional;

import org.example.common.model.Order;
import org.example.common.model.State;
import org.example.oms.model.ProcessingContext;
import org.example.oms.service.business.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationService {

    @Transactional
    @Observed(name = "oms.event-validation.service-validate")
    public void validate(ProcessingContext context) {
        if (isValidStateTransition(context.getOrder(), context.getNewState())) {
            context.getOrder().setState(context.getNewState());
            log.info("Validation successful: {}", context);
        } else {
            log.error("Validation failed: {}", context);
            throw new IllegalArgumentException("Validation failed");
        }
    }

    private boolean isValidStateTransition(Order order, State newState) {
        State currentState = order.getState();
        Optional<State> transitionedState = StateMachine.transition(currentState, newState);

        if (transitionedState.isPresent()) {
            log.info("Valid state transition from {} to {}", currentState, newState);
            return true;
        } else {
            log.warn("Invalid state transition from {} to {}", currentState, newState);
            return false;
        }
    }
}

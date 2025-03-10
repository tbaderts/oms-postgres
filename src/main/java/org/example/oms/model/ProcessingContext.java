package org.example.oms.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.lang.NonNull;
import org.example.oms.model.tx.Transaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class ProcessingContext {

    @NonNull
    private Transaction transaction;
    private Order order;
    private Fill fill;
    private Quote quote;
    private Event event;
    private OrderEvent orderEvent;
    private State newState;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}

package org.example.common.model.tx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewOrderTx.class, name = "newOrderTx"),
        @JsonSubTypes.Type(value = AcceptOrderTx.class, name = "acceptOrderTx"),
        @JsonSubTypes.Type(value = RejectOrderTx.class, name = "rejectOrderTx")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Transaction {

    private String orderId;
}

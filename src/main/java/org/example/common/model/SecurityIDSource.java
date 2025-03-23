package org.example.common.model;

import lombok.Getter;

@Getter
public enum SecurityIDSource {
    ISIN("4"),
    RIC("5"),
    EXCHANGE_SYMBOL("8"),
    BLOOMBERG_SYMBOL("A");

    private final String fixValue;

    SecurityIDSource(String fixValue) {
        this.fixValue = fixValue;
    }
}

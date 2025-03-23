package org.example.common.model.tx;

import lombok.Getter;

@Getter
public enum Tx {
    NO("NEW_ORDER"),
    AO("ACCEPT_ORDER"),
    RO("REJECT_ORDER");

    private final String value;

    Tx(String value) {
        this.value = value;
    }
}

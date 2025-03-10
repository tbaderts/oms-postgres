package org.example.oms.model.tx;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.oms.model.ExecInst;
import org.example.oms.model.HandlInst;
import org.example.oms.model.OrdType;
import org.example.oms.model.PositionEffect;
import org.example.oms.model.PriceType;
import org.example.oms.model.SecurityIDSource;
import org.example.oms.model.SecurityType;
import org.example.oms.model.Side;
import org.example.oms.model.TimeInForce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@NoArgsConstructor
@Getter
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewOrderTx extends Transaction {

private String sessionId;
    private String parentOrderId;
    private String clOrdId;
    private LocalDateTime sendingTime;
    private String account;
    @Enumerated(EnumType.STRING)
    private ExecInst execInst;
    @Enumerated(EnumType.STRING)
    private HandlInst handlInst;
    private SecurityIDSource securityIDSource;
    private BigDecimal orderQty;
    private BigDecimal cashOrderQty;
    private PositionEffect positionEffect;
    private String securityDesc;
    private SecurityType securityType;
    private String maturityMonthYear;
    private BigDecimal strikePrice;
    private PriceType priceType;
    private Integer putOrCall;
    private String underlyingSecurityType;
    private OrdType ordType;
    private BigDecimal price;
    private BigDecimal stopPx;
    private String securityId;
    private Side side;
    private String symbol;
    private TimeInForce timeInForce;
    private LocalDateTime transactTime;
    private String exDestination;
    private String settlCurrency;
    private LocalDateTime expireTime;
    private String securityExchange;
    private String text;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}

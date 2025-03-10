package org.example.oms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.oms.model.tx.Tx;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "orders")
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_seq", allocationSize = 1)
    private Long id;
    private String orderId;
    private String parentOrderId;
    private String rootOrderId;
    @Enumerated(EnumType.STRING)
    @Setter
    private Tx tx;
    @Setter
    private long txNr;
    private String sessionId;
    private String clOrdId;
    private LocalDateTime sendingTime;
    private String account;
    private String origClOrdId;
    @Enumerated(EnumType.STRING)
    private ExecInst execInst;
    @Enumerated(EnumType.STRING)
    private HandlInst handlInst;
    @Enumerated(EnumType.STRING)
    private SecurityIDSource securityIDSource;
    private BigDecimal orderQty;
    private BigDecimal cashOrderQty;
    @Enumerated(EnumType.STRING)
    private PositionEffect positionEffect;
    private String securityDesc;
    @Enumerated(EnumType.STRING)
    private SecurityType securityType;
    private String maturityMonthYear;
    private BigDecimal strikePrice;
    private PriceType priceType;
    private Integer putOrCall;
    private String underlyingSecurityType;
    @Enumerated(EnumType.STRING)
    private OrdType ordType;
    private BigDecimal price;
    private BigDecimal stopPx;
    private String securityId;
    @Enumerated(EnumType.STRING)
    private Side side;
    private String symbol;
    @Enumerated(EnumType.STRING)
    private TimeInForce timeInForce;
    private LocalDateTime transactTime;
    private String exDestination;
    private String settlCurrency;
    private LocalDateTime expireTime;
    private String securityExchange;
    private String text;
    private LocalDateTime tifTimestamp;
    @Setter
    private State state;
    @Setter
    private CancelState cancelState;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
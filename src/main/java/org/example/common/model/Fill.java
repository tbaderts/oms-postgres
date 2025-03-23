package org.example.common.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "fills")
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class Fill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fill_sequence")
    @SequenceGenerator(name = "fill_sequence", sequenceName = "fill_seq", allocationSize = 1)
    private Long id;
    private String orderId;
    private String fillId;
    private BigDecimal avgPx;
    private BigDecimal cumQty;
    private String execID;
    private String lastCapacity;
    private String lastMkt;
    private BigDecimal lastPx;
    private BigDecimal lastQty;
    private LocalDateTime transactTime;
    private String execType;
    private BigDecimal leavesQty;
    private BigDecimal dayOrderQty;
    private BigDecimal dayCumQty;
    private BigDecimal dayAvgPx;
    private String secondaryExecID;
    private LocalDateTime creationDate;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
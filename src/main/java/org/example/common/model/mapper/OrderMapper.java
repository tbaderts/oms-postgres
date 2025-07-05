package org.example.common.model.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.example.common.model.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "sendingTime", source = "sendingTime", qualifiedByName = "offsetToLocal")
    @Mapping(target = "expireTime", source = "expireTime", qualifiedByName = "offsetToLocal")
    Order toOrder(org.example.common.model.cmd.Order cmdOrder);

    @Mapping(target = "sendingTime", source = "sendingTime", qualifiedByName = "localToOffset")
    @Mapping(target = "expireTime", source = "expireTime", qualifiedByName = "localToOffset")
    org.example.common.model.cmd.Order toCmdOrder(Order order);

    @Named("offsetToLocal")
    public static LocalDateTime offsetToLocal(OffsetDateTime odt) {
        return odt == null ? null : odt.toLocalDateTime();
    }

    @Named("localToOffset")
    public static OffsetDateTime localToOffset(LocalDateTime ldt) {
        return ldt == null ? null : ldt.atOffset(OffsetDateTime.now().getOffset());
    }
}

package org.example.oms.config;

import java.util.function.Consumer;

import org.example.common.model.tx.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class OmsConfig {

    @Bean
    public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
        return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
    }

    @Bean
    public Consumer<Message<Transaction>> consumer() {
        return msg -> {
            log.info("Received message: {}", msg);
        };
    }
}

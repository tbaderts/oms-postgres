package org.example.oms.config;

import org.example.common.model.msg.CommandMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topics = "${kafka.ocommand-topic}", containerFactory = "kafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Message<CommandMessage> message) {
        log.info("New message: {}", message.getPayload());
    }
}

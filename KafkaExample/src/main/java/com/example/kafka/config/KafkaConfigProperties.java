package com.example.kafka.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConfigProperties {
    private String bootstrapServers;
    private String groupId;
}

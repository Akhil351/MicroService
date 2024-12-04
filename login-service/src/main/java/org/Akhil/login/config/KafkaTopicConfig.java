package org.Akhil.login.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Value("${spring.kafka.topic1.name}")
    private String topic1;

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(topicName).partitions(3).build();
    }
    @Bean
    public NewTopic topic1(){
        return TopicBuilder.name(topic1).partitions(3).build();
    }
}

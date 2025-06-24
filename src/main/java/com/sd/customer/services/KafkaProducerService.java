package com.sd.customer.services;

import com.sd.customer.models.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
        private static final Logger LOGGER = LogManager.getLogger(CustomerService.class);
        private KafkaTemplate<String, Customer> kafkaTemplate;

        @Autowired
        public KafkaProducerService(KafkaTemplate<String, Customer> kafkaTemplate) {
                this.kafkaTemplate = kafkaTemplate;
        }

        @Async
        public void sendMessageAsync(String topic, List<Customer> customers) {
                LOGGER.info("[KafkaProducerService] Sending a batch of {} users to kafka to topic {}", customers.size(), topic);
                for(Customer customer : customers) {
                        sendWithRetry(topic, customer, 5, 500);
                }
        }


        private void sendWithRetry(String topic, Customer customer, int maxAttempts, long backoffMillis) {
                sendWithRetry(topic, customer, maxAttempts, backoffMillis, 1);
        }

//        Need to do this way, as kafka is async. Don't want to make sync and block
        private void sendWithRetry(String topic, Customer customer, int maxAttempts, long backoffMillis, int attempt) {
                CompletableFuture<SendResult<String, Customer>> future = kafkaTemplate.send(topic, customer);
                future.whenComplete((result, ex) -> {
                        if (ex != null) {
                                if (attempt < maxAttempts) {
                                        try {
                                                Thread.sleep(backoffMillis);
                                        } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                        }
                                        sendWithRetry(topic, customer, maxAttempts, backoffMillis * 2, attempt + 1);
                                } else {
                                        LOGGER.error("Failed to send after " + attempt + " attempts: " + ex.getMessage());
                                }
                        }
                });
        }
}

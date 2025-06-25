package com.sd.customer.constants;

import java.time.LocalDateTime;
import java.util.Arrays;

public final class Constants {

    private Constants() {}

    public static final class CustomerRouteConstants {
        public static final String ROUTE = "/customers";
        private CustomerRouteConstants() {}
    }

    public static final class KafkaConstants {
        public static final String CREATE_CUSTOMER_TOPIC = "customer-created-topic";
        private KafkaConstants() {}
    }
}

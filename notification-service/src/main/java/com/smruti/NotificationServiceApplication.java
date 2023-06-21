package com.smruti;

import com.smruti.event.OrderPlacedEvent;
import io.micrometer.observation.Observation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Collections;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceApplication {

    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(NotificationServiceApplication.class);
        //SpringApplication.run(NotificationServiceApplication.class,args);
        app.setDefaultProperties(Collections.singletonMap("server.port", "0"));
        app.run(args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", orderPlacedEvent);
            log.info("TraceId- {}, Received Notification for Order - {}", this.tracer.currentSpan().context().traceId(),
                    orderPlacedEvent.getOrderNumber());
        });
        // send out an email notification
    }

}

package org.apereo.openlrw.security.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Component
public class Indicator implements HealthIndicator {

    public final static String SERVER_UP = "UP";
    public final static String SERVER_DOWN = "DOWN";
    public final static String SERVER_MAINTENANCE = "MAINTENANCE";

    private String status;

    public Indicator() {
        status = SERVER_UP;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public Health health() {
        return Health.up()
                .withDetail("indicator", status)
                .build();
    }

}

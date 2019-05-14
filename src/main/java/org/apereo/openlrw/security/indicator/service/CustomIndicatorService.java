package org.apereo.openlrw.security.indicator.service;

import org.apereo.openlrw.security.indicator.Indicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Service
public class CustomIndicatorService {

    private static Logger logger = LoggerFactory.getLogger(CustomIndicatorService.class);

    private final Indicator indicator;

    @Autowired
    public CustomIndicatorService(Indicator indicator) {
        this.indicator = indicator;
    }

    /**
     * Update the status of the indicator
     *
     * @param status
     * @return
     */
    public boolean update(String status) {
        if (status.equals(Indicator.SERVER_UP) || status.equals(Indicator.SERVER_DOWN) || status.equals(Indicator.SERVER_MAINTENANCE)) {
            this.indicator.setStatus(status);
            return true;
        }

        return false;
    }

}

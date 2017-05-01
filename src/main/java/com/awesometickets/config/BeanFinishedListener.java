package com.awesometickets.config;

import com.awesometickets.web.controller.response.ErrorStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * Listener for beans construction.
 */
@Component
public class BeanFinishedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(BeanFinishedListener.class);

    /**
     * Called when all beans are constructed.
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            LOG.info("Beans constructed.");
            ErrorStatus.init();
        }
    }
}

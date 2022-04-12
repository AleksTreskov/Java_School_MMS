package edu.aleksandrTreskov.mms.service;

import com.twilio.Twilio;
import edu.aleksandrTreskov.mms.config.TwilioConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Service for SMS messaging
 */
@Configuration
public class TwilioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioService.class);
    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(),
                twilioConfig.getAuthToken());
        LOGGER.info("Twilio initialized with account sid {}",twilioConfig.getAccountSid());
    }
}

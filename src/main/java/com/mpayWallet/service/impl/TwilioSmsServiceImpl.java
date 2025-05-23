package com.mpayWallet.service.impl;

import com.mpayWallet.dto.TwilioConfig;
import com.mpayWallet.exception.SmsServiceException;
import com.mpayWallet.service.SmsService;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;


@Slf4j
@Service
@RequiredArgsConstructor
public class TwilioSmsServiceImpl implements SmsService {

    private final TwilioConfig twilioConfig;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Override
    public void sendSms(String to, String message) {
        try {
            Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioConfig.getFromNumber()),
                message
            ).create();
            log.info("SMS sent successfully to: {}", to);
        } catch (ApiException e) {
            log.error("Failed to send SMS to {}: {}", to, e.getMessage());
            throw new SmsServiceException("Failed to send SMS to " + to, e);
        }
    }
}
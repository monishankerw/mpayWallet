package com.mpayWallet.service.impl;

import com.mpayWallet.dto.EmailConfig;
import com.mpayWallet.exception.EmailServiceException;
import com.mpayWallet.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;

    @Override
    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
            helper.setFrom(emailConfig.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (MailException | MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new EmailServiceException("Failed to send email to " + to, e);
        }
    }
}
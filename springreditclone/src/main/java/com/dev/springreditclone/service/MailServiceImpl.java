package com.dev.springreditclone.service;

import com.dev.springreditclone.exception.SpringReditException;
import com.dev.springreditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{
    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSenderImpl mailSender;

    @Async
     public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparatory = mimeMessage -> {
            MimeMessageHelper messageHelper =  new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("taisiq@email.com");
            messageHelper.setTo(notificationEmail.getReceipt());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparatory);
            log.info("Activation email send!!");
        }catch (MailException e){
            log.error("Exception occurs when sending email.",e);
            throw new SpringReditException("Exception occur when sending email to" + notificationEmail.getReceipt());

        }
    }
}

package edu.aleksandrTreskov.mms.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.aleksandrTreskov.mms.config.TwilioConfig;
import edu.aleksandrTreskov.mms.exception.WrongActivationCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

@RequiredArgsConstructor
@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TwilioConfig twilioConfig;


    private int activationCode;


    public void sendSimpleMessage(String toAddress, String subject, String message) {
        if (toAddress.startsWith("\""))
            toAddress = toAddress.substring(1, toAddress.length() - 1);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Eshop@gmail.com");
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        emailSender.send(mimeMessage);
    }

    /**
     * Sends SMS message through Twilio for password recovery
     *
     * @param number
     */
    public void sendSMS(String number) {
        getCode();
        System.out.println(activationCode);
        Message.creator(new PhoneNumber(String.format("+%s", number.substring(1, number.length() - 1))), new PhoneNumber(String.format("%s", twilioConfig.getTrialNumber())),
                String.format("Your code for password recovery: %d. Don't tell it anyone.", activationCode)).create();
    }

    /**
     * Generates code for sms or mail message.
     *
     * @return
     */
    public int getCode() {
        int a = 999;
        int b = 9999;
        activationCode = a + (int) (Math.random() * ((b - a) + 1));
        System.out.println(activationCode);
        return activationCode;
    }

    public void codeIsVerified(int code) {
        if (activationCode != code)
            throw new WrongActivationCodeException("Code is not correct");
    }
}

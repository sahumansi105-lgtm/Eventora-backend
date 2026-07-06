package com.pronight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("sahumansi105@gmail.com");
        message.setTo(toEmail);
        message.setSubject("OTP Verification - Pro Night");
        message.setText(
                "Your OTP is : " + otp +
                "\n\nDo not share it with anyone."
        );

        mailSender.send(message);
    }

    public void sendEmail(String sendTo, String subject, String msg) {

        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setTo(sendTo);
        sm.setSubject(subject);
        sm.setText(msg);
        sm.setFrom("sahumansi105@gmail.com");

        mailSender.send(sm);
    }
    
    public void sendBookingConfirmedMail(
            String email,
            String name,
            String eventName)
    {

        String subject = "Booking Confirmed";

        String body =
                "Hello " + name + ",\n\n" +

                "Your booking for " +

                eventName +

                " has been CONFIRMED.\n\n" +

                "Thank you for choosing Eventroa.";

        sendEmail(email, subject, body);

    }
    
    public void sendBookingCancelledMail(
            String email,
            String name,
            String eventName)
    {

        String subject = "Booking Cancelled";

        String body =
                "Hello " + name + ",\n\n" +

                "We regret to inform you that your booking for "

                + eventName +

                " has been CANCELLED.\n\n"

                + "For any queries contact support.";

        sendEmail(email, subject, body);

    }
    
    // Payment successful mail
    
    public void sendTicketEmail(
            String to,
            String name,
            String event,
            byte[] pdf
    ) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setFrom("sahumansi105@gmail.com");

        helper.setTo(to);

        helper.setSubject("🎫 Payment Successful - Pro Night");

        helper.setText(

                "Hello " + name +

                "\n\nYour payment was successful."

                + "\n\nYour booking for "

                + event

                + " has been confirmed."

                + "\n\nYour ticket is attached."

                + "\n\nThank you."

        );

        helper.addAttachment(
                "ProNight-Ticket.pdf",
                new ByteArrayResource(pdf)
        );

        mailSender.send(message);
    }
    
    public void sendSimpleEmail(
            String to,
            String subject,
            String message
    ){

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);

        mail.setSubject(subject);

        mail.setText(message);

        mailSender.send(mail);

    }
    
    
}
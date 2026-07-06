package com.pronight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.pronight.entity.ContactEntity;
import com.pronight.repository.ContactRepo;

@Service
public class ContactService {

    @Autowired
    ContactRepo repo;

    @Autowired
    JavaMailSender mailSender;



    public void saveContact(ContactEntity contact){

        repo.save(contact);

        sendMail(contact);

    }


    public void sendMail(ContactEntity contact){

        SimpleMailMessage mail
        = new SimpleMailMessage();

        mail.setTo(contact.getEmail());

        mail.setSubject(
        "Thank You For Contacting PRO NIGHT 🎵"
        );



        mail.setText(

        "Hi "+contact.getFullName()+",\n\n"+

        "Thank you for contacting PRO NIGHT.\n\n"+

        "We have received your message successfully.\n"+

        "Our team will contact you very soon.\n\n"+

        "Stay Tuned For Amazing Events.\n\n"+

        "Regards,\n"+

        "PRO NIGHT Team 🎤"

        );


        mailSender.send(mail);

    }

}
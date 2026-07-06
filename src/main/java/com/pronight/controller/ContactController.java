package com.pronight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pronight.entity.ContactEntity;
import com.pronight.service.ContactService;

@RestController

@RequestMapping("/contact")

@CrossOrigin(origins="*")

public class ContactController {


@Autowired

ContactService contactService;



@PostMapping

public String contact(

@RequestBody ContactEntity contact

){

contactService.saveContact(contact);

return "Message Sent Successfully";

}


}
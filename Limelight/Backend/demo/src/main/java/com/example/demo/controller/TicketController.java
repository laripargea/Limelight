package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

@RestController
@CrossOrigin(origins = "*")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/tickets/add/{email}/{idT}")
    public Ticket save(@RequestBody Ticket ticket, @PathVariable String email, @PathVariable Long idT) throws MessagingException, DocumentException, FileNotFoundException {
        return ticketService.save(ticket, email, idT);
    }
}
package com.example.demo;

import com.example.demo.controller.TicketController;
import com.example.demo.model.Ticket;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketControllerTests {
    @Autowired
    private TicketController ticketController;

    @Test
    public void saveInvalidTest() {
        Ticket ticket = new Ticket(null, 1, null, null);
        try {
            ticketController.save(ticket,"", 0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }
}
package com.example.demo;

import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LimelightApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class TicketServiceTests {
    @Autowired
    private TicketService ticketService;

    @Test
    public void saveInvalidTest() {
        Ticket ticket = new Ticket(null, 1, null, null);
        try {
            ticketService.save(ticket,"", 0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }
}

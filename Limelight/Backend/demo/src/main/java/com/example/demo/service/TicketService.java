package com.example.demo.service;

import com.example.demo.model.Ticket;
import com.example.demo.model.Timetable;
import com.example.demo.model.User;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.TimetableRepository;
import com.example.demo.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Objects;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    private void sendMessage(String to, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Limelight - Ticket Reservation");
        helper.setText(text);
        FileSystemResource file
                = new FileSystemResource(new File("G:/demo/Ticket_Reservation.pdf"));
        helper.addAttachment("Ticket_Reservation.pdf", file);
        emailSender.send(message);
    }

    private void createPdf(String contentTitle, String content) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Ticket_Reservation.pdf"));
        document.open();

        //set font for title of the reservation
        Font font = FontFactory.getFont(FontFactory.COURIER, 36, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph(contentTitle, font);

        //set font for text
        font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph2 = new Paragraph(content, font);

        document.add(paragraph1);
        document.add(paragraph2);
        document.close();
    }

    @Transactional
    public Ticket save(Ticket ticket, String email, Long idTimetable) throws DocumentException, FileNotFoundException, MessagingException {
        //check if the maximum number of reservations has been reached
        int counter = 0;
        for (Ticket tempTicket : ticketRepository.findAll())
            if (Objects.equals(tempTicket.getTimetable().getIdTimetable(), idTimetable))
                counter++;
        if (counter > 150)
            return null;

        User user = userRepository.findUserByEmail(email);
        ticket.setUser(user);
        for (Timetable timetable : timetableRepository.findAll())
            if (Objects.equals(timetable.getIdTimetable(), idTimetable)) {
                ticket.setTimetable(timetable);
                break;
            }

        //form unique code for the reservation based on user and play
        String code = ticket.getTimetable().getDateOfPlay().getMonth() + String.valueOf(ticket.getTimetable().getTimeOfPlay().getMinutes()) + ticket.getTimetable().getDateOfPlay().getDay() + String.valueOf(user.getIdUser());
        String titleContent = "Ticket Reservation - " + ticket.getTimetable().getPlay().getTitle();

        //set content of the reservation
        String name = "";
        if (user.getFirstName() != null && user.getLastName() != null)
            name = user.getFirstName() + " " + user.getLastName();
        else if (user.getFirstName() != null)
            name = user.getFirstName();
        else if (user.getLastName() != null)
            name = user.getLastName();
        else
            name = "-";
        String pdfContent = "\n\n\nUnique code: " + code + "\nName: " + name + "\n\nCongratulations! The reservation for the " + ticket.getTimetable().getPlay().getTitle() + " play on " + ticket.getTimetable().getDateOfPlay() + " at " + ticket.getTimetable().getTimeOfPlay().toString().substring(0, 5) + " was successful. Don't forget to bring this proof when you show up at the theater.\nIf you do not reach the theater, our application comes to your aid with a solution. On " + ticket.getTimetable().getDateOfPlay() + " you can watch " + ticket.getTimetable().getPlay().getTitle() + " online at any time for free on the virtual stage.\n\n\nHave fun and thank you for choosing us!\nLimelight";
        String text = "Thank you for your reservation. Have a great day!\n\nLimelight Team";

        //generate the reservation as pdf
        createPdf(titleContent, pdfContent);

        //send email with the reservation
        sendMessage(ticket.getUser().getEmail(), text);

        return ticketRepository.save(ticket);
    }
}
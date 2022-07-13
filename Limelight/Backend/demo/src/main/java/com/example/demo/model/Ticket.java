package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Transactional
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket", nullable = false)
    private Long idTicket;

    @Column(name = "number_of_ticket")
    private Integer numberOfTicket;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_timetable")
    private Timetable timetable;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
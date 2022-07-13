package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "timetable")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Transactional
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_timetable", nullable = false)
    private Long idTimetable;

    @Column(name = "date_of_play")
    private Date dateOfPlay;

    @Column(name = "time_of_play")
    private Time timeOfPlay;

    @Column
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_play")
    private Play play;

    @JsonIgnore
    @OneToMany(mappedBy = "timetable", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<Ticket> tickets;
}
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "plays")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Transactional
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_play", nullable = false)
    private Long idPlay;

    @Column
    private String title;

    @Column
    private String genre;

    @Column
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "favourites")
    private Set<User> usersFavourites;

    @JsonIgnore
    @ManyToMany(mappedBy = "recommendations")
    private Set<User> usersRecommendations;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "play_actors",
            joinColumns = @JoinColumn(name = "id_play"),
            inverseJoinColumns = @JoinColumn(name = "id_actor"))
    private Set<Actor> actors;

    @JsonIgnore
    @OneToMany(mappedBy = "play", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<Timetable> timetables;

    @Column(nullable = false)
    private Double energy;

    @Column(nullable = false)
    private Double tempo;

    @Column(nullable = false)
    private Double duration;

    @Column(nullable = false)
    private Double scenes;

    @Column
    private String actorCast;

    public double getScore() {
        return (duration + tempo + energy + scenes) / 4.0;
    }
}
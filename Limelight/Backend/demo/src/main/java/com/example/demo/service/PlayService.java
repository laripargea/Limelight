package com.example.demo.service;

import com.example.demo.model.Actor;
import com.example.demo.model.Play;
import com.example.demo.repository.ActorRepository;
import com.example.demo.repository.PlayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayService {
    @Autowired
    private PlayRepository playRepository;

    @Autowired
    private ActorRepository actorRepository;

    public List<Play> findAll() {
        return new ArrayList<>(playRepository.findAll());
    }

    @Transactional
    public Play save(Play play) {
        play.setActorCast(getActorsForPlay(play.getTitle()));
        return playRepository.save(play);
    }

    @Transactional
    public void deleteById(Long id) {
        playRepository.deleteById(id);
    }

    @Transactional
    public Play updateById(Long id, Play play) {
        Play p = playRepository.findPlayByIdPlay(id);
        p.setGenre(play.getGenre());
        p.setTitle(play.getTitle());
        p.setActors(play.getActors());
        return playRepository.save(p);
    }

    public void addActorToPlay(Long idActor, Long idPlay) {
        for (Play play : playRepository.findAll())
            if (Objects.equals(play.getIdPlay(), idPlay)) {
                for (Actor actor : actorRepository.findAll())
                    if (Objects.equals(actor.getIdActor(), idActor)) {
                        Set<Actor> actors = play.getActors();
                        actors.add(actor);
                        play.setActors(actors);
                        play.setActorCast(getActorsForPlay(play.getTitle()));
                        playRepository.save(play);
                        break;
                    }
            }
    }

    public String getActorsForPlay(String title) {
        String actors = " ";
        Play play = playRepository.findPlayByTitle(title);

        //get a string with the list of actors' names
        if (play.getActors() != null)
            if (play.getActors().size() > 0)
                actors = play.getActors().stream().map(actor -> {
                    return actor.getFirstName() + " " + actor.getLastName();
                }).collect(Collectors.joining(", "));

        return actors;
    }
}
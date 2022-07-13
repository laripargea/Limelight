package com.example.demo.service;

import com.example.demo.model.Actor;
import com.example.demo.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> findAll() {
        return new ArrayList<>(actorRepository.findAll());
    }

    @Transactional
    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    @Transactional
    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }

    @Transactional
    public Actor updateById(Long id, Actor actor) {
        Actor a = actorRepository.findActorByIdActor(id);
        a.setFirstName(actor.getFirstName());
        a.setLastName(actor.getLastName());
        return actorRepository.save(a);
    }
}
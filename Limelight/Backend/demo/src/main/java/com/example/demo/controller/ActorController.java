package com.example.demo.controller;

import com.example.demo.model.Actor;
import com.example.demo.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @GetMapping("/actors")
    public List<Actor> findAll() {
        return actorService.findAll();
    }

    @PostMapping("/actors/add")
    public Actor save(@RequestBody Actor actor) {
        return actorService.save(actor);
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PatchMapping("/actors/update/{id}")
    public Actor update(@RequestBody Actor actor, @PathVariable("id") Long id) {
        return actorService.updateById(id, actor);
    }
}
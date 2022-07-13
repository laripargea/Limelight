package com.example.demo.controller;

import com.example.demo.model.Play;
import com.example.demo.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class PlayController {
    @Autowired
    private PlayService playService;

    @GetMapping("/plays")
    public List<Play> findAll() {
        return playService.findAll();
    }

    @PostMapping("/plays/add")
    public Play save(@RequestBody Play play) {
        return playService.save(play);
    }

    @DeleteMapping("/plays/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        playService.deleteById(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PatchMapping("/plays/update/{id}")
    public Play update(@RequestBody Play play, @PathVariable("id") Long id) {
        return playService.updateById(id, play);
    }

    @PostMapping("/plays/actor/{idA}/{idP}")
    public void addActorToPlay(@PathVariable("idA") Long idA, @PathVariable("idP") Long idP) {
        playService.addActorToPlay(idA, idP);
    }

    @GetMapping(value = "/plays/actors/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getActorsForPlay(@PathVariable("title") String title) {
        return "{\"Actors\":\"" + playService.getActorsForPlay(title) + "\"}";
    }
}
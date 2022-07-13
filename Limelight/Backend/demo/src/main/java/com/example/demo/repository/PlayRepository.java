package com.example.demo.repository;

import com.example.demo.model.Play;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {
    Play findPlayByIdPlay(@NotNull Long idPlay);

    Play findPlayByTitle(@NotNull String title);
}
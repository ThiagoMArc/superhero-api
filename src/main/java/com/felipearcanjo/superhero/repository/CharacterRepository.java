package com.felipearcanjo.superhero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.felipearcanjo.superhero.model.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character,Long> {
    Character findByName(String name);
}

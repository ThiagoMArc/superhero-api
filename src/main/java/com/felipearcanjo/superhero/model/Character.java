package com.felipearcanjo.superhero.model;

import com.felipearcanjo.superhero.dto.CharacterDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "character")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String birthName;
    private String city;
    private List<String> habilities;

    public static Character convert(CharacterDTO characterDTO){
        Character character = new Character();
        character.setName(characterDTO.getName());
        character.setBirthName(characterDTO.getBirthName());
        character.setCity(characterDTO.getCity());
        character.setHabilities(characterDTO.getHabilities());

        return character;
    }

}

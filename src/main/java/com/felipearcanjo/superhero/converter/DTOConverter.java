package com.felipearcanjo.superhero.converter;

import com.felipearcanjo.superhero.dto.CharacterDTO;
import com.felipearcanjo.superhero.model.Character;

public class DTOConverter {

    public static CharacterDTO convert(Character character){
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setBirthName(character.getBirthName());
        characterDTO.setCity(character.getCity());
        characterDTO.setHabilities(character.getHabilities());

        return characterDTO;
    }
}

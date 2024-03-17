package com.felipearcanjo.superhero.service;

import com.felipearcanjo.superhero.converter.DTOConverter;
import com.felipearcanjo.superhero.dto.CharacterDTO;
import com.felipearcanjo.superhero.exception.custom.CharacterAlreadyExistsException;
import com.felipearcanjo.superhero.exception.custom.CharacterNotFoundException;
import com.felipearcanjo.superhero.model.Character;
import com.felipearcanjo.superhero.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    public List<CharacterDTO> getAll(){
        List<Character> characters = characterRepository.findAll();

        return characters.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public Page<CharacterDTO> getAllPaged(Pageable page){
        Page<Character> characters = characterRepository.findAll(page);
        return characters.map(DTOConverter::convert);
    }

    public CharacterDTO getById(long id){
        Character character = characterRepository.findById(id).orElseThrow(CharacterNotFoundException::new);
        return DTOConverter.convert(character);
    }

    public CharacterDTO save(CharacterDTO characterDTO){
        Character characterDb = characterRepository.findByName(characterDTO.getName());

        if(characterDb != null && 
           characterDTO.getBirthName().toLowerCase().equals(characterDb.getBirthName().toLowerCase()))
            throw new CharacterAlreadyExistsException();

        Character character = characterRepository.save(Character.convert(characterDTO));
        return DTOConverter.convert(character);
    }

    public void delete(long characterId){
        Character character = characterRepository.findById(characterId)
                                                 .orElseThrow(CharacterNotFoundException::new);
        characterRepository.delete(character);
    }

    public CharacterDTO getByName(String characterName){
        Character character = characterRepository.findByName(characterName);

        if(character == null)
            throw new CharacterNotFoundException();

        return DTOConverter.convert(character);
    }

    public CharacterDTO editCharacter(long characterId, CharacterDTO characterDTO){
        Character character = characterRepository.findById(characterId).orElseThrow(CharacterNotFoundException::new);

        if(characterDTO.getName() != null && !character.getName().equals(characterDTO.getName()))
            character.setName(characterDTO.getName());
        if(characterDTO.getBirthName() != null && !character.getBirthName().equals(characterDTO.getBirthName()))
            character.setBirthName(characterDTO.getBirthName());
        if(characterDTO.getCity() != null && !character.getCity().equals(characterDTO.getCity()))
            character.setCity(characterDTO.getCity());
        if(characterDTO.getHabilities() != null && !characterDTO.getHabilities().isEmpty() && !character.getHabilities().equals(characterDTO.getHabilities()))
            character.setHabilities(characterDTO.getHabilities());

        character = characterRepository.save(character);
        return DTOConverter.convert(character);
    }
}

package com.felipearcanjo.superhero.service;

import com.felipearcanjo.superhero.converter.DTOConverter;
import com.felipearcanjo.superhero.dto.CharacterDTO;
import com.felipearcanjo.superhero.exception.custom.CharacterNotFoundException;
import com.felipearcanjo.superhero.model.Character;
import com.felipearcanjo.superhero.repository.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTests {

    @InjectMocks
    private CharacterService characterService;

    @Mock
    private CharacterRepository characterRepository;

    @Test
    public void getAllShouldReturnAllCharacters(){
        //Arrange
        List<Character> characters = new ArrayList<>();
        characters.add(getCharacter(1L, "Batman", "Bruce Wayne", "Gotham", List.of("Intelligence", "Martial Arts", "Strategy")));
        characters.add(getCharacter(2L, "Superman", "Clark Kent", "Metropolis", List.of("Super strength", "XRay Vision", "Heat Vision")));
        Mockito.when(characterRepository.findAll()).thenReturn(characters);

        //Act
        List<CharacterDTO> characterDTOS = characterService.getAll();

        //Assert
        Assertions.assertEquals(characters.size(), characterDTOS.size());
    }

    @Test
    public void getAllPagedShouldReturnCharactersByPage(){
        //Arrange
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<Character> characters = new ArrayList<>();
        characters.add(getCharacter(1L, "Batman", "Bruce Wayne", "Gotham", List.of("Intelligence", "Martial Arts", "Strategy")));
        characters.add(getCharacter(2L, "Flash", "Barry Allen", "Central City", List.of("Super Velocity")));

        Page<Character> characterPage = new PageImpl<>(characters, pageRequest, characters.size());

        Mockito.when(characterRepository.findAll(pageRequest)).thenReturn(characterPage);
        //Act
        Page<CharacterDTO> characterDTOPage = characterService.getAllPaged(pageRequest);

        //Assert
        Assertions.assertEquals(1, characterDTOPage.getTotalPages());
        Assertions.assertEquals(characters.size(), characterDTOPage.getTotalElements());
    }

    @Test
    public void getCharacterByIdThrowExceptionWhenNonExistentIdIsProvided(){
        //Arrange
        long id = 5L;
        Mockito.when(characterRepository.findById(id)).thenThrow(CharacterNotFoundException.class);

        //Act && Assert
        Assertions.assertThrows(CharacterNotFoundException.class, () -> characterService.getById(id));
    }

    @Test
    public void getCharacterByIdShouldReturnACharacterWhenExistentIdIsProvided(){
        //Arrange
        List<Character> characters = new ArrayList<>();
        characters.add(getCharacter(1L, "Batman", "Bruce Wayne", "Gotham", List.of("Intelligence", "Martial Arts", "Strategy")));
        characters.add(getCharacter(2L, "Superman", "Clark Kent", "Metropolis", List.of("Super strength", "XRay Vision", "Heat Vision")));
        characters.add(getCharacter(3L, "Flash", "Barry Allen", "Central City", List.of("Super Velocity")));
        long id = 3L;

        Mockito.when(characterRepository.findById(id)).thenReturn(characters.stream().filter((c)-> c.getId() == id).findFirst());
        //Act
        CharacterDTO characterDto = characterService.getById(id);

        //Assert
        Assertions.assertNotNull(characterDto);
    }

    @Test
    public void saveShouldBeAbleToCreateNewCharacter(){
        //Arrange
        Character character = getCharacter(10L, "Spider Man", "Peter Parker", "New York", List.of("Wall Crawling", "Super Strength"));
        CharacterDTO characterDTO = DTOConverter.convert(character);
        Mockito.when(characterRepository.save(Mockito.any())).thenReturn(character);

        //Act
        CharacterDTO newCharacter = characterService.save(characterDTO);

        //Assert
        Assertions.assertEquals("Spider Man", newCharacter.getName());
    }

    @Test
    public void getByNameShouldReturnACharacterWhenExistingNameIsProvided(){
        //Arrange
        Character character = getCharacter(11L, "Mr Fantastic", "Reed Richards", "New York", List.of("Intelligence", "Elastic"));
        Mockito.when(characterRepository.findByName(Mockito.anyString())).thenReturn(character);

        //Act
        CharacterDTO characterDTO = characterService.getByName("Mr Fantastic");

        //Assert
        Assertions.assertNotNull(characterDTO);
    }

    @Test
    public void getCharacterByNameThrowExceptionWhenNonExistentNameIsProvided(){
        //Arrange
        String name = "John Kennedy";
        Mockito.when(characterRepository.findByName(name)).thenThrow(CharacterNotFoundException.class);

        //Act && Assert
        Assertions.assertThrows(CharacterNotFoundException.class, () -> characterService.getByName(name));
    }

    @Test
    public void editCharacterShouldBeAbleToModifyCharacterInfo(){
        //Arrange
        Long id = 12L;
        Character characterToBeUpdated = getCharacter(id, "Wolverine", "Joker", "Canada", List.of("Strength", "Adamantium Claws"));
        Mockito.when(characterRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getCharacter(id, "Wolverine", "Joker", "Canada", List.of("Strength", "Adamantium Claws"))));
        CharacterDTO dto = DTOConverter.convert(getCharacter(id, "Wolverine", "Logan", "Canada", List.of("Strength", "Adamantium Claws")));
        Mockito.when(characterRepository.save(Mockito.any())).thenReturn(Character.convert(dto));
        //Act
        CharacterDTO updatedCharacter = characterService.editCharacter(id, dto);

        //Assert
        Assertions.assertNotEquals(characterToBeUpdated.getBirthName(), updatedCharacter.getBirthName());
    }


    private Character getCharacter(Long id, String name, String birthName, String city, List<String> habilities){
        Character character = new Character();
        character.setId(id);
        character.setName(name);
        character.setBirthName(birthName);
        character.setCity(city);
        character.setHabilities(habilities);

        return character;
    }
}

package com.felipearcanjo.superhero.controller;

import com.felipearcanjo.superhero.dto.CharacterDTO;
import com.felipearcanjo.superhero.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name= "Character", description = "Character management APIs")
@RestController
@RequestMapping("api/characters")
@RequiredArgsConstructor
public class CharacterController {

    @Autowired
    private final CharacterService characterService;

    @Operation(summary = "Retrieve characters and display result separated by pages")
    @GetMapping("/pageable")
    public Page<CharacterDTO> getCharactersPaged(Pageable pageable){
        return characterService.getAllPaged(pageable);
    }

    @Operation(summary = "Retrieve all characters")
    @GetMapping
    public List<CharacterDTO> getAllCharacters(){
        return characterService.getAll();
    }

    @Operation(summary = "Retrieve a character by id")
    @GetMapping("/{id}")
    public CharacterDTO findCharacterById(@PathVariable Long id){
        return characterService.getById(id);
    }

    @Operation(summary = "Retrieve a character by name")
    @GetMapping("/name/{name}")
    public CharacterDTO findCharacterByName(@PathVariable String name){
        return characterService.getByName(name);
    }

    @Operation(summary = "Delete a character")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable Long id){
        characterService.delete(id);
    }

    @Operation(summary = "Create a character")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@Valid @RequestBody CharacterDTO characterDTO){
        return characterService.save(characterDTO);
    }

    @Operation(summary = "Update a character info")
    @PutMapping("/{id}")
    public CharacterDTO editCharacter(@PathVariable Long id, @Valid @RequestBody CharacterDTO characterDto){
        return characterService.editCharacter(id, characterDto);
    }
}

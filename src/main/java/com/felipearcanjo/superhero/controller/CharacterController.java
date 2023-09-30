package com.felipearcanjo.superhero.controller;

import com.felipearcanjo.superhero.dto.CharacterDTO;
import com.felipearcanjo.superhero.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/pageable")
    public Page<CharacterDTO> getCharactersPaged(Pageable pageable){
        return characterService.getAllPaged(pageable);
    }

    @GetMapping
    public List<CharacterDTO> getAllCharacters(){
        return characterService.getAll();
    }

    @GetMapping("/{id}")
    public CharacterDTO findCharacterById(@PathVariable Long id){
        return characterService.getById(id);
    }

    @GetMapping("/{name}/name")
    public CharacterDTO findCharacterByName(@PathVariable String name){
        return characterService.getByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable Long id){
        characterService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@Valid @RequestBody CharacterDTO characterDTO){
        return characterService.save(characterDTO);
    }

    @PutMapping("/{id}")
    public CharacterDTO editCharacter(@PathVariable Long id, @Valid @RequestBody CharacterDTO characterDto){
        return characterService.editCharacter(id, characterDto);
    }
}

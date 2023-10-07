package com.felipearcanjo.superhero.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTO {
    private long id;
    @NotBlank(message = "Character Name Is Required")
    private String name;
    private String birthName;
    private String city;
    @NotEmpty(message = "Character Habilities Are Required")
    private List<@NotNull String> habilities;
}

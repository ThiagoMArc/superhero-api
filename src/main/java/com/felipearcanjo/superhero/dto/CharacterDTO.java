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
    //@NotNull(message = "Necessário informar o nome do personagem")
    @NotBlank(message = "Necessário informar o nome do personagem")
    private String name;
    private String birthName;
    private String city;
    //@NotNull(message = "Necessário informar habilidades do personagem")
    @NotEmpty(message = "Necessaŕio informar habilidades do personagem")
    private List<@NotNull String> habilities;
}

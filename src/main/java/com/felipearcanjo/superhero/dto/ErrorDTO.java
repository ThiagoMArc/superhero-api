package com.felipearcanjo.superhero.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private int status;
    private String message;
    private Map<String, List<String>> validationErrors;
    private LocalDateTime timestamp;
}

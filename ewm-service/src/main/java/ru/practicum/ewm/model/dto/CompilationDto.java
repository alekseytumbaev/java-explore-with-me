package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    @Valid
    private Set<EventShortDto> events;

    @NotNull
    private Long id;

    @NotNull
    private Boolean pinned;

    @NotNull
    @NotBlank
    private String title;
}


package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

    @NotNull
    @Size(min = 6, max = 254)
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}


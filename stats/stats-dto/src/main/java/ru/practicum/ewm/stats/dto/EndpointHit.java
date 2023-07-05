package ru.practicum.ewm.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import ru.practicum.ewm.stats.contant.Patterns;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    @JsonProperty(access = READ_ONLY)
    private Long id;

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    @Pattern(regexp = "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})", message = "must be a valid IPv4 address")
    private String ip;

    @NotNull
    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime timestamp;
}

package ru.practicum.ewm.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.practicum.ewm.stats.contant.Patterns;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private List<String> error;
    private String message;
    private String reason;
    private HttpStatus status;

    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime timestamp;
}


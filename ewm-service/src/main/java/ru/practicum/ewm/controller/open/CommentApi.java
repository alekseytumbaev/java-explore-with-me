package ru.practicum.ewm.controller.open;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.model.dto.CommentDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RequestMapping("/comments")
public interface CommentApi {

    @GetMapping
    List<CommentDto> getComments(
            @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "from", required = false, defaultValue = "0") @Valid @PositiveOrZero Integer from,
            @RequestParam(value = "size", required = false, defaultValue = "10") @Valid @Positive Integer size
    );
}

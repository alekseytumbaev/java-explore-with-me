package ru.practicum.ewm.controller.open.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.controller.open.CommentApi;
import ru.practicum.ewm.model.dto.CommentDto;
import ru.practicum.ewm.service.CommentService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentsController implements CommentApi {

    private final CommentService commentService;

    @Override
    public List<CommentDto> getComments(Long eventId, Integer from, Integer size) {
        List<CommentDto> comments = commentService.getCommentsByEventId(eventId, from, size);
        log.info("Found {} comments, params: from={}, size={}", comments.size(), from, size);
        return comments;
    }
}

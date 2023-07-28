package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exception.*;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.model.dto.CommentDto;
import ru.practicum.ewm.model.dto.EventState;
import ru.practicum.ewm.model.entity.Comment;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private static final int MINUTES_BEFORE_ACTION_RESTRICTED = 15;

    private final CommentRepository commentRepo;
    private final UserService userService;
    private final EventRepository eventRepo;

    public CommentDto addComment(Long userId, Long eventId, CommentDto commentDto) {
        User author = userService.getEntityById(userId);
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(format("Cannot add comment, because event with id=%s not found", eventId)));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new CommentingRestrictedException("Cannot add comment, because event is not published");
        }

        commentDto.setCreatedOn(LocalDateTime.now());
        Comment comment = CommentMapper.toEntity(commentDto, null, author, event);
        return CommentMapper.toDto(commentRepo.save(comment));
    }

    public List<CommentDto> getCommentsByUserId(Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException(format("Cannot add comment, because user with id=%d not found", userId));
        }
        List<Comment> comments = commentRepo.findAllByAuthor_IdOrderByCreatedOnDesc(userId);
        return comments.stream().map(CommentMapper::toDto).collect(toList());
    }

    public CommentDto updateComment(Long userId, Long commentId, CommentDto commentDto) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException(format("Cannot update comment with id=%s, because it's not found", commentId)));

        checkIfActionAllowed(comment, userId);

        comment.setText(commentDto.getText());
        comment.setEditedOn(LocalDateTime.now());
        return CommentMapper.toDto(commentRepo.save(comment));
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException(format("Cannot delete comment with id=%s, because it's not found", commentId)));

        checkIfActionAllowed(comment, userId);

        commentRepo.deleteById(commentId);
    }

    void checkIfActionAllowed(Comment comment, Long userId) {
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new CommentActionRestrictedException(
                    format("User with id=%d cannot perform action on comment with id=%d, because his not the author",
                            userId, comment.getId())
            );
        }
        if (LocalDateTime.now().minusMinutes(MINUTES_BEFORE_ACTION_RESTRICTED).isAfter(comment.getCreatedOn())) {
            throw new CommentActionRestrictedException(
                    format("Cannot perform action on comment with id=%d, because it's too old, creation date is %s",
                            comment.getId(), comment.getCreatedOn()));
        }
    }

    public void deleteCommentAdmin(Long commentId) {
        if (!commentRepo.existsById(commentId)) {
            throw new CommentNotFoundException(format("Cannot delete comment with id=%s, because it's not found", commentId));
        }
        commentRepo.deleteById(commentId);
    }

    public List<CommentDto> getCommentsByEventId(Long eventId, Integer from, Integer size) {
        if (!eventRepo.existsById(eventId)) {
            throw new EventNotFoundException(format("Cannot get comments, because event with id=%s not found", eventId));
        }
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("createdOn").descending());
        List<Comment> comments = commentRepo.findAllByEvent_Id(eventId, pageRequest);
        return comments.stream().map(CommentMapper::toDto).collect(toList());
    }
}

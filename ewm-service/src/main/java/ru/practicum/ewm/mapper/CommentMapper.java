package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.CommentDto;
import ru.practicum.ewm.model.entity.Comment;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.User;

@UtilityClass
public class CommentMapper {

    public Comment toEntity(CommentDto dto, Long id, User author, Event event) {
        return new Comment(
                id,
                dto.getText(),
                event,
                author,
                dto.getCreatedOn(),
                dto.getEditedOn()
        );
    }

    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getId(),
                comment.getEvent().getId(),
                comment.getCreatedOn(),
                comment.getEditedOn()
        );
    }
}

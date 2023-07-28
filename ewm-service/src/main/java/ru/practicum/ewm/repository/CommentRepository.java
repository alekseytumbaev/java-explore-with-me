package ru.practicum.ewm.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthor_IdOrderByCreatedOnDesc(Long userId);

    List<Comment> findAllByEvent_Id(Long id, PageRequest pageRequest);
}

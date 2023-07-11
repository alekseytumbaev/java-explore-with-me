package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            "select u from User u where u.id in :ids"
    )
    Page<User> findAllById(Iterable<Long> ids, PageRequest pageRequest);
}

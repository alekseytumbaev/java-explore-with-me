package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

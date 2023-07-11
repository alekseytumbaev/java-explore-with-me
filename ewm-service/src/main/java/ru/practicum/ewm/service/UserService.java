package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exception.UserNotFoundException;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.dto.NewUserRequest;
import ru.practicum.ewm.model.dto.UserDto;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.repository.UserRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public UserDto registerUser(NewUserRequest newUserRequest) {
        User user = userRepo.save(UserMapper.toEntity(newUserRequest));
        return UserMapper.toDto(user);
    }

    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);

        Page<User> users;
        if (ids == null || ids.isEmpty()) {
            users = userRepo.findAll(pageRequest);
        } else {
            users = userRepo.findAllById(ids, pageRequest);
        }
        return users.map(UserMapper::toDto).toList();
    }

    public void delete(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException(format("Cannot delete user with id=%s, because it's not found", userId));
        }
        userRepo.deleteById(userId);
    }

    public User getEntityById(long userId) {
        return userRepo.findById(userId).orElseThrow(() ->
                new UserNotFoundException(format("Cannot get user with id=%s, because it's not found", userId)));
    }

    public boolean existsById(long userId) {
        return userRepo.existsById(userId);
    }
}

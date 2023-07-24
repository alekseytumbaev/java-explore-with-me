package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.NewUserRequest;
import ru.practicum.ewm.model.dto.UserDto;
import ru.practicum.ewm.model.dto.UserShortDto;
import ru.practicum.ewm.model.entity.User;

@UtilityClass
public class UserMapper {

    public User toEntity(NewUserRequest userRequest) {
        return new User(
                null,
                userRequest.getEmail(),
                userRequest.getName()
        );
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName()
        );
    }

    public UserShortDto toShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }
}

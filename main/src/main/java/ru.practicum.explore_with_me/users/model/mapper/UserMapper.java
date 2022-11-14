package ru.practicum.explore_with_me.users.model.mapper;


import ru.practicum.explore_with_me.users.model.User;
import ru.practicum.explore_with_me.users.model.dto.UserDto;
import ru.practicum.explore_with_me.users.model.dto.UserShortDto;

public class UserMapper {

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}

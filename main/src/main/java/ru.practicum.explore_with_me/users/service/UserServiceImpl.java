package ru.practicum.explore_with_me.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.exception.ConflictException;
import ru.practicum.explore_with_me.exception.ObjectNotFoundException;
import ru.practicum.explore_with_me.users.model.User;
import ru.practicum.explore_with_me.users.model.dto.NewUserRequest;
import ru.practicum.explore_with_me.users.model.dto.UserDto;
import ru.practicum.explore_with_me.users.model.mapper.UserMapper;
import ru.practicum.explore_with_me.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;

    public List<UserDto> getUsers(List<Long> ids, Pageable pageRequest) {
        return userRepository.getUsers(ids, pageRequest).stream().map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto createUser(NewUserRequest newUser) {
        if (userRepository.findAll().stream().map(User::getName).collect(Collectors.toList()).contains(newUser.getName())) {
            throw new ConflictException("Имя занято");
        }
        return UserMapper.toUserDto(userRepository.save(new User(0L, newUser.getName(), newUser.getEmail())));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Пользователь с таким id не найден"));
        userRepository.delete(user);
    }
}

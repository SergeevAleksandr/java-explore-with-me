package ru.practicum.explore_with_me.users.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
    private String email;
    private String name;
}
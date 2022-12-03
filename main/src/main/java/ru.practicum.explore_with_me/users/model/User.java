package ru.practicum.explore_with_me.users.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class User {
    @Transient
    final String userId = "user_id";
    @Transient
    final String userName = "user_name";
    @Transient
    final String userEmail = "user_email";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = userId)
    Long id;
    @Column(name = userName)
    String name;
    @Column(name = userEmail)
    String email;
}
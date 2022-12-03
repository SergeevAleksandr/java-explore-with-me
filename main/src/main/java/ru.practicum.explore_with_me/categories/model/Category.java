package ru.practicum.explore_with_me.categories.model;

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
@Table(name = "categories")
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class Category {
    @Transient
    final String categoryId = "category_id";
    @Transient
    final String categoryName = "category_name";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = categoryId)
    Long id;
    @Column(name = categoryName)
    String name;
}

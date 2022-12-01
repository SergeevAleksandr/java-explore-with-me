package ru.practicum.explore_with_me.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.compilations.model.Compilation;

import java.util.List;


public interface CompilationRepository extends JpaRepository<Compilation,Long>  {
    List<Compilation> getCompilationsByPinned(boolean pinned, Pageable pageRequest);
}

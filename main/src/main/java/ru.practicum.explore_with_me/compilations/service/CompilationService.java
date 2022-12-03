package ru.practicum.explore_with_me.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.compilations.model.Compilation;
import ru.practicum.explore_with_me.compilations.model.dto.CompilationDto;
import ru.practicum.explore_with_me.compilations.model.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilations.model.mapper.CompilationMapper;
import ru.practicum.explore_with_me.compilations.repository.CompilationRepository;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;
import ru.practicum.explore_with_me.events.model.mapper.EventMapper;
import ru.practicum.explore_with_me.events.repository.EventRepository;
import ru.practicum.explore_with_me.exception.ObjectNotFoundException;
import ru.practicum.explore_with_me.requests.repository.RequestRepository;
import ru.practicum.explore_with_me.stats.StatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationService {
    private final EventRepository eventRepository;
    private final StatsService statsService;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;

    public CompilationDto create(NewCompilationDto newCompilation) {
        List<Event> eventList = eventRepository.findAllById(newCompilation.getEvents());
        Compilation compilation = new Compilation(0L, newCompilation.getTitle(), newCompilation.getPinned(), eventList);
        List<EventShortDto> eventDtoList = new ArrayList<>();
        if (!compilation.getEvents().isEmpty()) {
            Map<Long, Long> viewsStat = statsService.getViews(eventList, false);
            eventDtoList = compilation.getEvents().stream()
                    .map(e -> EventMapper.toEventShortDto(e, requestRepository.getConfirmedRequests(e.getId()),
                            viewsStat.getOrDefault(e.getId(), 0L))).collect(Collectors.toList());
        }
        return CompilationMapper.toCompilationDTO(compilationRepository.save(compilation), eventDtoList);
    }

    public void deleteById(Long compilationId) {
        Compilation compilation = checkCompilation(compilationId);
        compilationRepository.delete(compilation);
    }

    public void deleteEventFromCompilation(Long compilationId, Long eventId) {
        Compilation compilation = checkCompilation(compilationId);
        Event event = checkEvent(eventId);
        List<Event> eventList = compilation.getEvents();
        eventList.remove(event);
        compilation.setEvents(eventList);
        compilationRepository.save(compilation);
    }

    public void addEventToCompilation(Long compilationId, Long eventId) {
        Compilation compilation = checkCompilation(compilationId);
        Event event = checkEvent(eventId);
        List<Event> eventList = compilation.getEvents();
        eventList.add(event);
        compilation.setEvents(eventList);
        compilationRepository.save(compilation);
    }

    public void setPinCompilation(Long compilationId, Boolean flag) {
        Compilation compilation = checkCompilation(compilationId);;
        compilation.setPinned(flag);
        compilationRepository.save(compilation);
    }

    public List<CompilationDto> get(Boolean pinned, PageRequest pageRequest) {
        List<Compilation> compilationList = compilationRepository.getCompilationsByPinned(pinned, pageRequest);
        List<CompilationDto> compilationDtoList = new ArrayList<>();
        for (Compilation compilation : compilationList) {
            List<EventShortDto> eventList = new ArrayList<>();
            if (!compilation.getEvents().isEmpty()) {
                Map<Long, Long> viewsStat = statsService.getViews(compilation.getEvents(), false);
                eventList = compilation.getEvents().stream()
                        .map(e -> EventMapper.toEventShortDto(e, requestRepository.getConfirmedRequests(e.getId()),
                                viewsStat.getOrDefault(e.getId(), 0L))).collect(Collectors.toList());
            }
            compilationDtoList.add(CompilationMapper.toCompilationDTO(compilation, eventList));
        }
        return compilationDtoList;
    }

    public CompilationDto getById(Long compilationId) {
        Compilation compilation = checkCompilation(compilationId);
        List<EventShortDto> eventList = new ArrayList<>();
        if (!compilation.getEvents().isEmpty()) {
            Map<Long, Long> viewsStat = statsService.getViews(compilation.getEvents(), false);
            eventList = compilation.getEvents().stream()
                    .map(e -> EventMapper.toEventShortDto(e, requestRepository.getConfirmedRequests(e.getId()),
                            viewsStat.getOrDefault(e.getId(), 0L))).collect(Collectors.toList());
        }
        return CompilationMapper.toCompilationDTO(compilation, eventList);
    }

    public Compilation checkCompilation(Long id) {
        return compilationRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Подборка с таким id не найдена!"));
    }

    public Event checkEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Событие с таким id не найдено!"));
    }
}

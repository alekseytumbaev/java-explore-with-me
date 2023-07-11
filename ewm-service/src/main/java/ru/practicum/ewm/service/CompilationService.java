package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exception.CompilationNotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm.model.entity.Compilation;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.repository.CompilationRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationService {

    private final CompilationRepository compilationRepo;
    private final EventService eventService;

    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventService.findEntitiesById(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto, events);
        compilation = compilationRepo.save(compilation);

        Set<EventShortDto> eventsDtos = new HashSet<>(eventService.toShortDtos(events, false));
        return CompilationMapper.toDto(compilation, eventsDtos);
    }

    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilation) {
        Compilation compilation = compilationRepo.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(format("Compilation with id=%d not found", compId))
        );
        List<Event> events = eventService.findEntitiesById(updateCompilation.getEvents());
        compilation = CompilationMapper.toUpdatedEntity(updateCompilation, events, compilation);
        compilation = compilationRepo.save(compilation);

        Set<EventShortDto> eventsDtos = new HashSet<>(eventService.toShortDtos(events, false));
        return CompilationMapper.toDto(compilation, eventsDtos);
    }


    public void deleteCompilation(Long compId) {
        compilationRepo.deleteById(compId);
    }

    public CompilationDto getCompilation(Long compId) {
        Compilation compilation = compilationRepo.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(format("Compilation with id=%d not found", compId))
        );
        Set<EventShortDto> eventsDtos = new HashSet<>(eventService.toShortDtos(compilation.getEvents(), false));
        return CompilationMapper.toDto(compilation, eventsDtos);
    }

    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Page<Compilation> compilations;
        compilations = pinned == null ? compilationRepo.findAll(pageRequest) : compilationRepo.findAllByPinned(pinned, pageRequest);

        return compilations.map(c ->
                CompilationMapper.toDto(c, new HashSet<>(eventService.toShortDtos(c.getEvents(), false)))
        ).toList();
    }


}

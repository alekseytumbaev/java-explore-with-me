package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.NewCompilationDto;
import ru.practicum.ewm.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm.model.entity.Compilation;
import ru.practicum.ewm.model.entity.Event;

import java.util.List;
import java.util.Set;

@UtilityClass
public class CompilationMapper {

    public Compilation toEntity(NewCompilationDto compilationDto, List<Event> events) {
        return new Compilation(
                null,
                events,
                compilationDto.getPinned(),
                compilationDto.getTitle()
        );
    }

    public CompilationDto toDto(Compilation compilation, Set<EventShortDto> events) {
        return new CompilationDto(
                events,
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }

    public Compilation toUpdatedEntity(UpdateCompilationRequest updateCompilation,
                                       List<Event> events,
                                       Compilation previousCompilation) {
        Compilation compilation = copyCompilation(previousCompilation);
        if (updateCompilation.getEvents() != null && events != null) {
            compilation.setEvents(events);
        }
        if (updateCompilation.getPinned() != null) {
            compilation.setPinned(updateCompilation.getPinned());
        }
        if (updateCompilation.getTitle() != null) {
            compilation.setTitle(updateCompilation.getTitle());
        }
        return compilation;
    }

    private Compilation copyCompilation(Compilation compilation) {
        return new Compilation(
                compilation.getId(),
                compilation.getEvents(),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }
}

package ru.practicum.ewm.controller.open.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.controller.open.CompilationApi;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompilationController implements CompilationApi {
    private final CompilationService compilationService;

    @Override
    public CompilationDto getCompilation(Long compId) {
        CompilationDto compilationDto = compilationService.getCompilation(compId);
        log.info("Found compilation with id: {}", compId);
        return compilationDto;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<CompilationDto> compilations = compilationService.getCompilations(pinned, from, size);
        log.info("Found {} compilations, params: pinned={}, from={}, size={}", compilations.size(), pinned, from, size);
        return compilations;
    }
}

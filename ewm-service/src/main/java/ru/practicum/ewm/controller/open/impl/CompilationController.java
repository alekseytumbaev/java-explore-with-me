package ru.practicum.ewm.controller.open.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.controller.open.CompilationsApi;
import ru.practicum.ewm.model.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CompilationController implements CompilationsApi {
    private final CompilationService compilationService;

    @Override
    public ResponseEntity<CompilationDto> getCompilation(Long compId) {
        CompilationDto compilationDto = compilationService.getCompilation(compId);
        log.info("Found compilation with id: {}", compId);
        return ResponseEntity.ok(compilationDto);
    }

    @Override
    public ResponseEntity<List<CompilationDto>> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<CompilationDto> compilations = compilationService.getCompilations(pinned, from, size);
        log.info("Found {} compilations, params: pinned={}, from={}, size={}", compilations.size(), pinned, from, size);
        return ResponseEntity.ok(compilations);
    }
}

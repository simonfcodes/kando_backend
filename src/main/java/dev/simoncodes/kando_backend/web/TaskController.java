package dev.simoncodes.kando_backend.web;

import dev.simoncodes.kando_backend.dto.TaskDto;
import dev.simoncodes.kando_backend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@Validated @RequestBody TaskDto dto) {
        return service.create(dto);
    }
}

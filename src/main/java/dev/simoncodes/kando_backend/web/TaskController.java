package dev.simoncodes.kando_backend.web;

import dev.simoncodes.kando_backend.domain.Task;
import dev.simoncodes.kando_backend.dto.TaskDto;
import dev.simoncodes.kando_backend.dto.UpdateTaskDto;
import dev.simoncodes.kando_backend.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public Page<TaskDto> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue ="createdAt,desc") String sort,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Boolean overdue) {
        Sort.Direction direction = sort.endsWith(",desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sort.split(",")[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        return service.listFiltered(
                Optional.ofNullable(status),
                Optional.ofNullable(priority),
                Optional.ofNullable(overdue),
                pageable
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@Validated @RequestBody TaskDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        var status = body.get("status");
        service.updateStatus(id, Task.Status.valueOf(status));
    }

    @PatchMapping("/{id}")
    public TaskDto updateTask(@PathVariable UUID id, @RequestBody UpdateTaskDto dto) {
        return service.updateTask(id, dto);
    }
}

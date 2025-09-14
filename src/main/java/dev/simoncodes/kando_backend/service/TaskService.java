package dev.simoncodes.kando_backend.service;

import dev.simoncodes.kando_backend.domain.Task;
import dev.simoncodes.kando_backend.dto.TaskDto;
import dev.simoncodes.kando_backend.dto.UpdateTaskDto;
import dev.simoncodes.kando_backend.mapper.TaskMapper;
import dev.simoncodes.kando_backend.repo.TaskRepository;
import dev.simoncodes.kando_backend.utils.TaskUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository repo;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repo, TaskMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    // Transactional is needed for the save operation
    // Ensures that the operation is all or nothing, any errors will roll back the whole transaction
    @Transactional
    public TaskDto create(TaskDto dto) {
        Task entity = mapper.toEntity(dto);
        Task saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<TaskDto> getTasks(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TaskDto> listFiltered(
            Optional<String> status,
            Optional<String> priority,
            Optional<Boolean> overdue,
            Pageable pageable) {

        List<Specification<Task>> parts = new ArrayList<>();

        status.ifPresent(s ->
                parts.add(TaskSpecifications.statusEquals(
                        TaskUtils.parseEnumIgnoreCase(Task.Status.class, s)))
        );
        priority.ifPresent(p ->
                parts.add(TaskSpecifications.priorityEquals(
                        TaskUtils.parseEnumIgnoreCase(Task.Priority.class, p)))
        );
        if (overdue.orElse(false)) {
            parts.add(TaskSpecifications.overdueAt(ZonedDateTime.now(ZoneId.of("UTC"))));
        }

        Specification<Task> spec = parts.stream()
                .reduce((r, q, cb) -> cb.conjunction(), Specification::and);

        return repo.findAll(spec, pageable).map(mapper::toDto);
    }

    @Transactional
    public void updateStatus(UUID id, Task.Status status) {
        Task t = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found."));
        t.setStatus(status);
    }

    @Transactional
    public TaskDto updateTask(UUID id, UpdateTaskDto dto) {
        Task t = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found."));
        mapper.updateEntityFromDto(dto, t);
        if (Boolean.TRUE.equals(dto.clearDueDate)) t.setDueDate(null);
        if (Boolean.TRUE.equals(dto.clearDueTime)) t.setDueTime(null);
        return mapper.toDto(t);
    }
}

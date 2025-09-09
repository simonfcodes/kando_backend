package dev.simoncodes.kando_backend.service;

import dev.simoncodes.kando_backend.domain.Task;
import dev.simoncodes.kando_backend.dto.TaskDto;
import dev.simoncodes.kando_backend.mapper.TaskMapper;
import dev.simoncodes.kando_backend.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

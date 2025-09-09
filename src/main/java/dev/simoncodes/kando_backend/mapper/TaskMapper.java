package dev.simoncodes.kando_backend.mapper;

import dev.simoncodes.kando_backend.domain.Category;
import dev.simoncodes.kando_backend.domain.Label;
import dev.simoncodes.kando_backend.domain.Task;
import dev.simoncodes.kando_backend.dto.TaskDto;
import dev.simoncodes.kando_backend.repo.CategoryRepository;
import dev.simoncodes.kando_backend.repo.LabelRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    @Autowired protected CategoryRepository categoryRepo;
    @Autowired protected LabelRepository labelRepo;

    @Mapping(target = "priority", expression = "java(task.getPriority().name())")
    @Mapping(target = "status", expression = "java(task.getStatus().name())")
    @Mapping(target = "categoryIds", expression = "java(toIds(task.getCategories()))")
    @Mapping(target = "labelIds", expression = "java(toIds(task.getLabels()))")
    public abstract TaskDto toDto(Task task);

    @Mapping(target = "priority", expression = "java(Task.Priority.valueOf(dto.priority))")
    @Mapping(target = "status", expression = "java(Task.Status.valueOf(dto.status))")
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "labels", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract Task toEntity(TaskDto dto);

    @AfterMapping
    protected void loadRelations(TaskDto dto, @MappingTarget Task task) {
        if (dto.categoryIds != null) {
            Set<Category> cats = dto.categoryIds.stream()
                    .map(id -> categoryRepo.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " not found.")))
                    .collect(Collectors.toSet());
            task.setCategories(cats);
        }
        if (dto.labelIds != null) {
            Set<Label> labels = dto.labelIds.stream()
                    .map(id -> labelRepo.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Label with id " + id + " not found.")))
                    .collect(Collectors.toSet());
            task.setLabels(labels);
        }
    }

    protected Set<UUID> toIds(Set<? extends Object> entities) {
        if (entities == null) return null;

        return entities.stream().map(e -> {
            if (e instanceof Category c) return c.getId();
            if (e instanceof Label l) return l.getId();
            throw new IllegalStateException("Unexpected type: " + e.getClass());
         }).collect(Collectors.toSet());

    }

}


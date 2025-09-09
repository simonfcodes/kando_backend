package dev.simoncodes.kando_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public class TaskDto {
    public UUID id;
    public String title;
    public String description;
    public String priority; // "LOW", "MEDIUM", "HIGH"
    public String status; // "TODO", "DONE"
    public LocalDate dueDate;
    public LocalTime dueTime;
    public Set<UUID> categoryIds;
    public Set<UUID> labelIds;
}

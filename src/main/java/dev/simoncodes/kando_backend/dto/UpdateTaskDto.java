package dev.simoncodes.kando_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public class UpdateTaskDto {

    public String title;
    public String description;
    public String priority;
    public String status;
    public LocalDate dueDate;
    public LocalTime dueTime;

    public Set<UUID> categoryIds;
    public Set<UUID> labelIds;

    public Boolean clearDueDate;
    public Boolean clearDueTime;
}

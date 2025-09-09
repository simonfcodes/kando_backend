package dev.simoncodes.kando_backend.repo;

import dev.simoncodes.kando_backend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}

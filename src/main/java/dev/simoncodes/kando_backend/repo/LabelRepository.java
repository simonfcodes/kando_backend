package dev.simoncodes.kando_backend.repo;

import dev.simoncodes.kando_backend.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabelRepository extends JpaRepository<Label, UUID> {
}

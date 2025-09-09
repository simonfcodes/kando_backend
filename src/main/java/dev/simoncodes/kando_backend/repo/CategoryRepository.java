package dev.simoncodes.kando_backend.repo;

import dev.simoncodes.kando_backend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}

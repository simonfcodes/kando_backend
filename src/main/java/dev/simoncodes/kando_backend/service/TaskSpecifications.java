package dev.simoncodes.kando_backend.service;

import dev.simoncodes.kando_backend.domain.Task;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public final class TaskSpecifications {
    private TaskSpecifications() {}

    public static Specification<Task> statusEquals(Task.Status status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Task> priorityEquals(Task.Priority priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority);
    }

    public static Specification<Task> overdueAt(ZonedDateTime now) {
        Objects.requireNonNull(now);
        LocalDate today = now.toLocalDate();
        LocalTime time = now.toLocalTime();

        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Task.Status> statusPath = root.get("status");
                Path<LocalDate> dueDatePath = root.get("dueDate");
                Path<LocalTime> dueTimePath = root.get("dueTime");

                // Status = TODO
                Predicate statusIsToDo = cb.equal(statusPath, Task.Status.TODO);
                // Due date < today
                Predicate dueDateBeforeToday = cb.lessThan(dueDatePath, today);
                // Due date = today
                Predicate dueDateIsToday = cb.equal(dueDatePath, today);
                // Due time is set (not null)
                Predicate dueTimeIsNotNull = cb.isNotNull(dueTimePath);
                // Due time < now
                Predicate dueTimeBeforeNow = cb.lessThan(dueTimePath, time);
                // Due date = today AND due time is set AND due time < now
                Predicate dueTodayAndTimePast = cb.and(dueDateIsToday, dueTimeIsNotNull, dueTimeBeforeNow);
                // (Due date < today) OR (due_date = today AND due_time < now)
                Predicate datePastOrTodayPastTime = cb.or(dueDateBeforeToday, dueTodayAndTimePast);
                // Final predicates - stitch together - final status is todo and the due date is past, or it's today but past the time
                return cb.and(statusIsToDo, datePastOrTodayPastTime);
            }
        };
    }
}

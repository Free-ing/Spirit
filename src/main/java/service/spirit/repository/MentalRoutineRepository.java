package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.spirit.entity.MentalRoutine;

public interface MentalRoutineRepository extends JpaRepository<MentalRoutine,Long> {
}

package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import service.spirit.entity.MentalRoutine;

import java.util.List;

@Repository
public interface MentalRoutineRepository extends JpaRepository<MentalRoutine,Long> {
    List<MentalRoutine> findByUserId(Long userId);

}

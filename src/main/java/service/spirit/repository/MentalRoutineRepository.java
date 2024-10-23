package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.MentalRoutine;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentalRoutineRepository extends JpaRepository<MentalRoutine,Long> {
    List<MentalRoutine> findByUserId(Long userId);

    @Query("select new service.spirit.dto.response.ResponseMentalDto$RoutineInfoDto(mr.explanation, mr.mentalRoutineName) " +
            "from MentalRoutine mr where mr.Id = :routineId")
    Optional<ResponseMentalDto.RoutineInfoDto> getRoutineInfo(Long routineId);
}

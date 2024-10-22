package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.EmotionalDiary;

import java.util.List;

@Repository
public interface EmotionalDiaryRepository extends JpaRepository<EmotionalDiary,Long> {

    @Query("SELECT new service.spirit.dto.response.ResponseMentalDto$DiaryDateDto(ed.createdAt, ed.emotion,ed.id) " +
            "FROM EmotionalDiary ed " +
            "WHERE YEAR(ed.createdAt) = :year " +
            "AND MONTH(ed.createdAt) = :month " +
            "AND ed.userId = :userId")
    List<ResponseMentalDto.DiaryDateDto> getRecordListByDate(@Param("year") int year,
                                                             @Param("month") int month,
                                                             @Param("userId") Long userId);
}

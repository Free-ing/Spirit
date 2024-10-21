package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.spirit.entity.EmotionalDiary;

@Repository
public interface EmotionalDiaryRepository extends JpaRepository<EmotionalDiary,Long> {
}

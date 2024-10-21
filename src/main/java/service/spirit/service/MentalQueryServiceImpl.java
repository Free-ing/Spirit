package service.spirit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.repository.EmotionalDiaryRepository;
import service.spirit.repository.MentalRoutineRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentalQueryServiceImpl implements MentalQueryService{

    private final MentalRoutineRepository mentalRoutineRepository;
    private final EmotionalDiaryRepository emotionalDiaryRepository;

    //Todo: 마음 채우기 루틴 리스트 조회
    @Override
    public List<ResponseMentalDto.MentalRoutineDto> getSpiritRoutineList(Long userId){
        return mentalRoutineRepository.findByUserId(userId)
                .stream()
                .map(mentalRoutine -> ResponseMentalDto.MentalRoutineDto.builder()
                        .hobbyName(mentalRoutine.getMentalRoutineName())
                        .routineId(mentalRoutine.getId())
                        .imageUrl(mentalRoutine.getImageUrl())
                        .build())
                .collect(Collectors.toList());

    }

    //Todo: 감정일기 존재하는지 여부 조회
    @Override
    public List<ResponseMentalDto.DiaryDateDto> getDiaryDate(int year, int month, Long userId){
        return emotionalDiaryRepository.getRecordListByDate(year, month,userId );
    }
}

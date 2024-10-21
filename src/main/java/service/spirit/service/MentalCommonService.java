package service.spirit.service;

import service.spirit.dto.request.MentalDto;

public interface MentalCommonService {
    //Todo: 마음 채우기 루틴 설정
    Long addMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto);

    //Todo: 감정일기 작성
    Long saveEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto);

    //Todo: 감정일기 작성
}

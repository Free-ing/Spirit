package service.spirit.service;

import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.MentalRoutine;

import java.util.List;

public interface MentalQueryService {
    //Todo: 마음 채우기 루틴 리스트 조회
    List<ResponseMentalDto.MentalRoutineDto> getSpiritRoutineList(Long userId);

    //Todo: 감정일기 존재하는지 여부 조회
    List<ResponseMentalDto.DiaryDateDto> getDiaryDate(int year, int month, Long userId);
}
package service.spirit.service;

import service.spirit.dto.response.MentalDto;

public interface MentalCommonService {
    //Todo: 마음 채우기 루틴 설정
    Long addMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto);
}

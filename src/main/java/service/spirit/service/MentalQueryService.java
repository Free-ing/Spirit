package service.spirit.service;

import org.springframework.transaction.annotation.Transactional;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.dto.response.RoutineTrackerDto;
import service.spirit.entity.MentalRoutine;

import java.time.LocalDate;
import java.util.List;

public interface MentalQueryService {
    //Todo: 마음 채우기 루틴 리스트 조회
    List<ResponseMentalDto.MentalRoutineDto> getSpiritRoutineList(Long userId);

//    //Todo: 감정일기 존재하는지 여부 조회
//    List<ResponseMentalDto.DiaryDateDto> getDiaryDate(int year, int month, Long userId);

    //Todo: 감정일기 존재하는지 여부 조회
    List<ResponseMentalDto.DiaryDateDto> getDiaryRecordDate(int year, int month, Long userId);

    // Todo: 감정일기 상세 조회 기능
    ResponseMentalDto.EmotionalDiaryDto getEmotionalDiary(Long recordId);

    //Todo: 마음 채우기 루틴 설명 보기
    ResponseMentalDto.RoutineInfoDto getRoutineInfo(Long routineId);

    //Todo: 월별 루틴 일정 조회
    List<ResponseMentalDto.DayRoutineDto> getDayRoutine(LocalDate date,Long userId);

    //Todo: 마음 채우기 루틴 트래커 조회
    List<RoutineTrackerDto.MentalRoutineTrackerDto> getMentalRoutineTrackers(Long userId, int year, int month);

    //Todo: 스크랩 감정일기 조회
    List<ResponseMentalDto.EmotionalDiaryDto> getScrapEmotionalDiary(Long userId);

    //Todo: ai 편지 조회
    ResponseMentalDto.AiLetterDto getLetter(Long letterId);

    //Todo: 홈화면 하나라도 수행한 날짜 반환
    @Transactional(readOnly = true)
    List<ResponseMentalDto.DayCompleteRoutine> getCompleteDate(LocalDate startDate, LocalDate endDate, Long userId);
}

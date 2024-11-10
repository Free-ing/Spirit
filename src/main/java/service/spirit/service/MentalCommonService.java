package service.spirit.service;

import service.spirit.dto.request.MentalDto;
import service.spirit.dto.response.ResponseMentalDto;

import java.time.LocalDate;

public interface MentalCommonService {
    //Todo: 마음 채우기 루틴 설정
    Long addMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto, Long userId);

    //Todo: 감정일기 작성
    Long saveEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto,Long recordId, Long userId);

    //Todo: 감정일기 스크랩하기
    Long emotionalRecordDiaryScrap(Long recordId, Long userId);

    //Todo: 감정일기 스크랩 취소
    Long emotionalRecordDiaryScrapCancel(Long recordId, Long userId);

    //Todo: 마음 채우기  루틴 삭제
    void deleteMentalRoutine(Long routineId, Long userId);

    //Todo: 감정일기 삭제
    void deleteEmotionalDiary(Long diaryId, Long userId);

    //Todo: ai 편지 삭제
    void deleteAiLetter(Long letterId, Long userId);

    //Todo : 마음 채우기 수정
    void updateMentalRoutine(Long routineId, MentalDto.mentalRoutineUpdateDto mentalRoutineUpdateDto, Long userId);

    //Todo : 마음 채우기 루틴 on
    void onMentalRoutine(Long routineId, LocalDate today, Long userId);

    //Todo : 마음 채우기 루틴 off
    void offMentalRoutine(Long routineId, LocalDate today, Long userId);

    //Todo: 마음 채우기 일정 수행 완료
    void completeRoutine(Long routineRecordId, Long userId);

    //Todo: 마음 채우기 일정 수행 완료 취소
    void cancelRoutine(Long routineRecordId, Long userId);

    //Todo: 회원의 모든 마음채우기 데이터 삭제
    void deleteSpiritDate(Long userId);

    //Todo: 감정일기 수정
    void updateEmotionalDiary(Long userId, Long diaryId, MentalDto.UpdateEmotionalDiaryDto emotionalDiaryDto);

    //Todo: 기본 기능 생성
    void createDefaultService(Long userId);

    //Todo: 쉬어가기
    void offDayRecord(Long recordId, Long userId);
}

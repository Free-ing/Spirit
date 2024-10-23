package service.spirit.service;

import service.spirit.dto.request.MentalDto;

import java.time.LocalDate;

public interface MentalCommonService {
    //Todo: 마음 채우기 루틴 설정
    Long addMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto);

    //Todo: 감정일기 작성
    Long saveEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto);

    //Todo: 감정일기 스크랩하기
    Long emotionalRecordDiaryScrap(Long recordId);

    //Todo: 감정일기 스크랩 취소
    Long emotionalRecordDiaryScrapCancel(Long recordId);

    //Todo: 마음 채우기  루틴 삭제
    void deleteMentalRoutine(Long routineId);

    //Todo: 감정일기 삭제
    void deleteEmotionalDiary(Long diaryId);

    //Todo: ai 편지 삭제
    void deleteAiLetter(Long letterId);

    //Todo : 마음 채우기 수정
    Long updateMentalRoutine(Long routineId, MentalDto.mentalRoutineUpdateDto mentalRoutineUpdateDto);

    //Todo : 마음 채우기 루틴 on
    void onMentalRoutine(Long routineId, LocalDate today);

    //Todo : 마음 채우기 루틴 off
    void offMentalRoutine(Long routineId, LocalDate today);

    //Todo: 마음 채우기 일정 수행 완료
    void completeRoutine(Long routineRecordId);

    //Todo: 마음 채우기 일정 수행 완료 취소
    void cancelRoutine(Long routineRecordId);

    //Todo: 회원의 모든 마음채우기 데이터 삭제
    void deleteSpiritDate(Long userId);
}

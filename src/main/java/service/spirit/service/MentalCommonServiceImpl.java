package service.spirit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.spirit.base.exception.code.RestApiException;
import service.spirit.base.exception.code.RoutineErrorStatus;
import service.spirit.dto.request.MentalDto;
import service.spirit.entity.AiLetter;
import service.spirit.entity.EmotionalDiary;
import service.spirit.entity.MentalRoutine;
import service.spirit.entity.MentalRoutineRecord;
import service.spirit.repository.AiLetterRepository;
import service.spirit.repository.EmotionalDiaryRepository;
import service.spirit.repository.MentalRoutineRecordRepository;
import service.spirit.repository.MentalRoutineRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static service.spirit.converter.toEntity.toEmotionalDiary;
import static service.spirit.converter.toEntity.toMentalRoutine;

@Service
@Transactional
@RequiredArgsConstructor
public class MentalCommonServiceImpl implements MentalCommonService {

    private final MentalRoutineRepository mentalRoutineRepository;
    private final EmotionalDiaryRepository emotionalDiaryRepository;
    private final AiLetterRepository aiLetterRepository;
    private final MentalRoutineRecordRepository mentalRoutineRecordRepository;

    //Todo: 마음 채우기 루틴 설정
    @Override
    public Long addMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto) {

        //마음 채우기 루틴 저장
        MentalRoutine mentalRoutine = mentalRoutineRepository.save(toMentalRoutine(mentalRoutineDto));

        //저장된 객체 반환
        return mentalRoutine.getId();
    }

    //Todo: 감정일기 작성
    @Override
    public Long saveEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto, Long recordId) {

        MentalRoutineRecord mentalRoutineRecord = mentalRoutineRecordRepository.findById(recordId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));

        //마음 채우기 루틴 저장
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.save(toEmotionalDiary(emotionalDiaryDto, mentalRoutineRecord.getRoutineDate(), mentalRoutineRecord));

        mentalRoutineRecord.setEmotionalDiary(emotionalDiary);
        //저장된 객체 반환
        return emotionalDiary.getId();
    }

    //Todo: 감정일기 스크랩하기
    @Override
    public Long emotionalRecordDiaryScrap(Long recordId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findById(recordId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));
        emotionalDiary.changeScrap(true);
        return emotionalDiary.getId();
    }

    //Todo: 감정일기 스크랩 취소
    @Override
    public Long emotionalRecordDiaryScrapCancel(Long recordId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findById(recordId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));
        emotionalDiary.changeScrap(false);
        return emotionalDiary.getId();
    }

    //Todo: 마음 채우기  루틴 삭제
    @Override
    public void deleteMentalRoutine(Long routineId){
        MentalRoutine mentalRoutine = mentalRoutineRepository.findById(routineId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));
        mentalRoutineRepository.delete(mentalRoutine);
    }

    //Todo: 감정일기 삭제
    @Override
    public void deleteEmotionalDiary(Long diaryId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));

        emotionalDiaryRepository.delete(emotionalDiary);
    }

    //Todo: ai 편지 삭제
    @Override
    public void deleteAiLetter(Long letterId){
        AiLetter aiLetter = aiLetterRepository.findById(letterId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.AI_LETTER_NOT_FOUND));

        aiLetterRepository.delete(aiLetter);
    }


    //Todo : 마음 채우기 수정
    @Override
    public void updateMentalRoutine(Long routineId, MentalDto.mentalRoutineUpdateDto mentalRoutineUpdateDto){
        MentalRoutine mentalRoutine = mentalRoutineRepository.findById(routineId)
                .orElseThrow(()-> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));

        mentalRoutine.update(mentalRoutineUpdateDto);

    }

    //Todo : 마음 채우기 루틴 on
    @Override
    public void onMentalRoutine(Long routineId, LocalDate today){
        MentalRoutine mentalRoutine = mentalRoutineRepository.findById(routineId)
                .orElseThrow(()-> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));

        mentalRoutine.updateStatus(true);
        // 현재 날짜 정보 가져오기
//        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        handleRoutineOn(mentalRoutine, today, endOfWeek);

        mentalRoutineRepository.save(mentalRoutine);
    }

    //Todo : 마음 채우기 루틴 off
    @Override
    public void offMentalRoutine(Long routineId, LocalDate today) {
        MentalRoutine mentalRoutine = mentalRoutineRepository.findById(routineId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));
        mentalRoutine.updateStatus(false);

        // 현재 날짜 정보 가져오기
//        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        handleRoutineOff(mentalRoutine, today, endOfWeek);

        mentalRoutineRepository.save(mentalRoutine);
    }

    //Todo: 마음 채우기 일정 수행 완료
    @Override
    public void completeRoutine(Long routineRecordId){
        MentalRoutineRecord mentalRoutineRecord = mentalRoutineRecordRepository.findById(routineRecordId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_RECORD_NOT_FOUND));

        //루틴 레코드 일정 완료 표시
        mentalRoutineRecord.updateCompleteAndCompleteDate(true, LocalDate.now());

    }

    //Todo: 마음 채우기 일정 수행 완료 취소
    @Override
    public void cancelRoutine(Long routineRecordId){
        MentalRoutineRecord mentalRoutineRecord = mentalRoutineRecordRepository.findById(routineRecordId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_RECORD_NOT_FOUND));
        mentalRoutineRecord.updateCompleteAndCompleteDate(false, null);

    }

    //Todo: 회원의 모든 마음채우기 데이터 삭제
    @Override
    public void deleteSpiritDate(Long userId){
        List<MentalRoutine> mentalRoutines = mentalRoutineRepository.findByUserId(userId);
        List<EmotionalDiary> emotionalDiaries = emotionalDiaryRepository.findByUserId(userId);

        mentalRoutineRepository.deleteAll(mentalRoutines);
        emotionalDiaryRepository.deleteAll(emotionalDiaries);
    }














    private void handleRoutineOff(MentalRoutine routine, LocalDate today, LocalDate endOfWeek) {
        LocalDate currentDate = today;
        while (!currentDate.isAfter(endOfWeek)) {
            // routineDate로 레코드를 찾아야 함
            Optional<MentalRoutineRecord> existingRecord = mentalRoutineRecordRepository
                    .findByMentalRoutineAndRoutineDateAndUserId(
                            routine,
                            currentDate,
                            routine.getUserId());

            existingRecord.ifPresent(record -> {
                record.updateStatus(false);
                mentalRoutineRecordRepository.save(record);  // 변경사항 저장
            });

            currentDate = currentDate.plusDays(1);
        }
    }
    private void handleRoutineOn(MentalRoutine routine, LocalDate today, LocalDate endOfWeek) {
        // 현재 날짜부터 이번 주 일요일까지의 날짜들에 대해 처리
        LocalDate currentDate = today;
        while (!currentDate.isAfter(endOfWeek )) {
            // 해당 요일에 대한 루틴 설정이 되어있는지 확인
            if (isDayEnabled(routine, currentDate)) {
                // 이미 record가 있는지 확인
                Optional<MentalRoutineRecord> existingRecord = mentalRoutineRecordRepository
                        .findByMentalRoutineAndRoutineDateAndUserId(
                                routine, currentDate, routine.getUserId());

                if (existingRecord.isPresent()) {
                    // 이미 존재하는 record의 status를 TRUE로 업데이트
                    existingRecord.get().updateStatus(true);
                } else {
                    // 새로운 record 생성
                    MentalRoutineRecord newRecord = MentalRoutineRecord.builder()
                            .userId(routine.getUserId())
                            .routineDate(currentDate)
                            .completeDay(null)
                            .complete(false)
                            .mentalRoutine(routine)
                            .status(true)
                            .build();
                    mentalRoutineRecordRepository.save(newRecord);
                }
            }
            currentDate = currentDate.plusDays(1);
        }
    }


    private boolean isDayEnabled(MentalRoutine routine, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return switch (dayOfWeek) {
            case MONDAY -> routine.getMonday() != null && routine.getMonday();
            case TUESDAY -> routine.getTuesday() != null && routine.getTuesday();
            case WEDNESDAY -> routine.getWednesday() != null && routine.getWednesday();
            case THURSDAY -> routine.getThursday() != null && routine.getThursday();
            case FRIDAY -> routine.getFriday() != null && routine.getFriday();
            case SATURDAY -> routine.getSaturday() != null && routine.getSaturday();
            case SUNDAY -> routine.getSunday() != null && routine.getSunday();
        };
    }
}

package service.spirit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.spirit.base.exception.code.RestApiException;
import service.spirit.base.exception.code.RoutineErrorStatus;
import service.spirit.dto.request.MentalDto;
import service.spirit.entity.*;
import service.spirit.repository.AiLetterRepository;
import service.spirit.repository.EmotionalDiaryRepository;
import service.spirit.repository.MentalRoutineRecordRepository;
import service.spirit.repository.MentalRoutineRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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
    public Long addMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto, Long userId) {

        MentalRoutine existRoutine = mentalRoutineRepository.findByUserIdAndMentalRoutineName(userId, mentalRoutineDto.getRoutineName());

        if(existRoutine != null){
            throw new RestApiException(RoutineErrorStatus.ROUTINE_ALREADY_EXIST);
        }else{
        //마음 채우기 루틴 저장
        MentalRoutine mentalRoutine = mentalRoutineRepository.save(toMentalRoutine(mentalRoutineDto));

        //저장된 객체 반환
        return mentalRoutine.getId();
        }
    }

    //Todo: 감정일기 작성
    @Override
    public Long saveEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto, Long recordId, Long userId) {

        MentalRoutineRecord mentalRoutineRecord = mentalRoutineRecordRepository.findByIdAndUserId(recordId, userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));

        if (mentalRoutineRecord.getEmotionalDiary() != null) {
            throw new RestApiException(RoutineErrorStatus.DIARY_ALREADY_EXIST);
        } else {
            //마음 채우기 루틴 저장
            EmotionalDiary emotionalDiary = emotionalDiaryRepository.save(toEmotionalDiary(emotionalDiaryDto, mentalRoutineRecord.getRoutineDate(), mentalRoutineRecord));

            mentalRoutineRecord.setEmotionalDiary(emotionalDiary);
            //저장된 객체 반환
            return emotionalDiary.getId();
        }
    }

    //Todo: 감정일기 스크랩하기
    @Override
    public Long emotionalRecordDiaryScrap(Long recordId, Long userId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findByIdAndUserId(recordId, userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));
        emotionalDiary.changeScrap(true);
        return emotionalDiary.getId();
    }

    //Todo: 감정일기 스크랩 취소
    @Override
    public Long emotionalRecordDiaryScrapCancel(Long recordId, Long userId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findByIdAndUserId(recordId,userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));
        emotionalDiary.changeScrap(false);
        return emotionalDiary.getId();
    }

    //Todo: 마음 채우기  루틴 삭제
    @Override
    public void deleteMentalRoutine(Long routineId,Long userId){
        MentalRoutine mentalRoutine = mentalRoutineRepository.findByIdAndUserId(routineId,userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));
        mentalRoutineRepository.delete(mentalRoutine);


    }

    //Todo: 감정일기 삭제
    @Override
    public void deleteEmotionalDiary(Long diaryId, Long userId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findByIdAndUserId(diaryId,userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));

        MentalRoutineRecord record = emotionalDiary.getMentalRoutineRecord();
        if (record != null) {
            record.setEmotionalDiary(null);
        }
        emotionalDiaryRepository.delete(emotionalDiary);

    }

    //Todo: ai 편지 삭제
    @Override
    public void deleteAiLetter(Long letterId, Long userId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findByAiLetter_IdAndUserId(letterId, userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.AI_LETTER_NOT_FOUND));

        AiLetter aiLetter = emotionalDiary.getAiLetter();


        EmotionalDiary diary = aiLetter.getEmotionalDiary();
        if (diary != null) {
            diary.setAiLetter(null);  // 연관관계 제거
        }

        aiLetterRepository.delete(aiLetter);
    }


    //Todo : 마음 채우기 수정
    @Override
    public void updateMentalRoutine(Long routineId, MentalDto.mentalRoutineUpdateDto mentalRoutineUpdateDto, Long userId, LocalDate today){
        MentalRoutine mentalRoutine = mentalRoutineRepository.findByIdAndUserId(routineId, userId)
                .orElseThrow(()-> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));

        mentalRoutine.update(mentalRoutineUpdateDto);

        offMentalRoutine(mentalRoutine.getId(), today, userId);
        onMentalRoutine(mentalRoutine.getId(), today, userId);

    }

    //Todo : 마음 채우기 루틴 on
    @Override
    public void onMentalRoutine(Long routineId, LocalDate today, Long userId) {
        MentalRoutine mentalRoutine = mentalRoutineRepository.findByIdAndUserId(routineId, userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));

        mentalRoutine.updateStatus(true);

        // today 날짜에 해당하는 루틴 레코드가 있는지 확인
        List<MentalRoutineRecord> existingRecords = mentalRoutineRecordRepository
                .findByMentalRoutineAndRoutineDateGreaterThanEqual(mentalRoutine, today);

        if (existingRecords.isEmpty()) {
            // 레코드가 없는 경우: 4주 후까지 새로 생성
            LocalDate endOfWeek = today.plusWeeks(4)
                    .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            handleRoutineOn(mentalRoutine, today, endOfWeek);
        } else {
            // 레코드가 있는 경우: 활성화된 요일의 레코드만 status를 true로 업데이트
            existingRecords.forEach(record -> {
                if (isDayEnabled(mentalRoutine, record.getRoutineDate())) {
                    record.updateStatus(true);
                    mentalRoutineRecordRepository.save(record);
                } else {
                    record.updateStatus(false);
                    mentalRoutineRecordRepository.save(record);
                }
            });
        }

        mentalRoutineRepository.save(mentalRoutine);
    }

    //Todo : 마음 채우기 루틴 off
    @Override
    public void offMentalRoutine(Long routineId, LocalDate today, Long userId) {
        MentalRoutine mentalRoutine = mentalRoutineRepository.findByIdAndUserId(routineId,userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));
        mentalRoutine.updateStatus(false);

        // 현재 날짜 정보 가져오기
//        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        handleRoutineOff(mentalRoutine, today);

        mentalRoutineRepository.save(mentalRoutine);
    }

    //Todo: 마음 채우기 일정 수행 완료
    @Override
    public void completeRoutine(Long routineRecordId, Long userId ){
        MentalRoutineRecord mentalRoutineRecord = mentalRoutineRecordRepository.findByIdAndUserId(routineRecordId, userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_RECORD_NOT_FOUND));

        //루틴 레코드 일정 완료 표시
        mentalRoutineRecord.updateCompleteAndCompleteDate(true, LocalDate.now());

    }

    //Todo: 마음 채우기 일정 수행 완료 취소
    @Override
    public void cancelRoutine(Long routineRecordId, Long userId){
        MentalRoutineRecord mentalRoutineRecord = mentalRoutineRecordRepository.findByIdAndUserId(routineRecordId, userId)
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


    //Todo: routine이 on인 건 자동으로 일정 생성되게 하기
    @Scheduled(cron = "0 1 0 ? * MON")
    public void createRoutineRecord() {
        List<MentalRoutine> mentalRoutineList = mentalRoutineRepository.findActiveRoutines();

        for (MentalRoutine mentalRoutine : mentalRoutineList) {
            onMentalRoutine(mentalRoutine.getId(), LocalDate.now(), mentalRoutine.getUserId());
        }

    }

    //Todo: 감정일기 수정
    @Override
    public void updateEmotionalDiary(Long userId, Long diaryId, MentalDto.UpdateEmotionalDiaryDto emotionalDiaryDto){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findByIdAndUserId(diaryId, userId)
                .orElseThrow(()-> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));

        emotionalDiary.updateDiary(emotionalDiaryDto);
    }


    //Todo: 기본 기능 생성
    @Override
    public void createDefaultService(Long userId) {
        MentalRoutine diaryRoutine = createMentalRoutine(userId, "감정일기 작성", "오늘 잘했던 일, 힘들었던 일을\n 일기로 작성해보세요!\n 작성 후 ai가 써주는 편지도 읽을 수 있어요.",
                LocalTime.of(22, 0, 0), LocalTime.of(22, 30, 0), "https://freeingimage.s3.ap-northeast-2.amazonaws.com/emotional_diary.png", SpiritType.DIARY);

        MentalRoutine meditation = createMentalRoutine(userId, "명상하기", """
                명상은 마음을 안정시키고 현재에 집중하는 정신 수련법입니다.\n\n 스트레스 해소, 불안 감소, 집중력 향상에 효과적이며, 규칙적인 명상은 정서적 안정과 수면의 질 향상에도 도움을 줍니다.\n\n 초보자도 하루 5-10분부터 시작할 수 있어 부담 없이 시작할 수 있어요!
                """,
                LocalTime.of(22, 0, 0), LocalTime.of(22, 30, 0), "https://freeingimage.s3.ap-northeast-2.amazonaws.com/leaf_and_music.png",SpiritType.MEDITATION);


//        1. 호흡 명상
//        편안한 자세로 앉아 눈을 감고, 들숨과 날숨에 집중합니다. 호흡이 들어오고 나가는 것을 자연스럽게 관찰하며, 다른 생각이 들면 다시 호흡으로 주의를 가져옵니다.
//
//        2. 바디스캔 명상
//        발끝부터 머리끝까지 차례로 신체 각 부위의 감각을 느끼며 이완합니다. 긴장된 부위가 있다면 호흡과 함께 그 긴장을 부드럽게 놓아주세요.
        mentalRoutineRepository.save(diaryRoutine);
        mentalRoutineRepository.save(meditation);
    }


    //Todo: 쉬어가기
    @Override
    public void offDayRecord(Long recordId, Long userId){
        MentalRoutineRecord mentalRoutineRecord = mentalRoutineRecordRepository.findByIdAndUserId(recordId, userId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.RECORD_NOT_FOUND));

        mentalRoutineRecord.offRoutineRecord();

    }


    //Todo: 활성화된 루틴 매주 일정이 자동으로 생기도록 설정
    @Scheduled(cron = "0 9 14 ? * TUE")  // 매주 화요일 14:03에 실행
    public void autoCreateRoutineRecord() {
        try {
            List<MentalRoutine> mentalRoutineList = mentalRoutineRepository.findActiveRoutines();
            System.out.println("스케줄러 실행: 활성화된 루틴 수 = " + mentalRoutineList.size());

            for (MentalRoutine mentalRoutine : mentalRoutineList) {
                try {
                    System.out.println("처리 중인 루틴: " + mentalRoutine.getMentalRoutineName());

                    // 해당 루틴의 가장 최근 routineRecord 찾기
                    Optional<MentalRoutineRecord> latestRecord = mentalRoutineRecordRepository
                            .findTopByMentalRoutineAndStatusOrderByRoutineDateDesc(mentalRoutine, true);

                    if (latestRecord.isEmpty()) {
                        System.out.println("최근 기록 없음 - 현재 날짜부터 시작");
                        LocalDate startDate = LocalDate.now();
                        LocalDate endDate = startDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                        handleRoutineOn(mentalRoutine, startDate, endDate);
                        continue;
                    }

                    LocalDate startDate = latestRecord.get().getRoutineDate().plusDays(1);
                    LocalDate endDate = startDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

                    System.out.println("루틴 레코드 생성 - 시작일: " + startDate + ", 종료일: " + endDate);

                    // 날짜 유효성 검사
                    if (startDate.isAfter(endDate)) {
                        System.out.println("시작일이 종료일보다 늦음 - 스킵");
                        continue;
                    }

                    handleRoutineOn(mentalRoutine, startDate, endDate);
                    System.out.println("루틴 레코드 생성 완료: " + mentalRoutine.getMentalRoutineName());

                } catch (Exception e) {
                    System.err.println("루틴 처리 중 오류 발생: " + mentalRoutine.getMentalRoutineName());
                    e.printStackTrace();
                    // 한 루틴의 실패가 다른 루틴 처리를 중단시키지 않도록 continue
                    continue;
                }
            }
        } catch (Exception e) {
            System.err.println("자동 루틴 생성 중 오류 발생");
            e.printStackTrace();
        }
    }

    private void handleRoutineOff(MentalRoutine routine, LocalDate today) {
        LocalDate currentDate = today;
        LocalDate endOfWeek = today.plusWeeks(4);  // 4주 추가
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







    private MentalRoutine createMentalRoutine(Long userId, String routineName, String explanation, LocalTime startTime , LocalTime endTime, String imageUrl, SpiritType spiritType) {
        return MentalRoutine.builder()
                .metalRoutineName(routineName)
                .userId(userId)
                .monday(true)
                .tuesday(true)
                .wednesday(true)
                .thursday(true)
                .friday(true)
                .saturday(true)
                .sunday(true)
                .explanation(explanation)
                .startTime(startTime)
                .endTime(endTime)
                .status(false)
                .imageUrl(imageUrl)
                .basicService(spiritType)
                .build();
    }
}

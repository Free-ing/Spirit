package service.spirit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.spirit.base.exception.code.RestApiException;
import service.spirit.base.exception.code.RoutineErrorStatus;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.dto.response.RoutineTrackerDto;
import service.spirit.entity.*;
import service.spirit.repository.AiLetterRepository;
import service.spirit.repository.EmotionalDiaryRepository;
import service.spirit.repository.MentalRoutineRecordRepository;
import service.spirit.repository.MentalRoutineRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static service.spirit.converter.ToDto.toAiLetterDto;
import static service.spirit.converter.ToDto.toEmotionalDiaryDTO;

@Service
@RequiredArgsConstructor
public class MentalQueryServiceImpl implements MentalQueryService{

    private final MentalRoutineRepository mentalRoutineRepository;
    private final EmotionalDiaryRepository emotionalDiaryRepository;
    private final MentalRoutineRecordRepository mentalRoutineRecordRepository;
    private final AiLetterRepository aiLetterRepository;

    //Todo: 마음 채우기 루틴 리스트 조회
    @Override
    public List<ResponseMentalDto.MentalRoutineDto> getSpiritRoutineList(Long userId){
        return mentalRoutineRepository.findByUserId(userId)
                .stream()
                .map(mentalRoutine -> ResponseMentalDto.MentalRoutineDto.builder()
                        .spiritName(mentalRoutine.getMentalRoutineName())
                        .routineId(mentalRoutine.getId())
                        .imageUrl(mentalRoutine.getImageUrl())
                        .status(mentalRoutine.getStatus())
                        .monday(mentalRoutine.getMonday())
                        .tuesday(mentalRoutine.getTuesday())
                        .wednesday(mentalRoutine.getWednesday())
                        .thursday(mentalRoutine.getThursday())
                        .friday(mentalRoutine.getFriday())
                        .saturday(mentalRoutine.getSaturday())
                        .sunday(mentalRoutine.getSunday())
                        .startTime(mentalRoutine.getStartTime())
                        .endTime(mentalRoutine.getEndTime())
                        .explanation(mentalRoutine.getExplanation())
                        .build())
                .collect(Collectors.toList());

    }

//    //Todo: 감정일기 존재하는지 여부 조회
//    @Override
//    public List<ResponseMentalDto.DiaryDateDto> getDiaryDate(int year, int month, Long userId){
//
//        List<ResponseMentalDto.DiaryDateDto> List = emotionalDiaryRepository.getRecordListByDate(year, month,userId);
//        System.out.println(List);
//        return List;
//    }

    //Todo: 감정일기 존재하는지 여부 조회
    @Override
    public List<ResponseMentalDto.DiaryDateDto> getDiaryRecordDate(int year, int month, Long userId) {
        return mentalRoutineRecordRepository.getRecordListByDate(year, month, userId, SpiritType.DIARY);
    }


    // Todo: 감정일기 상세 조회 기능
    @Override
    public ResponseMentalDto.EmotionalDiaryDto getEmotionalDiary(Long recordId){
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findById(recordId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));
        return toEmotionalDiaryDTO(emotionalDiary);
    }

    //Todo: 마음 채우기 루틴 설명 보기
    @Override
    public ResponseMentalDto.RoutineInfoDto getRoutineInfo(Long routineId){
        return mentalRoutineRepository.getRoutineInfo(routineId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));
    }

    //Todo: 일별 루틴 일정 조회
    @Override
    public  List<ResponseMentalDto.DayRoutineDto> getDayRoutine(LocalDate date, Long userId){
        System.out.println(date);
        System.out.println(userId);
        List<ResponseMentalDto.DayRoutineDto> dayRoutineDtoList = mentalRoutineRecordRepository.getDayRoutine(date,userId, true);
        System.out.println(dayRoutineDtoList);
        return dayRoutineDtoList;

    }

    //Todo: 마음 채우기 루틴 트래커 조회
    @Override
    public List<RoutineTrackerDto.MentalRoutineTrackerDto> getMentalRoutineTrackers(Long userId, int year, int month) {
        Map<String, RoutineTrackerDto.MentalRoutineTrackerDto> routineMap = new LinkedHashMap<>();
        List<MentalRoutine> routines = mentalRoutineRepository.findAllWithRecordsByUserId(userId, year, month);

        for (MentalRoutine routine : routines) {
            if (!routine.getMentalRoutineRecordList().isEmpty()) {  // 레코드가 있는 경우만 처리
                RoutineTrackerDto.MentalRoutineTrackerDto trackerDto =
                        routineMap.computeIfAbsent(routine.getMentalRoutineName(),
                                k -> {
                                    RoutineTrackerDto.MentalRoutineTrackerDto dto =
                                            new RoutineTrackerDto.MentalRoutineTrackerDto(routine.getMentalRoutineName());
                                    dto.setImageUrl(routine.getImageUrl()); // 이미지 URL 설정
                                    return dto;
                                });
                for (MentalRoutineRecord record : routine.getMentalRoutineRecordList()) {
                    trackerDto.addRecord(new RoutineTrackerDto.MentalRecordDto(
                            record.getId(),
                            record.getRoutineDate()
                    ));
                }
            }
        }

        return new ArrayList<>(routineMap.values());
    }

    //Todo: 스크랩 감정일기 조회
    @Override
    public List<ResponseMentalDto.EmotionalDiaryDto> getScrapEmotionalDiary(Long userId){
       return emotionalDiaryRepository.getScrapEmotionalDiary(userId,true);
    }

    //Todo: ai 편지 조회
    @Override
    public ResponseMentalDto.AiLetterDto getLetter(Long letterId){
        AiLetter aiLetter = aiLetterRepository.findById(letterId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.AI_LETTER_NOT_FOUND));
        return toAiLetterDto(aiLetter);

    }


    //Todo: 홈화면 하나라도 수행한 날짜 반환
    @Override
    @Transactional(readOnly = true)
    public List<ResponseMentalDto.DayCompleteRoutine> getCompleteDate(LocalDate startDate, LocalDate endDate, Long userId) {
        return mentalRoutineRecordRepository.findCompletedDatesByUserIdAndDateRange(userId, startDate, endDate);
    }

}

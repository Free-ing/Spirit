package service.spirit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.spirit.base.exception.code.RestApiException;
import service.spirit.base.exception.code.RoutineErrorStatus;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.EmotionalDiary;
import service.spirit.repository.EmotionalDiaryRepository;
import service.spirit.repository.MentalRoutineRecordRepository;
import service.spirit.repository.MentalRoutineRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static service.spirit.converter.ToDto.toEmotionalDiaryDTO;

@Service
@RequiredArgsConstructor
public class MentalQueryServiceImpl implements MentalQueryService{

    private final MentalRoutineRepository mentalRoutineRepository;
    private final EmotionalDiaryRepository emotionalDiaryRepository;
    private final MentalRoutineRecordRepository mentalRoutineRecordRepository;

    //Todo: 마음 채우기 루틴 리스트 조회
    @Override
    public List<ResponseMentalDto.MentalRoutineDto> getSpiritRoutineList(Long userId){
        return mentalRoutineRepository.findByUserId(userId)
                .stream()
                .map(mentalRoutine -> ResponseMentalDto.MentalRoutineDto.builder()
                        .hobbyName(mentalRoutine.getMentalRoutineName())
                        .routineId(mentalRoutine.getId())
                        .imageUrl(mentalRoutine.getImageUrl())
                        .status(mentalRoutine.getStatus())
                        .monthday(mentalRoutine.getMonday())
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

    //Todo: 감정일기 존재하는지 여부 조회
    @Override
    public List<ResponseMentalDto.DiaryDateDto> getDiaryDate(int year, int month, Long userId){
        return emotionalDiaryRepository.getRecordListByDate(year, month,userId );
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
        List<ResponseMentalDto.DayRoutineDto> dayRoutineDtoList = mentalRoutineRecordRepository.getDayRoutine(date,userId, true);
        System.out.println(dayRoutineDtoList);
        return dayRoutineDtoList;

    }
}

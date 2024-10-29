package service.spirit.converter;

import service.spirit.dto.request.MentalDto;
import service.spirit.entity.AiLetter;
import service.spirit.entity.EmotionalDiary;
import service.spirit.entity.MentalRoutine;
import service.spirit.entity.MentalRoutineRecord;

import java.time.LocalDate;

public class toEntity {
    public static MentalRoutine toMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto){
        return MentalRoutine.builder()
                .metalRoutineName(mentalRoutineDto.getRoutineName())
                .userId(mentalRoutineDto.getUserId())
                .monday(mentalRoutineDto.getMonday())
                .tuesday(mentalRoutineDto.getTuesday())
                .wednesday(mentalRoutineDto.getWednesday())
                .thursday(mentalRoutineDto.getThursday())
                .friday(mentalRoutineDto.getFriday())
                .saturday(mentalRoutineDto.getSaturday())
                .sunday(mentalRoutineDto.getSunday())
                .explanation(mentalRoutineDto.getExplanation())
                .startTime(mentalRoutineDto.getStartTime())
                .endTime(mentalRoutineDto.getEndTime())
                .status(false)
                .imageUrl(mentalRoutineDto.getImageUrl())
                .build();
    }

    public static EmotionalDiary toEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto, LocalDate date, MentalRoutineRecord mentalRoutineRecord){
        return EmotionalDiary.builder()
                .mentalRoutineRecord(mentalRoutineRecord)
                .goodContent(emotionalDiaryDto.getWellDone())
                .badContent(emotionalDiaryDto.getHardWork())
                .emotion(emotionalDiaryDto.getEmotion())
                .routineDate(date)
                .getAiLetter(emotionalDiaryDto.getGetAiLetter())
                .userId(emotionalDiaryDto.getUserId())
                .scrap(false)
                .build();
    }

//    public static AiLetter toAiLetter(String content){
//        return AiLetter.builder()
//                .
//    }

}

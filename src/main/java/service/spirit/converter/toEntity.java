package service.spirit.converter;

import service.spirit.dto.request.MentalDto;
import service.spirit.entity.EmotionalDiary;
import service.spirit.entity.MentalRoutine;

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
                .build();
    }

    public static EmotionalDiary toEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto){
        return EmotionalDiary.builder()
                .goodContent(emotionalDiaryDto.getWellDone())
                .badContent(emotionalDiaryDto.getHardWork())
                .emotion(emotionalDiaryDto.getEmotion())
                .getAiLetter(emotionalDiaryDto.getGetAiLetter())
                .userId(emotionalDiaryDto.getUserId())
                .build();
    }

}

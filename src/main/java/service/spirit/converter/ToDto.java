package service.spirit.converter;

import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.AiLetter;
import service.spirit.entity.EmotionalDiary;

public class ToDto {

    public static ResponseMentalDto.EmotionalDiaryDto toEmotionalDiaryDTO(EmotionalDiary emotionalDiary) {
        return ResponseMentalDto.EmotionalDiaryDto.builder()
                .emotion(emotionalDiary.getEmotion())
                .date(emotionalDiary.getRoutineDate())
                .wellDone(emotionalDiary.getGoodContent())
                .hardWork((emotionalDiary.getBadContent()))
                .diaryId(emotionalDiary.getId())
                .scrap(emotionalDiary.getScrap())
                .letterId(emotionalDiary.getAiLetter() != null ? emotionalDiary.getAiLetter().getId() : null)  // null 체크 추가
                .build();
    }

    public static ResponseMentalDto.AiLetterDto toAiLetterDto(AiLetter aiLetter) {
        return ResponseMentalDto.AiLetterDto.builder()
                .content(aiLetter.getContent())
                .build();
    }
 }


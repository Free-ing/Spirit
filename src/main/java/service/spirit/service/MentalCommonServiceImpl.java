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
import service.spirit.repository.AiLetterRepository;
import service.spirit.repository.EmotionalDiaryRepository;
import service.spirit.repository.MentalRoutineRepository;

import static service.spirit.converter.toEntity.toEmotionalDiary;
import static service.spirit.converter.toEntity.toMentalRoutine;

@Service
@Transactional
@RequiredArgsConstructor
public class MentalCommonServiceImpl implements MentalCommonService {

    private final MentalRoutineRepository mentalRoutineRepository;
    private final EmotionalDiaryRepository emotionalDiaryRepository;
    private final AiLetterRepository aiLetterRepository;

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
    public Long saveEmotionalDiary(MentalDto.emotionalDiaryDto emotionalDiaryDto) {

        //마음 채우기 루틴 저장
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.save(toEmotionalDiary(emotionalDiaryDto));

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
    public Long updateMentalRoutine(Long routineId, MentalDto.mentalRoutineUpdateDto mentalRoutineUpdateDto){
        MentalRoutine mentalRoutine = mentalRoutineRepository.findById(routineId)
                .orElseThrow(()-> new RestApiException(RoutineErrorStatus.ROUTINE_NOT_FOUND));

        mentalRoutine.update(mentalRoutineUpdateDto);

        return mentalRoutine.getId();
    }

}

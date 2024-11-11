package service.spirit.service;


import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.spirit.base.exception.code.RestApiException;
import service.spirit.base.exception.code.RoutineErrorStatus;
import service.spirit.entity.AiLetter;
import service.spirit.entity.EmotionalDiary;
import service.spirit.repository.AiLetterRepository;
import service.spirit.repository.EmotionalDiaryRepository;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OpenAiServiceImpl implements OpenAiService{
    private final ChatClient letterChatClient;
    private final ChatClient letterWriterExpert;
    private final EmotionalDiaryRepository emotionalDiaryRepository;
    private final AiLetterRepository aiLetterRepository;
    private final TranslationService translationService;


    //Todo:ai 편지 작성
    @Override
    public Long writeAiLetter(Long diaryId) {

        //감정일기 찾기
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));


                String diary1 = emotionalDiary.getGoodContent();
                String diary2 = emotionalDiary.getBadContent();


        String userMessageContent = String.format("\n" +
                "Please write a letter to your friend based on the following diary entry. Good job: %s / Bad job: %s", diary1,diary2);

        String jsonResponse1 = letterChatClient.prompt()
                .user(userMessageContent)
                .call()
                .content();

        System.out.println(jsonResponse1);



//        String translatedContent = translationService.translateToKorean(jsonResponse);


        //ai 편지 객체 만들기
        AiLetter aiLetter  = AiLetter.builder()
                .content(jsonResponse1)
                .emotionalDiary(emotionalDiary)
                .build();

        //ai 편지 저장
        aiLetterRepository.save(aiLetter);
        emotionalDiary.setAiLetter(aiLetter);
        System.out.println(emotionalDiary.getAiLetter().getId());

        return aiLetter.getId();

    }
}
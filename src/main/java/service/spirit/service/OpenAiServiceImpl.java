package service.spirit.service;


import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import service.spirit.base.exception.code.RestApiException;
import service.spirit.base.exception.code.RoutineErrorStatus;
import service.spirit.entity.AiLetter;
import service.spirit.entity.EmotionalDiary;
import service.spirit.repository.AiLetterRepository;
import service.spirit.repository.EmotionalDiaryRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements OpenAiService {
    private final ChatClient chatClient;
    private final EmotionalDiaryRepository emotionalDiaryRepository;
    private final AiLetterRepository aiLetterRepository;

    //Todo:ai 편지 작성
    @Override
    public Long writeAiLetter(Long diaryId) {

        //감정일기 찾기
        EmotionalDiary emotionalDiary = emotionalDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new RestApiException(RoutineErrorStatus.DIARY_NOT_FOUND));

        String systemPromptContent = "너는 사용자의 친구야. 사용자가 오늘 잘한 일과 힘들었던 일에 대해서 일기를 작성하면 너는 편지를 작성해줘. 위로의 말도 좋고, 너가 하고 싶은 말을 적어도 좋아. 친근하게 최소 5줄 이상으로 친근하고 다정하게" ;


        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPromptContent);
        Message systemMessage = systemPromptTemplate.createMessage();
                String diary1 = emotionalDiary.getGoodContent();
                String diary2 = emotionalDiary.getBadContent();

        String userMessageContent = String.format("다음 일기를 바탕으로 친구에게 편지를 작성해줘. 잘한일: %s  / 힘들었던 일 : %s", diary1,diary2);
        UserMessage userMessage = new UserMessage(userMessageContent);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.call(prompt);
        String jsonResponse = response.getResult().getOutput().getContent();


        System.out.println(jsonResponse);



        //ai 편지 객체 만들기
        AiLetter aiLetter  = AiLetter.builder()
                .content(jsonResponse)
                .emotionalDiary(emotionalDiary)
                .build();

        //ai 편지 저장
        aiLetterRepository.save(aiLetter);

        return aiLetter.getId();

//        try {
//            // JSON 배열을 List<AiHobbyResponseDto>로 직접 파싱
//            return objectMapper.readValue(jsonResponse,
//                    objectMapper.getTypeFactory().constructCollectionType(List.class, MentalDto.AiLetterDto.class));
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse AI response", e);
//        }
    }
}
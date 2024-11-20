package service.spirit.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    private static final String LETTER_PROMPT = """
            "You're writing to your best friend. They just shared their daily diary with you. " +
             "Write a casual and heartfelt letter like you're really talking to them - the way best friends chat! " +
             -"Be supportive about the good stuff and give them a honest comfort about the hard parts. " +
             - "Keep it super natural, like you're having a cozy conversation. No need for formal greetings or endings."+
             - "Also, add one creative and touching comment that would move your friend.\\n"+
             - "You must use emoticons and special characters.\\n" +
             -"한글로 번역해서, 반말로 친근하게 자연스럽게 번역해서 편지를 작성해줘. 그리고 이모티콘은 넣지 말아줘"
        """;

    private static final String KOREAN_PROMPT = """
            "Please review the following Korean feedback carefully and adjust it to make the language sound natural, warm, and friendly, as if speaking to a friend. Please avoid using symbols like '*' and '#'. For emphasis, feel free to use emojis or adjust the tone instead. Korean feedback: \\"%s\\"", translatedFeedback);
            """;


    @Bean
    public ChatClient letterChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(LETTER_PROMPT)
                .build();
    }

    @Bean
    public ChatClient koreanChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(KOREAN_PROMPT)
                .build();
    }




}

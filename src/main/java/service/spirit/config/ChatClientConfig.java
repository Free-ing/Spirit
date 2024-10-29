package service.spirit.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    ChatClient chatClient (ChatClient.Builder builder){

        return builder
                .defaultSystem("너는 친구야. 잘한 일, 힘들었던 일에 대해서 친구가 힘낼 수 있게 편지를 작성해줘. 이 때 편지의 맨 위와 맨 끝에 누가 누구에게 보내는지는 적지 말아줘. 그리고 반말을 사용하고 특수문자는 사용하지 말아줘")
                .build();
    }
}

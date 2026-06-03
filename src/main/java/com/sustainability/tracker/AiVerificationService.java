package com.sustainability.tracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@Service
public class AiVerificationService {
    private final OpenAiService service;

    public AiVerificationService(@Value("${OPENAI_API_KEY}") String apiKey) {
        this.service = new OpenAiService(apiKey);
    }

    public boolean verifyAction(String imageUrl, String actionDescription) {
ChatCompletionRequest request = ChatCompletionRequest.builder()
.model("gpt-4o")
.messages(List.of(
new ChatMessage("user", "Does this image " + imageUrl + " show someone " + actionDescription + "? Answer only with YES or NO.")
))

.build();
ChatCompletionResult result = service.createChatCompletion(request);
String answer = result.getChoices().get(0).getMessage().getContent();
return answer.trim().equalsIgnoreCase("YES");

}
}

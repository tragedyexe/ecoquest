package com.sustainability.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;

import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;


@Service
public class AiVerificationService {
    private final OpenAIClient openAIClient;

    public AiVerificationService(@Value("${OPENAI_API_KEY:}") String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("OPENAI_API_KEY is not set. Please provide it as an environment variable or in application.properties");
        }
        this.openAIClient = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public boolean verifyAction(String imageUrl, String actionDescription) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-4o")
                .addMessage(ChatCompletionUserMessageParam.builder()
                        
                        .content("Does this image " + imageUrl + " show someone " + actionDescription + "? Answer only with YES or NO.")
                        .build())
                .build();

        var result = openAIClient.chat().completions().create(params);
        String answer = result.choices().get(0).message().content().orElse("");
        return answer.trim().equalsIgnoreCase("YES");
    }
}
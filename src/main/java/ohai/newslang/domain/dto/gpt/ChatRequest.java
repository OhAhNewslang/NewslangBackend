package ohai.newslang.domain.dto.gpt;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    @Builder
    public ChatRequest(String model, String systemPrompt, String prompt, int n, double temperature) {
        this.model = model;
        this.messages = new ArrayList<>();
//        this.messages.add(new Message("system", systemPrompt));
        this.messages.add(new Message("user", prompt));
        this.n = n;
        this.temperature = temperature;
    }
}

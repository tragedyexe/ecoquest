package com.sustainability.tracker;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SustainabilityBot extends TelegramLongPollingBot {
@Autowired private UserRepository userRepository;
Map<Long, String> userStates = new HashMap<>();
private AiVerificationService aiService;
    @Override
public String getBotUsername() {
    return "EcoQuestBot";
}

@Override
public String getBotToken() {
    return "8025089207:AAGExgQsuMn6oB6-WSaEYTHxcPWyFWZSkx4";
}

@Override
public void onUpdateReceived(Update update) {
if (update.hasMessage() && update.getMessage().hasText()) {
String messageText = update.getMessage().getText();
long chatId = update.getMessage().getChatId();
SendMessage message = new SendMessage();
message.setChatId(String.valueOf(chatId));

if (messageText.equals("/start")) {
message.setText("Welcome to EcoQuestBot! Choose a username:");
userStates.put(chatId, "WAITING_FOR_USERNAME");
} else if ("WAITING_FOR_USERNAME".equals(userStates.get(chatId))) {
User newUser = new User(chatId, messageText, 0);
userRepository.save(newUser);
message.setText("Username " + messageText + " saved!");
userStates.remove(chatId);
} else if (messageText.equals("/log")) {
message.setText("what action did you do? (e.g. reusable cup, took bus)");
userStates.put(chatId, "WAITING_FOR_ACTION");
} else if ("WAITING_FOR_ACTION".equals(userStates.get(chatId))) {
message.setText("bet, now send a photo for proof!");

userStates.put(chatId, "WAITING_FOR_ACTION_PHOTO");
}

try { execute(message); } catch (Exception e) { e.printStackTrace(); }
} else if (update.hasMessage() && update.getMessage().hasPhoto()) {
long chatIdPhoto = update.getMessage().getChatId();
if ("WAITING_FOR_ACTION_PHOTO".equals(userStates.get(chatIdPhoto))) {
    if (update.getMessage().hasPhoto()) {
String imageUrl = update.getMessage().getPhoto().get(0).getFileId();
String caption = update.getMessage().getCaption() != null ? update.getMessage().getCaption() : "";
boolean isVerified = aiService.verifyAction(imageUrl, caption);
SendMessage picMsg = new SendMessage();
picMsg.setChatId(String.valueOf(chatIdPhoto));
picMsg.setText(isVerified ? "proof secured! action verified!" : "proof failed. ai says that doesn't match the action.");
userStates.remove(chatIdPhoto);
try { execute(picMsg); } catch (Exception e) {}
}

}
}
}
private static final String BOT_USERNAME = "EcoQuestBot";
private static final String BOT_TOKEN = "8025089207:AAGExgQsuMn6oB6-WSaEYTHxcPWyFWZSkx4";

}

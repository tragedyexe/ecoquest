package com.sustainability.tracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class SustainabilityTrackerApplication {

	public static void main(String[] args) {
		var context =SpringApplication.run(SustainabilityTrackerApplication.class, args);
		try {
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

    botsApi.registerBot(context.getBean(SustainabilityBot.class));
    System.out.println("Bot started!");
} catch (TelegramApiException e) {
    e.printStackTrace();
}
	}

}
 
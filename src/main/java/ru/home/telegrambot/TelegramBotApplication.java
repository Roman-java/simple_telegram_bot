package ru.home.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.home.telegrambot.handlers.MySuperBot;

@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(TelegramBotApplication.class, args);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MySuperBot());

    }

}

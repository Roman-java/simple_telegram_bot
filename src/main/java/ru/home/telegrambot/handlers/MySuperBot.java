package ru.home.telegrambot.handlers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MySuperBot extends TelegramLongPollingBot {
    public static final String MONDAY_REQUEST = "monday";
    public static final String TUESDAY_REQUEST = "tuesday";
    public static final String WEDNESDAY_REQUEST = "wednesday";
    public static final String THURSDAY_REQUEST = "thursday";
    public static final String FRIDAY_REQUEST = "friday";

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();

        try {
            execute(getResponseMessage(message));
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    private SendMessage getResponseMessage(Message message) throws IOException {
            switch (message.getText()) {
                case MONDAY_REQUEST:
                case TUESDAY_REQUEST:
                case WEDNESDAY_REQUEST:
                case THURSDAY_REQUEST:
                case FRIDAY_REQUEST:
                    return ReadFile(message);
                default:
                    return greetingMessage(message);
            }
    }

    private SendMessage greetingMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setText("Hello, " + message.getFrom().getFirstName() +
                ", choose the required task in the menu");
        response.setChatId(String.valueOf(message.getChatId()));
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    @Override
    public String getBotUsername() {
        return "username";
    }

    @Override
    public String getBotToken() {
        return "token";
    }

    private ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(MONDAY_REQUEST);
        row1.add(TUESDAY_REQUEST);
        row1.add(WEDNESDAY_REQUEST);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(THURSDAY_REQUEST);
        row2.add(FRIDAY_REQUEST);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        markup.setKeyboard(rows);
        return markup;
    }

    private SendMessage ReadFile(Message message) throws IOException {
        SendMessage response = new SendMessage();
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(message.getText() + ".txt"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        response.setText(stringBuilder.toString());
        bufferedReader.close();
        response.setChatId(String.valueOf(message.getChatId()));
        return response;
    }
}
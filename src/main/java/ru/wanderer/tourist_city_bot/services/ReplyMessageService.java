package ru.wanderer.tourist_city_bot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyMessageService {

    private final LocaleMessageService localeMessageService;

    public SendMessage getMenuMessage(final Long chatId, final String text) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();

        return createMessageWithKeyboard(chatId, text, replyKeyboardMarkup);
    }

    public SendMessage getReplyMessage(Long chatId, String message) {
        return new SendMessage(chatId.toString(), message);
    }

    public SendMessage getLocaleReplyMessage(Long chatId, String message) {
        return new SendMessage(chatId.toString(), localeMessageService.getMessage(message));
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        row1.add(new KeyboardButton("Получить информацию"));
        row1.add(new KeyboardButton("Добавить информацию"));
        row2.add(new KeyboardButton("Редактировать информацию"));
        row2.add(new KeyboardButton("Удалить информацию"));
        row3.add(new KeyboardButton("Все города"));
        row3.add(new KeyboardButton("Добавить город"));
        row4.add(new KeyboardButton("Редактировать город"));
        row4.add(new KeyboardButton("Удалить город"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(final Long chatId,
                                                  final String text,
                                                  final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);

        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }

        return sendMessage;
    }
}

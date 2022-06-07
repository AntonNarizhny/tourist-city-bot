package ru.wanderer.tourist_city_bot.botapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.wanderer.tourist_city_bot.cache.UserDataCache;

@Component
@RequiredArgsConstructor
public class TelegramFacade {

    private final BotContext botContext;
    private final UserDataCache userDataCache;

    public BotApiMethod<?> handleUpdate(Update update){
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        Long userId = message.getFrom().getId();
        String inputMessage = message.getText();

        BotState botState;
        BotOperation operation;
        SendMessage replyMessage;

        switch (inputMessage) {
            case "/start" -> {
                botState = BotState.START;
                operation = BotOperation.START;
            }
            case "Все города" -> {
                botState = BotState.SHOW_ALL_CITIES;
                operation = BotOperation.GET;
            }
            case "Добавить город" -> {
                botState = BotState.ASK_CITY;
                operation = BotOperation.CREATE;
            }
            case "Редактировать город" -> {
                botState = BotState.ASK_CITY;
                operation = BotOperation.UPDATE;
            }
            case "Удалить город" -> {
                botState = BotState.ASK_CITY;
                operation = BotOperation.DELETE;
            }
            case "Получить информацию" -> {
                botState = BotState.ASK_CITY;
                operation = BotOperation.GET;
            }
            case "Добавить информацию" -> {
                botState = BotState.ASK_CITY;
                operation = BotOperation.ADD_INFORMATION;
            }
            case "Редактировать информацию" -> {
                botState = BotState.ASK_CITY;
                operation = BotOperation.UPDATE_INFORMATION;
            }
            case "Удалить информацию" -> {
                botState = BotState.ASK_CITY;
                operation = BotOperation.DELETE_INFORMATION;
            }
            default -> {
                botState = userDataCache.getUserCurrentBotState(userId);
                operation = userDataCache.getUserCurrentBotOperation(userId);
            }
        }

        userDataCache.setUserCurrentBotState(userId, botState);
        userDataCache.setUserCurrentBotOperation(userId, operation);

        replyMessage = botContext.processInputMessage(botState, operation, message);

        return replyMessage;
    }
}

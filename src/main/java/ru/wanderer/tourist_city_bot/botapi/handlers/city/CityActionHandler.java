package ru.wanderer.tourist_city_bot.botapi.handlers.city;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.wanderer.tourist_city_bot.botapi.BotOperation;
import ru.wanderer.tourist_city_bot.botapi.BotState;
import ru.wanderer.tourist_city_bot.botapi.handlers.InputMessageHandler;
import ru.wanderer.tourist_city_bot.cache.CityCache;
import ru.wanderer.tourist_city_bot.cache.InformationCache;
import ru.wanderer.tourist_city_bot.cache.UserDataCache;
import ru.wanderer.tourist_city_bot.services.CityService;
import ru.wanderer.tourist_city_bot.services.InformationService;
import ru.wanderer.tourist_city_bot.services.ReplyMessageService;

@Component
@RequiredArgsConstructor
public class CityActionHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final CityCache cityCache;
    private final InformationCache informationCache;
    private final CityService cityService;
    private final InformationService informationService;
    private final ReplyMessageService messagesService;

    @Override
    public SendMessage handle(Message message, BotOperation operation) {
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        String answer = message.getText();

        SendMessage replyToUser = null;

        switch (operation){
            case UPDATE -> {
                String city = cityCache.getForAction(userId);

                if (cityService.update(city, answer)) {
                    replyToUser = messagesService.getReplyMessage(chatId, "Город успешно обновлён");
                } else {
                    replyToUser = messagesService.getReplyMessage(chatId, "Обновление не удалось\nПопробуйте ещё раз");
                }
                cityCache.clear(userId);
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
            }
            case ADD_INFORMATION -> {
                String city = cityCache.getForAction(userId);

                if (cityService.addInformation(city, answer)) {
                    replyToUser = messagesService.getReplyMessage(chatId,
                            String.format("Информация о городе %s успешно добавлена", city));
                } else {
                    replyToUser = messagesService.getReplyMessage(chatId, "Добавление не удалось\nПопробуйте ещё раз");
                }
                cityCache.clear(userId);
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
            }
            case UPDATE_INFORMATION -> {
                try {
                    if (informationService.findById(Long.valueOf(answer))) {
                        informationCache.setForAction(userId, Long.valueOf(answer));
                        replyToUser = messagesService.getReplyMessage(chatId, "Введите новую информацию:");
                        userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
                        userDataCache.setUserCurrentBotOperation(userId, BotOperation.SAVE_INFORMATION);
                    }
                } catch (NumberFormatException ex) {
                    replyToUser = messagesService.getReplyMessage(chatId, "Некорректный id!");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.SAVE_INFORMATION);
                    ex.printStackTrace();
                }
            }
            case DELETE_INFORMATION -> {
                try {
                    if (informationService.delete(Long.valueOf(answer))) {
                        replyToUser = messagesService.getReplyMessage(chatId, "Информация успешно удалена!");
                    } else {
                        replyToUser = messagesService.getReplyMessage(chatId, "Неверный id. Повторите попытку");
                    }
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
                } catch (NumberFormatException ex) {
                    replyToUser = messagesService.getReplyMessage(chatId, "Некорректный id!");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.SAVE_INFORMATION);
                    ex.printStackTrace();
                }
            }
        }

        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WAIT_NEW;
    }
}

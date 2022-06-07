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
public class WaitCityHandler implements InputMessageHandler {

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

        switch (operation) {
            case GET -> {
                replyToUser = messagesService.getReplyMessage(chatId, cityService.findInformationByCityName(answer));
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
            }
            case CREATE -> {
                replyToUser = messagesService.getReplyMessage(chatId, cityService.create(answer));
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
            }
            case UPDATE -> {
                if (cityService.findCityByName(answer)) {
                    cityCache.setForAction(userId, answer);
                    replyToUser = messagesService.getReplyMessage(chatId, "Введите новое название для города:");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT_NEW);
                } else {
                    replyToUser = messagesService.getReplyMessage(chatId, "Город с таким названием не найден!");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
                }
            }
            case DELETE -> {
                if (cityService.delete(answer)) {
                    replyToUser = messagesService.getReplyMessage(chatId, "Город успешно удалён");
                } else {
                    replyToUser = messagesService.getReplyMessage(chatId, "Город с таким названием не найден");
                }
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
            }
            case ADD_INFORMATION -> {
                if (cityService.findCityByName(answer)) {
                    cityCache.setForAction(userId, answer);
                    replyToUser = messagesService.getReplyMessage(chatId, "Введите новую информацию для города:");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT_NEW);
                } else {
                    replyToUser = messagesService.getReplyMessage(chatId, "Город с таким названием не найден!");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
                }
            }
            case WAIT_INFORMATION -> {
                if (cityService.findCityByName(answer)) {
                    cityCache.setForAction(userId, answer);
                    replyToUser = messagesService.getReplyMessage(chatId,
                            "Введите id информации о городе\n" +
                                    "(Это id) -> (n).Информация о городе");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT_NEW);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.UPDATE_INFORMATION);
                } else {
                    replyToUser = messagesService.getReplyMessage(chatId, "Город с таким названием не найден!");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
                }
            }
            case DELETE_INFORMATION -> {
                if (cityService.findCityByName(answer)) {
                    cityCache.setForAction(userId, answer);
                    replyToUser = messagesService.getReplyMessage(chatId,
                            "Введите id информации о городе\n" +
                                    "(Это id) -> (n).Информация о городе");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT_NEW);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.DELETE_INFORMATION);
                } else {
                    replyToUser = messagesService.getReplyMessage(chatId, "Город с таким названием не найден!");
                    userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                    userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
                }
            }
            case SAVE_INFORMATION -> {
                Long id = informationCache.getForAction(userId);

                if (informationService.update(id, answer))
                    replyToUser = messagesService.getReplyMessage(chatId, "Информация успешно обновлена");
                else replyToUser = messagesService.getReplyMessage(chatId, "Повторите попытку");

                informationCache.clear(userId);
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
                userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);
            }
        }

        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WAIT_CITY;
    }
}

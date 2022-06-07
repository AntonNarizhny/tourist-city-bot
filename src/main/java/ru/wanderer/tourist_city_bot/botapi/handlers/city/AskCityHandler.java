package ru.wanderer.tourist_city_bot.botapi.handlers.city;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.wanderer.tourist_city_bot.botapi.BotOperation;
import ru.wanderer.tourist_city_bot.botapi.BotState;
import ru.wanderer.tourist_city_bot.botapi.handlers.InputMessageHandler;
import ru.wanderer.tourist_city_bot.cache.UserDataCache;
import ru.wanderer.tourist_city_bot.services.ReplyMessageService;

@Component
@RequiredArgsConstructor
public class AskCityHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ReplyMessageService messageService;

    @Override
    public SendMessage handle(Message message, BotOperation operation) {
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();

        SendMessage replyToUser = null;

        switch (operation) {
            case GET -> {
                replyToUser = messageService.getLocaleReplyMessage(chatId, "reply.askCityForGetInformation");
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
            }
            case CREATE -> {
                replyToUser = messageService.getLocaleReplyMessage(chatId, "reply.askCityForCreate");
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
            }
            case UPDATE -> {
                replyToUser = messageService.getLocaleReplyMessage(chatId, "reply.askCityForUpdate");
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
            }
            case DELETE -> {
                replyToUser = messageService.getLocaleReplyMessage(chatId, "reply.askCityForDelete");
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
            }
            case ADD_INFORMATION -> {
                replyToUser = messageService.getLocaleReplyMessage(chatId, "reply.askCityForAddInformation");
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
            }
            case UPDATE_INFORMATION -> {
                replyToUser = messageService.getLocaleReplyMessage(chatId, "reply.askCityForUpdateInformation");
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
                userDataCache.setUserCurrentBotOperation(userId,BotOperation.WAIT_INFORMATION);
            }
            case DELETE_INFORMATION -> {
                replyToUser = messageService.getLocaleReplyMessage(chatId, "reply.askCityForDeleteInformation");
                userDataCache.setUserCurrentBotState(userId, BotState.WAIT_CITY);
            }
        }

        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_CITY;
    }
}

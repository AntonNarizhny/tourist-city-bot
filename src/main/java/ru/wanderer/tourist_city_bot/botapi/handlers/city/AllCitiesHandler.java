package ru.wanderer.tourist_city_bot.botapi.handlers.city;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.wanderer.tourist_city_bot.botapi.BotOperation;
import ru.wanderer.tourist_city_bot.botapi.BotState;
import ru.wanderer.tourist_city_bot.botapi.handlers.InputMessageHandler;
import ru.wanderer.tourist_city_bot.cache.UserDataCache;
import ru.wanderer.tourist_city_bot.services.CityService;
import ru.wanderer.tourist_city_bot.services.ReplyMessageService;

@Component
@RequiredArgsConstructor
public class AllCitiesHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final CityService cityService;
    private final ReplyMessageService messageService;

    @Override
    public SendMessage handle(Message message, BotOperation operation) {
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();

        SendMessage replyToUser = messageService.getReplyMessage(chatId, cityService.findAllCities());
        userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
        userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);

        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_ALL_CITIES;
    }
}

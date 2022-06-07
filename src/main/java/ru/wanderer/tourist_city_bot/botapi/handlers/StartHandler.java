package ru.wanderer.tourist_city_bot.botapi.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.wanderer.tourist_city_bot.botapi.BotOperation;
import ru.wanderer.tourist_city_bot.botapi.BotState;
import ru.wanderer.tourist_city_bot.cache.UserDataCache;
import ru.wanderer.tourist_city_bot.services.ReplyMessageService;

@Component
@RequiredArgsConstructor
public class StartHandler implements InputMessageHandler{

    private final UserDataCache userDataCache;
    private final ReplyMessageService messageService;

    @Override
    public SendMessage handle(Message message, BotOperation operation) {
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        String text = String.format("""
                Приветствую в туристическом боте, %s!
                Этот бот может рассказать информацию о введённом городе.
                Также доступны различные действия с городами и информацией о них.
                Воспользуйтесь кнопками меню для управления.
                """, message.getFrom().getFirstName());

        SendMessage replyToUser = messageService.getMenuMessage(chatId, text);

        userDataCache.setUserCurrentBotState(userId, BotState.WAIT);
        userDataCache.setUserCurrentBotOperation(userId, BotOperation.WAIT);

        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START;
    }
}

package ru.wanderer.tourist_city_bot.botapi.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.wanderer.tourist_city_bot.botapi.BotOperation;
import ru.wanderer.tourist_city_bot.botapi.BotState;
import ru.wanderer.tourist_city_bot.services.ReplyMessageService;

@Component
@RequiredArgsConstructor
public class WaitHandler implements InputMessageHandler{

    private final ReplyMessageService messageService;

    @Override
    public SendMessage handle(Message message, BotOperation operation) {
        Long chatId = message.getChatId();

        return messageService.getMenuMessage(chatId, "Выберите действие с помощью кнопок:");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WAIT;
    }
}

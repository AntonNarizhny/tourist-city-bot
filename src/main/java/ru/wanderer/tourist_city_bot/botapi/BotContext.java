package ru.wanderer.tourist_city_bot.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.wanderer.tourist_city_bot.botapi.handlers.InputMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotContext {

    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, BotOperation operation, Message message) {
        InputMessageHandler currentMessageHandler = messageHandlers.get(currentState);
        return currentMessageHandler.handle(message, operation);
    }
}

package ru.wanderer.tourist_city_bot.botapi.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.wanderer.tourist_city_bot.botapi.BotOperation;
import ru.wanderer.tourist_city_bot.botapi.BotState;

public interface InputMessageHandler {

    SendMessage handle(Message message, BotOperation operation);

    BotState getHandlerName();
}

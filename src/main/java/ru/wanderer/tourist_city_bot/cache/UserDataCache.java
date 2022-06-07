package ru.wanderer.tourist_city_bot.cache;

import org.springframework.stereotype.Component;
import ru.wanderer.tourist_city_bot.botapi.BotOperation;
import ru.wanderer.tourist_city_bot.botapi.BotState;

import java.util.HashMap;
import java.util.Map;

/**
 *  In-memory cache.
 *  userBotStates: userId and user's bot state
 *  userBotOperation: userId and user's bot operation
 */

@Component
public class UserDataCache{

    private final Map<Long, BotState> userBotStates = new HashMap<>();
    private final Map<Long, BotOperation> userBotOperation = new HashMap<>();

    public void setUserCurrentBotState(Long userId, BotState botState) {
        userBotStates.put(userId, botState);
    }

    public void setUserCurrentBotOperation(Long userId, BotOperation botOperation) {
        userBotOperation.put(userId, botOperation);
    }

    public BotState getUserCurrentBotState(Long userId) {
        BotState botState = userBotStates.get(userId);

        if (botState == null)
            botState = BotState.SHOW_ALL_CITIES;

        return botState;
    }

    public BotOperation getUserCurrentBotOperation(Long userId) {
        BotOperation botOperation = userBotOperation.get(userId);

        if (botOperation == null)
            botOperation = BotOperation.GET;

        return botOperation;
    }
}

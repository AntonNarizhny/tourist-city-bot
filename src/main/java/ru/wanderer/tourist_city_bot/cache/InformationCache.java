package ru.wanderer.tourist_city_bot.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  In-memory cache.
 *  informationForAction: userId and informationId for action
 */

@Component
public class InformationCache{

    private final Map<Long, Long> informationForAction = new HashMap<>();

    public void setForAction(Long userId, Long information) {
        informationForAction.put(userId, information);
    }

    public Long getForAction(Long userId) {
        return informationForAction.get(userId);
    }

    public void clear(Long userId) {
        informationForAction.put(userId, null);
    }
}

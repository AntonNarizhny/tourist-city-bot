package ru.wanderer.tourist_city_bot.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  In-memory cache.
 *  cityForAction: userId and city for action
 */

@Component
public class CityCache{

    private final Map<Long, String> cityForAction = new HashMap<>();

    public void setForAction(Long userId, String city) {
        cityForAction.put(userId, city);
    }

    public String getForAction(Long userId) {
        return cityForAction.get(userId);
    }

    public void clear(Long userId) {
        cityForAction.put(userId, null);
    }
}
